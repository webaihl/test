package com.web.download;

import com.web.download.api.ConnectionManager;
import com.web.download.impl.ConnectionManagerImpl;


public class FileDownloaderTest {
    private static boolean downloadFinished = false;

    public static void main(String[] args) {

        String url = "https://down5.huorong.cn/sysdiag-all-5.0.17.5.exe";
//        url = "http://mirrors.163.com/debian/ls-lR.gz";
//        url = "http://www.hinews.cn/pic/0/13/91/26/13912621_821796.jpg";
        url = "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png";

        FileDownloader downloader = new FileDownloader(url);


        ConnectionManager cm = new ConnectionManagerImpl();
        downloader.setConnectionManager(cm);

        downloader.setListener(() -> downloadFinished = true);

        downloader.execute();

        // 等待多线程下载程序执行完毕
        while (!downloadFinished) {
            try {
                System.out.println("还没有下载完成，休眠五秒");
                //休眠5秒
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("下载完成！");


    }

}
