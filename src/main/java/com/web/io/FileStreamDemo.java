package com.web.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileStreamDemo {

    //装饰器模式
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // try(source)自动关闭资源
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream("text.txt"))) {
            byte[] buffer = new byte[1024];
            int n = 0;
            while ((n = inputStream.read(buffer)) != -1) {
                System.out.println(new String(buffer, "GBK"));
            }
        }
    }
}
