package com.web.proxy.simple;

public class RealObj implements InterFace {

    @Override
    public void getName() {
        System.out.println("name = web");
    }

    @Override
    public String getNameById(String id) {
        System.out.println("id = " + id);
        return "web";
    }

}
