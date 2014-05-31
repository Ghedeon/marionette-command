package co.techsylvania.marionette.command;

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
