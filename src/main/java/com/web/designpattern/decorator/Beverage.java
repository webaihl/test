package com.web.designpattern.decorator;

public abstract class Beverage {
    String desc = "UNKOWN Beverage";

    public String getDesc() {
        return this.desc;
    }

    public abstract double cost();
}
