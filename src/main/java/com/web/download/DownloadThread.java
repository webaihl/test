package com.web.download;


import com.web.download.api.Connection;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.CountDownLatch;


public class DownloadThread extends Thread {

    Connection conn;
    int startPos;
    int endPos;
    RandomAccessFile currentPart;
    CountDownLatch downLatch;

    public DownloadThread(Connection conn, int startPos, int endPos, RandomAccessFile currentPart, CountDownLatch downLatch) {

        this.conn = conn;
        this.startPos = startPos;
        this.endPos = endPos;
        this.currentPart = currentPart;
        this.downLatch = downLatch;
    }


    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + ": " + this.startPos + " " + this.endPos + " " + (this.endPos - this.startPos));
            byte[] content = this.conn.read(this.startPos, this.endPos);
            this.currentPart.seek(startPos);
            this.currentPart.write(content);
        } catch (IOException e) {
            System.out.println("byte写入失败: " + e.getMessage());
        } finally {
            downLatch.countDown();
        }
    }
}
