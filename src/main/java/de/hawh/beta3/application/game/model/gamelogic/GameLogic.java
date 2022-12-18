package de.hawh.beta3.application.game.model.gamelogic;

import java.util.List;

public class GameLogic implements IGameLogic {

    private int size;
    private List<Player> players;
    private int gameWinner;
    private GameState gameState;

    /**
     * Initializes game logic with params
     *
     * @param numPlayers number of Players (2-6)
     * @param size       size of game field
     * @param gameSpeed  speed of game
     */
    @Override
    public void init(int numPlayers, int size, int gameSpeed) {
        this.size = size;
        //this.gameSpeed = gameSpeed;
        Position[] startingPos;
        Direction[] startingDir;
        if (numPlayers > 4) {
            startingPos = new Position[]{
                    new Position(0, (size-1)/3),
                    new Position((size-1), (size-1)/3),
                    new Position((size-1)/2, 0),
                    new Position((size-1)/2, (size-1)),
                    new Position(0, 2*(size-1)/3),
                    new Position((size-1), 2*(size-1)/3)
            };
            startingDir = new Direction[] {
                    Direction.RIGHT,
                    Direction.LEFT,
                    Direction.DOWN,
                    Direction.UP,
                    Direction.RIGHT,
                    Direction.LEFT
            };
        } else {
            startingPos = new Position[]{
                    new Position(0, (size-1)/2),
                    new Position((size-1), (size-1)/2),
                    new Position((size-1)/2, 0),
                    new Position((size-1)/2, (size-1))
            };
            startingDir = new Direction[] {
                    Direction.RIGHT,
                    Direction.LEFT,
                    Direction.DOWN,
                    Direction.UP
            };
        }

        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(i, startingPos[i], startingDir[i]));
        }
    }

    /**
     * Method for changing direction of a player with <code>id</code>
     *
     * @param id     id of player
     * @param action "left" or "right"
     */
    @Override
    public void changePlayerDirection(int id, String action) {

    }

    /**
     * Updates the position of every player
     */
    @Override
    public void updatePlayers() {

    }

    /**
     * Return current game state <code>INIT, RUNNING, OVER</code>
     *
     * @return game state enum
     */
    @Override
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Returns the id of the winner or -1 if tied
     *
     * @return id or -1 (tie)
     */
    @Override
    public int getGameWinner() {
        return gameWinner;
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    private Player getPlayerById(int id) {
        return null;
    }

    private Position calcNextPos(Position position, Direction direction) {
        return null;
    }

    private void movePlayer(Player p) {
        Direction d = Direction.RIGHT;

    }

    private void setGameState(GameState gameState) {

    }

    private int getNumLivingPlayers() {
        return 0;
    }

    private boolean checkForCollision(Player p) {
        return false;
    }

    private void killPlayers (List<Player> players) {

    }
}
