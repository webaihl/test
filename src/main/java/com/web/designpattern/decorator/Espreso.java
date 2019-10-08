package com.web.designpattern.decorator;

/**
 * 咖啡类
 */
public class Espreso extends Beverage {
    public Espreso() {
        desc = "Espreso";
    }

    @Override
    public double cost() {
        return .89;
    }
}
