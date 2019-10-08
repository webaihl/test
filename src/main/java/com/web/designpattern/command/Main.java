package com.web.designpattern.command;

public class Main {
    public static void main(String[] args) {
        RemoControl remoControl = new RemoControl();//遥控器--调用者

        Light light = new Light("Living Room"); //操作执行者

        LightOffCommand lightOffCommand = new LightOffCommand(light); //请求对象-->命令对象
        LightOnCommand lightOnCommand = new LightOnCommand(light);

        //调用者与命令对象的关联
        remoControl.setCommands(0, lightOnCommand, lightOffCommand);

        System.out.println(remoControl);

        remoControl.onButtonWasPushed(0);
        remoControl.offButtonWasPushed(0);
    }
}
