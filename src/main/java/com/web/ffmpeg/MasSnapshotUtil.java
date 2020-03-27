package com.web.ffmpeg;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.java_websocket.drafts.Draft_6455;
import org.springframework.util.StringUtils;

import static org.bytedeco.javacpp.avutil.AV_LOG_PANIC;
import static org.bytedeco.javacpp.avutil.av_log_set_level;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class MasSnapshotUtil {

    /**
     * @param url
     * @return Base64 字符串
     */
    public static String getMasSnapshotBase64(String url) throws Exception {
        return Base64.encodeBase64String(getMasSnapshotByte(url));
    }

    /**
     * @param url
     * @return byte 数组
     */
    public static byte[] getMasSnapshotByte(String url) {
        byte[] pictureByte = null;
        Pattern p = Pattern.compile("vas://name=([^&]*)&psw=([^&]*)&srvip=([^&]*)&srvport=([^&]*)&devid=([^&]*)&");
        Matcher m = p.matcher(url);
        String masMasterIp = null;
        String masMasterPort = null;
        String channelId = null;
        if (m.find()) {
            masMasterIp = m.group(3);
            masMasterPort = m.group(4);
            channelId = m.group(5);
        } else {
            return new byte[0];
        }

        try {
            String masNodeIp = getMasNode(masMasterIp, masMasterPort);

            pictureByte = getPlayStream(masNodeIp, channelId);

            if (pictureByte != null) {
                return pictureByte;
            }
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 向Mas node请求2s的播放流
     *
     * @param masNodeIp
     * @param channelId
     * @return byte[]
     * @throws Exception
     */
    private static byte[] getPlayStream(String masNodeIp, String channelId) throws Exception {
        WebsocketClient websocketClient;
        JSONObject param;
        String wsAddr = "ws://" + masNodeIp;
        String nodeIp = masNodeIp.split(":")[0];
        websocketClient = new WebsocketClient(new URI(wsAddr), new Draft_6455());
        websocketClient.connectBlocking();
        param = new JSONObject();
        param.put("request", "play");
        param.put("seq", 123);
        param.put("channel", channelId);
        param.put("type", "real_Platform");
        param.put("startTime", "");
        param.put("stopTime", "");
        param.put("nodeIP", nodeIp);
        param.put("nodePort", 0);
        param.put("serialNum", "");
        param.put("use", "");

        log.info("websocket param :" + param.toString());
        websocketClient.send(param.toString());

        String msg = websocketClient.getResponse();
        while (StringUtils.isEmpty(msg)) {
            msg = websocketClient.getResponse();
            Thread.sleep(500L);
        }
        JSONObject jsonResp = JSONObject.parseObject(msg);
        param = new JSONObject();
        param.put("request", "stop");
        param.put("seq", 123);
        param.put("channel", channelId);
        param.put("callId", jsonResp.get("callId"));

        log.info("websocket param :" + param.toString());
        websocketClient.send(param.toString());

        websocketClient.closeBlocking();

        List<byte[]> masByteList = websocketClient.getMasByteList();
        log.info("收到流数量:" + masByteList.size());

        int framesLen = 0;
        for (byte[] bytes : masByteList) {
            framesLen += bytes.length;
        }
        byte[] framesBy = new byte[framesLen];
        framesLen = 0;
        for (byte[] bytes : masByteList) {
            System.arraycopy(bytes, 0, framesBy, framesLen, bytes.length);
            framesLen += bytes.length;
        }

        InputStream inputStream = new ByteArrayInputStream(framesBy);
        return getByteFromFfmpeg(inputStream);
    }

    /**
     * 利用ffmpeg解出一个i帧
     *
     * @param inputStream
     * @return byte[]  一个i帧
     * @throws IOException
     */
    private static byte[] getByteFromFfmpeg(InputStream inputStream) throws Exception {
        byte[] picBy = null;
        av_log_set_level(AV_LOG_PANIC);
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream);
        grabber.setOption("stimeout", "5000000");
        try {
            grabber.start();
            Frame frame = null;
            int cnt = 0, frameLen = grabber.getFrameNumber();
            while (true) {
                Frame tmpframe = grabber.grabFrame();
                if (tmpframe == null || tmpframe.image == null) {
                    continue;
                }
                if (tmpframe.keyFrame) {
                    frame = tmpframe;
                    break;
                }
            }
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage bufferedImage = converter.getBufferedImage(frame);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            boolean flag = ImageIO.write(bufferedImage, "jpg", out);
            if (flag) {
                picBy = out.toByteArray();
            }
            grabber.stop();
            grabber.close();
        } catch (Exception e) {
            log.error("vas流解析失败， {}", e.getMessage());
        }

        return picBy;
    }

    /**
     * MAS节点地址请求
     *
     * @param masMasterIp
     * @param masMasterPort
     * @return mas node的ip及端口
     */
    private static String getMasNode(String masMasterIp, String masMasterPort) {
        WebsocketClient websocketClient = null;
        JSONObject param;
        String masNodeIp = null;
        String wsAddr = "ws://" + masMasterIp + ":" + masMasterPort;
        try {
            websocketClient = new WebsocketClient(new URI(wsAddr), new Draft_6455());
            websocketClient.connectBlocking();
            param = new JSONObject();
            param.put("request", "nodeAddr");
            param.put("seq", 0);
            param.put("nodeType", "GB28181");
            websocketClient.send(param.toString());
            Thread.sleep(200L);
            String response = websocketClient.getResponse();
            log.info("收到nodeAddr服务端消息:" + response);
            if (!StringUtils.isEmpty(response)) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                masNodeIp = jsonObject.get("nodeIp") + ":" + jsonObject.get("port");
            }
        } catch (Exception e) {
            log.error("mas节点地址转换失败,{}", e.getMessage());
        } finally {
            if (websocketClient != null) {
                try {
                    websocketClient.closeBlocking();
                } catch (InterruptedException e) {
                    log.error("websocketClient关闭失败,{}", e.getMessage());
                }
            }
        }
        return masNodeIp;
    }
}
