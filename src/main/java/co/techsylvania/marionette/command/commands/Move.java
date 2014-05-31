package co.techsylvania.marionette.command.commands;

public class Move extends ConcreteCommand {

    private CommandType direction;

    public Move(CommandType commandType) {
        this.direction = commandType;
    }

    public CommandType getDirection() {
        return direction;
    }
}
