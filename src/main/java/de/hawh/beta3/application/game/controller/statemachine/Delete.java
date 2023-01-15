package de.hawh.beta3.application.game.controller.statemachine;


public class Delete implements State {

    IContext context;

    public Delete() {
        this.context = Context.getInstance();
        this.behavior();
    }

    @Override
    public void behavior() {
        context.setCurrentState("START");
    }

}
