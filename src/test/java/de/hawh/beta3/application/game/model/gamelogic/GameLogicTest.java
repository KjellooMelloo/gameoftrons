package de.hawh.beta3.application.game.model.gamelogic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {
    IGameLogic gameLogic;

    @BeforeEach
    void setUp() {
        gameLogic = new GameLogic();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void init() {
        //4 players
        assertSame(gameLogic.getGameState(), GameState.INIT);
        gameLogic.init(4, 40, 3);
        List<Player> players = gameLogic.getPlayers();
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
    void changePlayerDirection() {
        gameLogic.init(4, 40, 3);
        List<Player> players = gameLogic.getPlayers();
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
    void updatePlayers() {
        //TODO
    }
}