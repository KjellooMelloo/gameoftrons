package de.hawh.beta3.application.game.model.gamelogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameLogic implements IGameLogic {

    private int size;
    private List<Player> players;
    private int gameWinner;
    private GameState gameState = GameState.INIT;

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
        players = new ArrayList<>();
        //this.gameSpeed = gameSpeed;
        Position[] startingPos = getStartingPositions(numPlayers);
        Direction[] startingDir = getStartingDirections(numPlayers);

        for (int i = 0; i < numPlayers; i++) {
            Player p = new Player(i, startingPos[i], startingDir[i]);
            p.addFrontToTrail();
            players.add(p);
        }

        gameState = GameState.RUNNING;
        gameWinner = -1;
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
        List<Player> playersToKill = new ArrayList<>();

        for (Player p : players) {
            if (p.isAlive()) {
                //move player, if he didn't press anything this tick
                if(p.getCurrentAction() == null) {
                    p.setFront(calcNextPos(p.getFront(), p.getDirection(), "stay"));
                }
                if (checkForCollision(p)) {
                    playersToKill.add(p);
                } else {
                    movePlayer(p);
                }
            }
        }
        killPlayers(playersToKill);
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
     * Creates fair starting points for players for any player size
     *
     * @param numPlayers number of players
     * @return Position Array with starting positions
     */
    private Position[] getStartingPositions(int numPlayers) {
        Position[] startingPos;

        if (numPlayers < 5) {
            startingPos = new Position[]{
                    new Position(0, (size - 1) / 2),
                    new Position((size - 1), (size - 1) / 2),
                    new Position((size - 1) / 2, 0),
                    new Position((size - 1) / 2, (size - 1))
            };
        } else {
            startingPos = new Position[]{
                    new Position(0, (size - 1) / 3),
                    new Position((size - 1), (size - 1) / 3),
                    new Position((size - 1) / 2, 0),
                    new Position((size - 1) / 2, (size - 1)),
                    new Position(0, 2 * (size - 1) / 3),
                    new Position((size - 1), 2 * (size - 1) / 3)
            };
        }

        return Arrays.copyOf(startingPos, numPlayers);
    }

    /**
     * Returns each <code>Direction</code> corresponding to starting position
     *
     * @param numPlayers number of players
     * @return Direction Array with starting directions
     */
    private Direction[] getStartingDirections(int numPlayers) {
        Direction[] startingDir = new Direction[]{
                Direction.RIGHT,
                Direction.LEFT,
                Direction.DOWN,
                Direction.UP,
                Direction.RIGHT,
                Direction.LEFT
        };

        return Arrays.copyOf(startingDir, numPlayers);
    }

    /**
     * Method is optimistic id is correct. It's called <code>getPlayerById</code> and not <code>findPlayerById</code>
     *
     * @param id id of player to find
     * @return player with id
     */
    private Player getPlayerById(int id) {
        return players.get(id);
    }

    /**
     * Calculates the next position given by current <code>position</code> and turn in <code>direction</code>
     *
     * @param position   current position
     * @param pDirection direction of player
     * @param action     "left", "right" or "stay"
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

    /**
     * Method moves Player <code>p</code> by adding front to the tail.
     * His <code>currentAction</code> gets reset and his new front gets set, in case there is no input next tick
     *
     * @param p player to move
     */
    private void movePlayer(Player p) {
        p.addFrontToTrail();
        p.setCurrentAction(null);
    }

    private void setGameOver() {
        this.gameState = GameState.OVER;
    }

    private int getNumLivingPlayers() {
        return (int) players.stream().filter(Player::isAlive).count();
    }

    /**
     * Methods checks for a collision for Player <code>p</code>
     * Possible collisions are: out of map bounds, drove in own trail, drove in other trail, drove in other front
     *
     * @param p player to check
     * @return true, if collision detected, false else
     */
    private boolean checkForCollision(Player p) {
        Position front = p.getFront();

        if (front.getX() > size - 1 || front.getX() < 0
                || front.getY() > size - 1 || front.getY() < 0
                || p.getTrail().contains(front)) return true;

        for (Player player : players) {
            if (player.isAlive() && player.getColor() != p.getColor()) {
                if (player.getFront().equals(front)) return true;
                if (player.getTrail().contains(front)) return true;
            }
        }

        return false;
    }

    /**
     * Method kills every player in <code>players</code>
     * If this results in a tie or only one player left, <code>gameWinner</code> gets set accordingly
     *
     * @param toBeKilled list of players to be killed
     */
    private void killPlayers(List<Player> toBeKilled) {
        if (toBeKilled.size() == 0) return;
        if (toBeKilled.size() == getNumLivingPlayers()) {
            gameWinner = -1;
            setGameOver();
            return;
        }

        for (Player p : toBeKilled) p.setDead();

        if (getNumLivingPlayers() != 1) return;

        for (Player p : players) {
            if (p.isAlive()) {
                gameWinner = p.getColor();
                setGameOver();
                return;
            }
        }
    }
}
