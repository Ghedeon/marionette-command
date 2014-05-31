package co.techsylvania.marionette.command.commands;

public class Rotate extends ConcreteCommand {

    private CommandType around;
    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public CommandType getAround() {
        return around;
    }
}
