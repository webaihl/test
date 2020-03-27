package com.web.ffmpeg;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.bytedeco.javacv.*;
import org.bytedeco.javacv.Frame;
import org.apache.commons.io.FileUtils;
import static org.bytedeco.javacpp.avutil.AV_LOG_PANIC;
import static org.bytedeco.javacpp.avutil.av_log_set_level;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Component
public class SnapshotUtil {

    /** 绝对路径 **/
    private static String absolutePath = "";

    /** ftp目录 **/
    private static String staticDir = "ftp";

    /** 快照存放的目录 **/
    private static String fileDir = "/snapshot/";

    public static String catSnapshotBase64(String url) {
        return Base64.encodeBase64String(catSnapshot(url));
    }

    public static byte[] catSnapshot(String url) {
        return catSnapshot(url, 5);
    }

    public static byte[] catSnapshot(String url, int frameIdx) {
        if (Pattern.compile("^rtsp://.*$", Pattern.CASE_INSENSITIVE).matcher(url).matches()) {
            return catRtspSnapshot(url, frameIdx);
        } else if (Pattern.compile("^vas://.*$", Pattern.CASE_INSENSITIVE).matcher(url).matches()) {
            return MasSnapshotUtil.getMasSnapshotByte(url);
        }
        return null;
    }

    private static byte[] catRtspSnapshot(String url, int frameIdx) {
        byte[] picBy = null;
        try {
            av_log_set_level(AV_LOG_PANIC);
            FFmpegFrameGrabber grabber = FFmpegFrameGrabber.createDefault(url);
            grabber.setOption("stimeout", "3000000");
            grabber.start();
            int frameCnt = 0;
            while (true) {
                Frame frame = grabber.grabFrame();
                if (frame == null || frame.image ==null){
                    continue;
                }
                frameCnt++;
                if (frameCnt < frameIdx){
                    continue;
                }
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage bufferedImage = converter.getBufferedImage(frame);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                boolean flag = ImageIO.write(bufferedImage, "jpg", out);
                if (flag) {
                    picBy = out.toByteArray();
                }
                break;
            }
            grabber.stop();
            grabber.close();
        } catch (Exception e) {
            log.error("rtsp流解析失败， {}", e.getMessage());
        }
        return picBy;
    }


    public static String getSnapshotUrl(String url, String cameraId, boolean refresh){
        return getSnapshotUrl(url, cameraId, 5, refresh);
    }

    public static String getSnapshotUrl(String url, String cameraId, int frameIdx,boolean refresh) {
        //cameraId 作为图片的唯一标识
        String picName = java.util.Base64.getEncoder().encodeToString(cameraId.getBytes(StandardCharsets.UTF_8)) + ".jpg";
        HashMap<String, Object> res = new HashMap<>(1);
        if (!refresh){
            res.put("url", getUrl(fileDir  + picName));
            JSONObject jsonObject = new JSONObject(res);
            return jsonObject.toString();
        }
        //获取图片流
        byte[] picStream = catSnapshot(url, frameIdx);
        String saveRes = null;
        if (null != picStream) {
            saveRes = save(picStream, picName);
        }
        if (!StringUtils.isEmpty(saveRes)) {
            JSONObject jsonObject = new JSONObject(res);
            return jsonObject.toString();
        }
        return new JSONObject(Collections.emptyMap()).toString();
    }

    private static String save(byte[] picByte, String filename) {
        //第一次会创建文件夹
        createDirIfNotExists();
        //删除之前的快照
        delete(filename);

        String resultPath = fileDir  + filename;

        //存文件
        File uploadFile = new File(absolutePath, staticDir + resultPath);
        try {
            FileUtils.writeByteArrayToFile(uploadFile, picByte);
        } catch (IOException e) {
            log.error("快照写入失败, {}", e.getMessage());
            return null;
        }

        return resultPath;
    }

    private static void createDirIfNotExists() {
        if (!absolutePath.isEmpty()) {return;}

        //获取跟目录
        File file = null;
        try {
            file = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            log.error("获取根目录失败，无法创建上传目录！");
            return;
        }
        if(!file.exists()) {
            file = new File("");
        }

        absolutePath = file.getAbsolutePath();

        File upload = new File(absolutePath, staticDir + fileDir);
        if(!upload.exists()) {
            upload.mkdirs();
        }
    }

    private static String getUrl(String url){
        String hostAddress ;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostAddress = addr.getHostAddress();
        }catch (UnknownHostException e){
            hostAddress = "127.0.0.1";
        }

        String port = "8080";

        return "http://"+hostAddress+":"+port+url;
    }

    private static Boolean delete(String path){
        File file = new File(absolutePath, staticDir+fileDir+path);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static void main(String[] args) {
        String url = getSnapshotUrl("rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp",
                "1576767493292", true);
        log.info(url);
    }
}
