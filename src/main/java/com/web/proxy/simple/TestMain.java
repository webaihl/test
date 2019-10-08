package com.web.proxy.simple;

public class TestMain {

    public static void main(String[] args) {

        consume(new RealObj());
        System.out.println("==========");
        consume(new ProxyObj(new RealObj()));
    }

    public static void consume(InterFace interFace) {
        interFace.getName();
        String res = interFace.getNameById("100");
        System.out.println("name = " + res);
    }
}
