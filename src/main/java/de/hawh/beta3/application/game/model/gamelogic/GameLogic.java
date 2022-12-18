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
                    new Position(0, (size - 1) / 3),
                    new Position((size - 1), (size - 1) / 3),
                    new Position((size - 1) / 2, 0),
                    new Position((size - 1) / 2, (size - 1)),
                    new Position(0, 2 * (size - 1) / 3),
                    new Position((size - 1), 2 * (size - 1) / 3)
            };
            startingDir = new Direction[]{
                    Direction.RIGHT,
                    Direction.LEFT,
                    Direction.DOWN,
                    Direction.UP,
                    Direction.RIGHT,
                    Direction.LEFT
            };
        } else {
            startingPos = new Position[]{
                    new Position(0, (size - 1) / 2),
                    new Position((size - 1), (size - 1) / 2),
                    new Position((size - 1) / 2, 0),
                    new Position((size - 1) / 2, (size - 1))
            };
            startingDir = new Direction[]{
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
        //if (id < 0 || id > players.size()) return; //needed?
        Player p = getPlayerById(id);

        if (p.isAlive() && p.getCurrentAction() == null) {
            Position pos = calcNextPos(p.getFront(), p.getDirection(), action);
            p.setFront(pos);
            p.setDirection(action.equals("left") ? p.getDirection().prev() : p.getDirection().next());
            p.setCurrentAction(action);
        }
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

    /**
     * Method is optimistic id is correct. It's called <code>getPlayerById</code> and not <code>findPlayerById</code>
     *
     * @param id id of player to find
     * @return player with id
     */
    private Player getPlayerById(int id) {
        return players.stream().filter(p -> p.getColor() == id).findFirst().get();
    }

    /**
     * Calculates the next position given by current <code>position</code> and turn in <code>direction</code>
     *
     * @param position   current position
     * @param pDirection direction of player
     * @param action     "left", "right" or null
     * @return new position
     */
    private Position calcNextPos(Position position, Direction pDirection, String action) {
        int[] xyPositionChange = new int[]{-1, 0, 0, -1, 1, 0, 0, 1};
        Direction resDirection;

        if (action.equals("left")) {
            resDirection = pDirection.prev();
        } else if (action.equals("right")) {
            resDirection = pDirection.next();
        } else {
            resDirection = pDirection;
        }

        int x = position.getX() + xyPositionChange[2 * resDirection.ordinal()];
        int y = position.getY() + xyPositionChange[2 * resDirection.ordinal() + 1];

        return new Position(x, y);
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

    private void killPlayers(List<Player> players) {

    }
}
