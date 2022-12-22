package de.hawh.beta3.application.game.model.gamelogic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {
    IGameLogic gameLogic;
    List<Player> players;

    @BeforeEach
    void setUp() {
        gameLogic = new GameLogic();
        gameLogic.init(4, 40, 3);
        players = gameLogic.getPlayers();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void init() {
        assertSame(gameLogic.getGameState(), GameState.RUNNING);
        assertTrue(players.stream().allMatch(Player::isAlive));

        List<Position> startingPos = new ArrayList<>();
        for (Player p : players) startingPos.add(p.getFront());
        assertEquals(startingPos.stream().distinct().count(), startingPos.size());

        List<Direction> startingDir = new ArrayList<>();
        for (Player p : players) startingDir.add(p.getDirection());
        assertEquals(startingDir.stream().distinct().count(), startingDir.size());

        //6 players
        gameLogic.init(6, 40, 3);
        players = gameLogic.getPlayers();

        startingPos = new ArrayList<>();
        for (Player p : players) startingPos.add(p.getFront());
        assertEquals(startingPos.stream().distinct().count(), startingPos.size());

        startingDir = new ArrayList<>();
        for (Player p : players) startingDir.add(p.getDirection());
        assertNotEquals(startingDir.stream().distinct().count(), startingDir.size());
        assertEquals(startingDir.stream().distinct().count(), startingDir.size() - 2);
    }

    @Test
    void initCorrectStartingPositions() {
        //4 players size 40
        List<Position> startingPos = new ArrayList<>();
        for (Player p : players) startingPos.add(p.getFront());
        assertEquals(startingPos.get(0), new Position(0, 19));
        assertEquals(startingPos.get(1), new Position(39, 19));
        assertEquals(startingPos.get(2), new Position(19, 0));
        assertEquals(startingPos.get(3), new Position(19, 39));

        //3 players size 53
        gameLogic.init(4, 53, 3);
        players = gameLogic.getPlayers();
        startingPos = new ArrayList<>();
        for (Player p : players) startingPos.add(p.getFront());
        assertEquals(startingPos.get(0), new Position(0, 26));
        assertEquals(startingPos.get(1), new Position(52, 26));
        assertEquals(startingPos.get(2), new Position(26, 0));

        //6 players size 40
        gameLogic.init(6, 40, 3);
        players = gameLogic.getPlayers();
        startingPos = new ArrayList<>();
        for (Player p : players) startingPos.add(p.getFront());
        assertEquals(startingPos.get(0), new Position(0, 13));
        assertEquals(startingPos.get(1), new Position(39, 13));
        assertEquals(startingPos.get(2), new Position(19, 0));
        assertEquals(startingPos.get(3), new Position(19, 39));
        assertEquals(startingPos.get(4), new Position(0, 26));
        assertEquals(startingPos.get(5), new Position(39, 26));
    }

    @Test
    void changePlayerDirection() {
        Player player = players.get(0);
        Position posPre = player.getFront();
        //player 0 move left
        gameLogic.changePlayerDirection(0, "left");
        assertNotEquals(player.getFront(), posPre);
        assertSame(player.getDirection(), Direction.UP);
        //try to move left again -> no change
        gameLogic.changePlayerDirection(0, "left");
        assertNotEquals(player.getFront(), posPre);
        assertSame(player.getDirection(), Direction.UP);
        //player 1 dead --> no change
        players.get(1).setDead();
        player = players.get(1);
        posPre = player.getFront();
        gameLogic.changePlayerDirection(1, "left");
        assertEquals(player.getFront(), posPre);
        assertSame(player.getDirection(), Direction.LEFT);
    }

    @Test
    void updatePlayersEveryPlayerMoved() {
        List<Position> posPre = new ArrayList<>();
        for (Player p : players) posPre.add(p.getFront());
        gameLogic.updatePlayers();
        List<Position> posPost = new ArrayList<>();
        for (Player p : players) posPost.add(p.getFront());
        posPre.retainAll(posPost);   //only keeps any same values from two lists
        assertEquals(0, posPre.size());    //every position should be different
    }

    @Test
    void updatePlayersOneDead() {
        //Kill one player
        List<Position> posPre = new ArrayList<>();
        for (Player p : players) posPre.add(p.getFront());
        players.get(0).setDead();
        gameLogic.updatePlayers();
        List<Position> posPost = new ArrayList<>();
        for (Player p : players) posPost.add(p.getFront());
        posPre.retainAll(posPost);   //only keeps any same values from two lists
        assertNotEquals(posPre.size(), posPost.size());
        assertEquals(posPost.size() - 3, posPre.size());
        assertFalse(players.get(0).isAlive());
    }

    @Test
    void updatePlayersCollisionWithWall() {
        //one player steers right twice and drives into wall
        gameLogic.changePlayerDirection(0, "right");
        gameLogic.updatePlayers();
        gameLogic.changePlayerDirection(0, "right");
        gameLogic.updatePlayers();
        assertFalse(players.get(0).isAlive());
    }

    @Test
    void updatePlayersCollisionWithSelf() {
        //one player steers right three times and drives into own trail
        //another players steers left three times for same result
        gameLogic.updatePlayers();
        gameLogic.changePlayerDirection(0, "right");
        gameLogic.changePlayerDirection(1, "left");
        gameLogic.updatePlayers();
        gameLogic.changePlayerDirection(0, "right");
        gameLogic.changePlayerDirection(1, "left");
        gameLogic.updatePlayers();
        gameLogic.changePlayerDirection(0, "right");
        gameLogic.changePlayerDirection(1, "left");
        gameLogic.updatePlayers();
        assertFalse(players.get(0).isAlive());
        assertFalse(players.get(1).isAlive());
    }
}