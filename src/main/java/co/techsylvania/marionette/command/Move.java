package co.techsylvania.marionette.command;

public class Move extends ConcreteCommand {

    private CommandType direction;

    public CommandType getDirection() {
        return direction;
    }
}
