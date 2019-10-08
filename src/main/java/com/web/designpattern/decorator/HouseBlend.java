package com.web.designpattern.decorator;

/**
 * 咖啡类
 */

public class HouseBlend extends Beverage {

    public HouseBlend() {
        desc = "HouseBlend";
    }

    @Override
    public double cost() {
        return 1.99;
    }
}
