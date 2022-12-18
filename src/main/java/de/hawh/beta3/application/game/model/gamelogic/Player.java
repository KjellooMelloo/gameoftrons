package de.hawh.beta3.application.game.model.gamelogic;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int color;
    private boolean isAlive;
    private Position front;
    private List<Position> trail;
    private Direction direction;
    private String currentAction;

    public Player (int color, Position start, Direction direction) {
        this.color = color;
        this.front = start;
        this.direction = direction;
        isAlive = true;
        trail = new ArrayList<>();
    }

    /* Getter and Setter */

    public int getColor() {
        return color;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setDead() {
        isAlive = false;
    }

    public Position getFront() {
        return front;
    }

    public void setFront(Position front) {
        this.front = front;
    }

    public List<Position> getTrail() {
        return trail;
    }

    public void addFrontToTrail() {

    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }
}
