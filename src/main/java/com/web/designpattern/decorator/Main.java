package com.web.designpattern.decorator;

public class Main {
    public static void main(String[] args) {
        Beverage beverage = new Espreso();//Espreso咖啡
        beverage = new Mocha(beverage);//加Mocha 0.89+0.2
        System.out.println(beverage.getDesc() + " $" + beverage.cost());
    }
}
