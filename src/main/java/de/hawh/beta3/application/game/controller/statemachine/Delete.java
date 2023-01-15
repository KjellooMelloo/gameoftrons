package de.hawh.beta3.application.game.controller.statemachine;


public class Delete implements State {

    IContext context;

    public Delete() {
        this.behavior();
        this.context = Context.getInstance();
    }

    @Override
    public void behavior() {
        context.setCurrentState("START");
    }

}
