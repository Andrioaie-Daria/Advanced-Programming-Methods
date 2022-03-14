package view;

import controller.Controller;

public class RunExampleCommand extends Command {
    private Controller controller;
    public RunExampleCommand(String key, String description, Controller ctr){
        super(key, description);
        this.controller = ctr;
    }
    @Override
    public void execute() {
        try{
            controller.completeExecution(); }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}