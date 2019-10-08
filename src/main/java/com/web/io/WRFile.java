package com.web.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class WRFile {

    public static void main(String[] args) throws IOException {
        File file = new File("./text.txt");
        if (file.exists()) {
            //读取流
            FileInputStream fis = new FileInputStream(file);
            //以指定字符集读取文件流
            InputStreamReader isr = new InputStreamReader(fis, "GBK");
            //建立Buffered缓冲区域
            BufferedReader bd = new BufferedReader(isr);

            String str = null;
            //字符串的形式读取文件
            while ((str = bd.readLine()) != null) {
                System.out.println(str);
            }

            bd.close();
            isr.close();
            fis.close();  //关闭文件流的操作


            File file2 = new File("./text2.txt");
            //输出流
            FileOutputStream fos = new FileOutputStream(file2);
            //将文件 --GBK--》文件流
            OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");
            //建立Buffered缓冲区域
            BufferedWriter bw = new BufferedWriter(osw);
            //写入字符（字符串）
            for (int i = 0; i < 10; i++) {
                bw.write(i * 341 + "\n");
            }


            bw.close();
            osw.close();
            fos.close();
        } else {
            file.createNewFile();
        }

    }

}
