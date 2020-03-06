package com.web.thread;



import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicInteger;

/***
 *
 * https://juejin.im/entry/5ae05aa6f265da0ba062d5b6
 */
public class HttpDownloader {

    private URL url;
    private File localFile; //存储位置
    private final Object waiting = new Object();
    private AtomicInteger downloadedBytes = new AtomicInteger(0);//已经下载的文件大小
    private AtomicInteger aliveThreads = new AtomicInteger(0);//当前剩下的线程数量
    private boolean multiThreadModel = true;//是否多线程模式
    private int fileSize = 0;
    private int THREAD_NUM = 5;
    private int TIME_OUT = 5000;
    private RandomAccessFile accessFile;

    public static void main(String[] args) throws IOException {
        String url = "https://down5.huorong.cn/sysdiag-all-5.0.17.5.exe";
        new HttpDownloader(url, "E:/uu.exe", 5, 5000).get();
    }

    public HttpDownloader(String Url, String localPath) throws MalformedURLException, FileNotFoundException {
        this.url = new URL(Url);
        this.localFile = new File(localPath);
        this.accessFile = new RandomAccessFile(localPath, "rw");
    }

    public HttpDownloader(String Url, String localPath,
                          int threadNum, int timeout) throws MalformedURLException, FileNotFoundException {
        this(Url, localPath);
        this.THREAD_NUM = threadNum;
        this.TIME_OUT = timeout;
        this.accessFile = new RandomAccessFile(localPath, "rw");
    }

    //开始下载文件
    public void get() throws IOException {
        long startTime = System.currentTimeMillis();

        //是否支持断点续传
        boolean resumable = supportResumeDownload();
        //低于2MB的使用单线程模式
        int MIN_SIZE = 2 << 20;
        if (!resumable || THREAD_NUM == 1|| fileSize < MIN_SIZE) multiThreadModel = false;
        if (!multiThreadModel) {
            new DownloadThread(0, 0, fileSize - 1, accessFile.getChannel()).start();;
        }
        else {
            //每个块的结束点
            int[] endPoint = new int[THREAD_NUM + 1];
            int block = fileSize / THREAD_NUM;
            for (int i = 0; i < THREAD_NUM; i++) {
                endPoint[i] = block * i;
            }
            //文件大小不是分块的整数倍，最后存储最后存储文件大小
            endPoint[THREAD_NUM] = fileSize;

            for (int i = 0; i < THREAD_NUM; i++) {
                new DownloadThread(i, endPoint[i], endPoint[i + 1] - 1, accessFile.getChannel()).start();//每个块开始下载线程
            }
        }

        startDownloadMonitor();

        //阻塞，等待 downloadMonitor 通知下载完成
        try {
            synchronized(waiting) {
                waiting.wait();
            }
        } catch (InterruptedException e) {
            System.err.println("Download interrupted.");
        }

        //cleanTempFile();
        accessFile.close();
        long timeElapsed = System.currentTimeMillis() - startTime;
        System.out.println("* File successfully downloaded.");
        System.out.println(String.format("* Time used: %.3f s, Average speed: %d KB/s",
                timeElapsed / 1000.0, downloadedBytes.get() / timeElapsed));
    }

    //检测目标文件是否支持断点续传，以决定是否开启多线程下载文件的不同部分
    public boolean supportResumeDownload() throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Range", "bytes=0-");
        int resCode;
        while (true) {
            try {
                con.connect();
                fileSize = con.getContentLength();
                resCode = con.getResponseCode();
                con.disconnect();
                if(fileSize > Integer.MAX_VALUE)
                    new Throwable("文件太大了， 不支持下载");
                break;
            } catch (ConnectException e) {
                System.out.println("Retry to connect due to connection problem.");
            }
        }
        if (resCode == 206) {
            System.out.println("* Support resume download");
            return true;
        } else {
            System.out.println("* Doesn't support resume download");
            return false;
        }
    }

    //监测下载速度及下载状态，下载完成时通知主线程
    public void startDownloadMonitor() {
        Thread downloadMonitor = new Thread(() -> {
            int prev = 0;
            int curr = 0;
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}

                curr = downloadedBytes.get();
                System.out.println(String.format("Speed: %d KB/s, Downloaded: %d KB (%.2f%%), Threads: %d",
                        (curr - prev) >> 10, curr >> 10, curr / (float) fileSize * 100, aliveThreads.get()));
                prev = curr;
                //下载线程完成，唤醒其他线程
                if (aliveThreads.get() == 0) {
                    synchronized (waiting) {
                        waiting.notifyAll();
                    }
                }
            }
        });

        downloadMonitor.setDaemon(true);
        downloadMonitor.start();
    }

    //对临时文件进行合并或重命名
