package co.techsylvania.marionette.command.commands;

import java.io.Serializable;

public class Command implements Serializable {

    private CommandType commandType;
    private byte[] concreteCommand;

    public Command(CommandType commandType, byte[] concreteCommand) {
        this.commandType = commandType;
        this.concreteCommand = concreteCommand;
    }

    public byte[] getConcreteCommand() {
        return concreteCommand;
    }

    public CommandType getCommandType() {
        return commandType;
    }
}
