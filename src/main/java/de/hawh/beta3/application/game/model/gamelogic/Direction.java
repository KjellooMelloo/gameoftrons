package de.hawh.beta3.application.game.model.gamelogic;

enum Direction {
    LEFT, UP, RIGHT, DOWN;

    /**
     * Returns the next direction in cycle LEFT-UP-RIGHT-DOWN
     *
     * @return next direction in order
     */
    public Direction next() {
        return Direction.values()[this.ordinal() == 3 ? 0 : this.ordinal() + 1];
    }

    /**
     * Returns the previous direction in cycle LEFT-UP-RIGHT-DOWN
     *
     * @return next direction in order
     */
    public Direction prev() {
        return Direction.values()[this.ordinal() == 0 ? 3 : this.ordinal() - 1];
    }
}
