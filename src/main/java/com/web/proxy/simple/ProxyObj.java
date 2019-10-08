package com.web.proxy.simple;

public class ProxyObj implements InterFace {

    private InterFace proxied;

    public ProxyObj(InterFace proxied) {
        this.proxied = proxied;
    }

    @Override
    public void getName() {
        System.out.println("Proxy Class");
        proxied.getName();
    }

    @Override
    public String getNameById(String id) {
        System.out.println("proxy Class");
        return proxied.getNameById(id);
    }

}
