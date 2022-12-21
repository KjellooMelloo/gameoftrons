package de.hawh.beta3.application.game.model.gamelogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    private Direction d1;

    @BeforeEach
    void setUp() {
        d1 = Direction.LEFT;
    }

    @Test
    void next() {
        assertEquals(Direction.UP, d1.next());
        assertEquals(Direction.RIGHT, d1.next().next());
        assertEquals(Direction.DOWN, d1.next().next().next());
        assertEquals(Direction.LEFT, d1.next().next().next().next());
    }

    @Test
    void prev() {
        assertEquals(Direction.DOWN, d1.prev());
        assertEquals(Direction.RIGHT, d1.prev().prev());
        assertEquals(Direction.UP, d1.prev().prev().prev());
        assertEquals(Direction.LEFT, d1.prev().prev().prev().prev());
    }
}