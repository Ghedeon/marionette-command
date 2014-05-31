package co.techsylvania.marionette.command;

public class Command {

    private CommandType commandType;
    private byte[] concreteCommand;

    public byte[] getConcreteCommand() {
        return concreteCommand;
    }

    public CommandType getCommandType() {
        return commandType;
    }
}
