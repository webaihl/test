package com.web.designpattern.command;

public class RemoControl {
    Command[] onCommands;
    Command[] offCommands;

    public RemoControl() {
        onCommands = new Command[2];
        offCommands = new Command[2];

        Command noCommand = new NoCommand();
        for (int i = 0; i < 2; i++) {
            offCommands[i] = noCommand;
            onCommands[i] = noCommand;
        }
    }

    public void setCommands(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    public void onButtonWasPushed(int slot) {
        onCommands[slot].execute();
    }

    public void offButtonWasPushed(int slot) {
        offCommands[slot].execute();
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < onCommands.length; i++) {
            stringBuffer.append(onCommands[i].getClass().getName() + " " +
                    offCommands[i].getClass().getName() + "\n"
            );
        }
        return stringBuffer.toString();
    }
}