//    public void cleanTempFile() throws IOException {
//        if (multiThreadModel) {
//            merge();
//            System.out.println("* Temp file merged.");
//        } else {
//            //单线程时使用0下标临时文件
//            Files.move(Paths.get(localFile.getAbsolutePath() + ".0.tmp"),
//                    Paths.get(localFile.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
//        }
//    }

    //合并多线程下载产生的多个临时文件
/*    public void merge() {
        try (OutputStream out = new FileOutputStream(localFile)) {
            byte[] buffer = new byte[1024];
            int size;
            for (int i = 0; i < THREAD_NUM; i++) {
                String tmpFile = localFile.getAbsolutePath() + "." + i + ".tmp";
                InputStream in = new FileInputStream(tmpFile);
                while ((size = in.read(buffer)) != -1) {
                    out.write(buffer, 0, size);
                }
                in.close();
                Files.delete(Paths.get(tmpFile));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    //一个下载线程负责下载文件的某一部分，如果失败则自动重试，直到下载完成
    class DownloadThread extends Thread {
        private int id;
        private int start;
        private int end;
        private FileChannel channel;
        private OutputStream out;


        public DownloadThread(int id, int start, int end, FileChannel channel) {
            this.id = id;
            this.start = start;
            this.end = end;
            this.channel = channel;
            aliveThreads.incrementAndGet();
        }

        //保证文件的该部分数据下载完成
        @Override
        public void run() {
            boolean success;
            while (true) {
                success = download();
                if (success) {
                    System.out.println("* Downloaded part " + (id + 1));
                    break;
                } else {
                    System.out.println("Retry to download part " + (id + 1));
                }
            }
            //下载完成后，线程数量-1
            aliveThreads.decrementAndGet();
        }

        private ByteBuffer generateStaticBytes(byte[] b, int size) {
            ByteBuffer buf = ByteBuffer.allocate(b.length);
            buf.put(b, 0, size);
            buf.flip();
            return buf;
        }

        //下载文件指定范围的部分
        public boolean download() {
            try {
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("Range", String.format("bytes=%d-%d", start, end));
                con.setConnectTimeout(TIME_OUT);
                con.setReadTimeout(TIME_OUT);
                con.connect();
                int partSize = con.getHeaderFieldInt("Content-Length", -1);
                if (partSize != end - start + 1) return false;
                //if (out == null) out = new FileOutputStream(localFile.getAbsolutePath() + "." + id + ".tmp");

                //accessFile.seek(start);
                try (InputStream in = con.getInputStream()) {
                    byte[] buffer = new byte[1024];
                    int size;
                    while (start < end && (size = in.read(buffer)) > 0) {
                        channel.write(generateStaticBytes(buffer,size), start);
                        start += size;
                        downloadedBytes.addAndGet(size);
                       // out.write(buffer, 0, size);
                        //accessFile.write(buffer, 0, size);
                       // out.flush();
                    }
                    con.disconnect();
                    if (start < end) return false;
                    //else out.close();
                }
            } catch(SocketTimeoutException e) {
                System.out.println("Part " + (id + 1) + " Reading timeout.");
                return false;
            } catch (IOException e) {
                System.out.println("Part " + (id + 1) + " encountered error.");
                return false;
            }

            return true;
        }
    }

}