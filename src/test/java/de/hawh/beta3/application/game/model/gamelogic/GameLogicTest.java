package de.hawh.beta3.application.game.model.gamelogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {
    GameLogic gameLogic;
    int[][] players;

    @BeforeEach
    void setUp() {
        gameLogic = new GameLogic();
        gameLogic.init(4, 40, 3);
        players = gameLogic.getPlayerPositions();
    }

    @Test
    void init() {
        assertSame(gameLogic.getGameState(), "RUNNING");
        assertTrue(Arrays.stream(players).noneMatch(p -> p[1] == -1 || p[2] == -1));    //every player is alive

        List<Position> startingPos = new ArrayList<>();
        for (int[] p : players) startingPos.add(new Position(p[1], p[2]));
        assertEquals(startingPos.stream().distinct().count(), startingPos.size());

        //6 players
        gameLogic.init(6, 40, 3);
        players = gameLogic.getPlayerPositions();

        startingPos = new ArrayList<>();
        for (int[] p : players) startingPos.add(new Position(p[1], p[2]));
        assertEquals(startingPos.stream().distinct().count(), startingPos.size());
    }

    @Test
    void initCorrectStartingPositions() {
        //4 players size 40
        List<Position> startingPos = new ArrayList<>();
        for (int[] p : players) startingPos.add(new Position(p[1], p[2]));
        assertEquals(startingPos.get(0), new Position(0, 19));
        assertEquals(startingPos.get(1), new Position(39, 19));
        assertEquals(startingPos.get(2), new Position(19, 0));
        assertEquals(startingPos.get(3), new Position(19, 39));

        //3 players size 53
        gameLogic.init(4, 53, 3);
        players = gameLogic.getPlayerPositions();
        startingPos = new ArrayList<>();
        for (int[] p : players) startingPos.add(new Position(p[1], p[2]));
        assertEquals(startingPos.get(0), new Position(0, 26));
        assertEquals(startingPos.get(1), new Position(52, 26));
        assertEquals(startingPos.get(2), new Position(26, 0));

        //6 players size 40
        gameLogic.init(6, 40, 3);
        players = gameLogic.getPlayerPositions();
        startingPos = new ArrayList<>();
        for (int[] p : players) startingPos.add(new Position(p[1], p[2]));
        assertEquals(startingPos.get(0), new Position(0, 13));
        assertEquals(startingPos.get(1), new Position(39, 13));
        assertEquals(startingPos.get(2), new Position(19, 0));
        assertEquals(startingPos.get(3), new Position(19, 39));
        assertEquals(startingPos.get(4), new Position(0, 26));
        assertEquals(startingPos.get(5), new Position(39, 26));
    }

    @Test
    void changePlayerDirection() {
        Player player = gameLogic.getPlayerById(0);
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
        gameLogic.getPlayerById(1).setDead();
        player = gameLogic.getPlayerById(1);
        posPre = player.getFront();
        gameLogic.changePlayerDirection(1, "left");
        assertEquals(player.getFront(), posPre);
        assertSame(player.getDirection(), Direction.LEFT);
    }

    @Test
    void updatePlayersEveryPlayerMoved() {
        List<Position> posPre = new ArrayList<>();
        for (int[] p : players) posPre.add(new Position(p[1], p[2]));
        gameLogic.updatePlayers();
        players = gameLogic.getPlayerPositions();
        List<Position> posPost = new ArrayList<>();
        for (int[] p : players) posPost.add(new Position(p[1], p[2]));
        posPre.retainAll(posPost);   //only keeps any same values from two lists
        assertEquals(0, posPre.size());    //every position should be different
    }

    @Test
    void updatePlayersOneDead() {
        //Kill one player
        gameLogic.getPlayerById(0).setDead();
        gameLogic.updatePlayers();
        players = gameLogic.getPlayerPositions();
        assertTrue(Arrays.stream(players).anyMatch(p -> p[1] == -1 && p[2] == -1));
        assertEquals(3, Arrays.stream(players).filter(p -> p[1] != -1 && p[2] != -1).count());
        assertTrue(players[0][1] == -1 && players[0][2] == -1);
    }

    @Test
    void updatePlayersCollisionWithWall() {
        //one player steers right twice and drives into wall
        gameLogic.changePlayerDirection(0, "right");
        gameLogic.updatePlayers();
        gameLogic.changePlayerDirection(0, "right");
        gameLogic.updatePlayers();
        players = gameLogic.getPlayerPositions();
        assertTrue(players[0][1] == -1 && players[0][2] == -1);     //player 0 dead
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
        players = gameLogic.getPlayerPositions();
        assertTrue(players[0][1] == -1 && players[0][2] == -1); //player 0 dead
        assertTrue(players[1][1] == -1 && players[1][2] == -1); //player 1 dead
    }

    @Test
    void updatePlayersCollisionWithOtherTrail() {
        /**
         * Gamefield at collision of 0 with trail of 2
         * x x 2 x x
         * 0 0 2 x x
         * 0 x 2 x 1
         * x x 2 x 1
         * x x x 1 1
         */
        gameLogic.init(3, 5, 3);
        players = gameLogic.getPlayerPositions();
        gameLogic.changePlayerDirection(0, "left");
        gameLogic.changePlayerDirection(1, "left");
        gameLogic.updatePlayers();  //tick 1
        gameLogic.changePlayerDirection(0, "right");
        gameLogic.updatePlayers();  //tick 2
        gameLogic.changePlayerDirection(1, "right");
        gameLogic.updatePlayers();  //tick 3
        players = gameLogic.getPlayerPositions();
        assertSame(gameLogic.getGameState(), "RUNNING");    //only one player died
        assertTrue(Arrays.stream(players).anyMatch(p -> p[1] == -1 && p[2] == -1));
    }

    @Test
    void updatePlayersCollisionWithOtherFront() {
        /**
         * Gamefield at collision of 0 and 1
         * x x 2 x x
         * x 2 2 x x
         * 0 0 X 1 1
         * x x 3 3 x
         * x x 3 x x
         */
        gameLogic.init(4, 5, 3);
        players = gameLogic.getPlayerPositions();
        gameLogic.updatePlayers();  //tick 1
        gameLogic.changePlayerDirection(2, "right");
        gameLogic.changePlayerDirection(3, "right");
        gameLogic.updatePlayers();  //tick 2 --> collision in the middle
        players = gameLogic.getPlayerPositions();
        assertSame(gameLogic.getGameState(), "RUNNING");
        assertSame(2, (int) Arrays.stream(players).filter(p -> p[1] == -1 && p[2] == -1).count());    //2 players dead
        assertTrue(players[0][1] == -1 && players[0][2] == -1); //player 0 dead
        assertTrue(players[1][1] == -1 && players[1][2] == -1); //player 1 dead
        assertTrue(players[2][1] != -1 && players[2][2] != -1); //player 2 alive
        assertTrue(players[3][1] != -1 && players[3][2] != -1); //player 3 alive
    }

    @Test
    void updatePlayersPlayerWinCollision() {
        //one player steers right twice and drives into wall --> other player wins
        gameLogic.init(2, 40, 3);
        players = gameLogic.getPlayerPositions();
        gameLogic.changePlayerDirection(0, "right");
        gameLogic.updatePlayers();
        gameLogic.changePlayerDirection(0, "right");
        gameLogic.updatePlayers();
        players = gameLogic.getPlayerPositions();
        assertTrue(players[0][1] == -1 && players[0][2] == -1); //player 0 dead
        assertSame(gameLogic.getGameState(), "OVER");
        assertEquals(1, gameLogic.getGameWinner()); //player 0 died so player 1 should be winner
    }

    @Test
    void updatePlayersPlayerWinTwoCollisions() {
        //players 0 and 1 drive into one another --> player 2 wins
        gameLogic.init(3, 5, 3);
        players = gameLogic.getPlayerPositions();
        gameLogic.updatePlayers();  //tick 1
        gameLogic.changePlayerDirection(2, "left"); //player 2 is clever
        gameLogic.updatePlayers();  //tick 2 --> collision in the middle
        players = gameLogic.getPlayerPositions();
        assertTrue(players[0][1] == -1 && players[0][2] == -1); //player 0 dead
        assertTrue(players[1][1] == -1 && players[1][2] == -1); //player 1 dead
        assertTrue(players[2][1] != -1 && players[2][2] != -1); //player 2 alive
        assertSame(gameLogic.getGameState(), "OVER");
        assertEquals(2, gameLogic.getGameWinner()); //players 0 and 1 died so player 2 should be winner
    }

    @Test
    void updatePlayersTie() {
        //every player drives into one another
        gameLogic.init(4, 5, 3);
        gameLogic.updatePlayers();  //tick 1
        gameLogic.updatePlayers();  //tick 2 --> collision in the middle so big its a Karambolage
        assertSame(gameLogic.getGameState(), "OVER");
        assertEquals(-1, gameLogic.getGameWinner());

        //tie every (5) different collision type
        /**
         * x x x 2 x x x
         * 0 0 0 2 x x x
         * 0 x X 2 2 1 1
         * 4 4 4 X x 1 1
         * 4 x x x x x x
         * x x x 3 3 x x
         * x x x 3 3 x x
         */
        gameLogic.init(5, 7, 3);
        gameLogic.changePlayerDirection(0, "left");
        gameLogic.changePlayerDirection(4, "left");
        gameLogic.updatePlayers();  //tick 1
        gameLogic.changePlayerDirection(0, "right");
        gameLogic.changePlayerDirection(1, "right");
        gameLogic.changePlayerDirection(3, "right");
        gameLogic.changePlayerDirection(4, "right");
        gameLogic.updatePlayers();  //tick 2
        gameLogic.changePlayerDirection(1, "right");
        gameLogic.changePlayerDirection(2, "left");
        gameLogic.changePlayerDirection(3, "right");
        gameLogic.updatePlayers();  //tick 3
        gameLogic.changePlayerDirection(0, "right");
        gameLogic.changePlayerDirection(3, "right");
        gameLogic.changePlayerDirection(4, "left");
        gameLogic.updatePlayers();  //tick 4 --> 0:coll 4, 1:coll wall, 2:coll trail, 3:coll self, 4:coll 0
        assertSame(gameLogic.getGameState(), "OVER");
        assertEquals(-1, gameLogic.getGameWinner());
    }
}