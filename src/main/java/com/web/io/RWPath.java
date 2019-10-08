package com.web.io;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class RWPath {

    public static void main(String[] args) {

        String url = Thread.currentThread().getContextClassLoader().getResource("resource/test.txt").getPath();

        System.out.println(RWPath.class.getResource("").getPath());//包所在的目录
        System.out.println(RWPath.class.getResource("/").getPath());//classes目录
        //System.out.println(RWPath.class.getResource("resource/test.txt").getPath());//null
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());//classes目录 src
        //System.out.println(Thread.currentThread().getContextClassLoader().getResource("/").getPath());//null
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("resource/test.txt").getPath());
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(url))) {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = bis.read(bytes)) > -1) {
                System.out.println(new String(bytes, 0, len));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(url))) {
            bos.write("这是一个新增的行----".getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
