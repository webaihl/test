package com.web.designpattern.command;

public class AllClasses {
}

class Light {
    String address;

    public Light(String address) {
        this.address = address;
    }

    public void on() {
        System.out.println("Living Room light is off!");
    }

    public void off() {
        System.out.println("Living Room light is off!");
    }
}

class LightOffCommand implements Command {

    Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }
}

class LightOnCommand implements Command {

    Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }
}