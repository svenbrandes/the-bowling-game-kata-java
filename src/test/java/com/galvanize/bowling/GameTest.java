package com.galvanize.bowling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameTest {
    Game game;

    @BeforeEach
    void setup() {
        game = new Game();
    }

    @Test
    void gameStarts() {
        assertNotNull(game);
    }

    @Test
    void checkScoreNewGame_rtnZero() {
        int expected = 0;
        int actual = game.getTotalScore();
        assertEquals(expected, actual);
    }

    @Test
    void rollBallFor2_rtnTwo() {
        game.roll(2);
        int expected = 2;
        int actual = game.getTotalScore();
        assertEquals(expected, actual);
    }

    @Test
    void roll20BallsFor0_rtnZero() {
        for(int i=0; i<20; i++) {
            game.roll(0);
        }
        int expected = 0;
        int actual = game.getTotalScore();
        assertEquals(expected, actual);
    }

    @Test
    void roll20BallsFor1_rtnTwenty() {
        for(int i=0; i<20; i++) {
            game.roll(1);
        }
        int expected = 20;
        int actual = game.getTotalScore();
        assertEquals(expected, actual);
    }

    @Test
    void roll20BallsWith1Spare_rtn19() {
        game.roll(5);
        game.roll(5);
        game.roll(3);
        game.roll(3);
        for(int i=0; i<16; i++) {
            game.roll(0);
        }
        int expected = 19;
        int actual = game.getTotalScore();
        assertEquals(expected, actual);
    }

    @Test
    void roll20BallsWith1Strike_rtn22() {
        game.roll(10);
        game.roll(3);
        game.roll(3);
        for(int i=0; i<16; i++) {
            game.roll(0);
        }
        int expected = 22;
        int actual = game.getTotalScore();
        assertEquals(expected, actual);
    }

    @Test
    void roll3Strikes_rtn60() {
        game.roll(10);
        game.roll(10);
        game.roll(10);
        for(int i=0; i<14; i++) {
            game.roll(0);
        }
        int expected = 60;
        int actual = game.getTotalScore();
        assertEquals(expected, actual);
    }

    @Test
    void roll9Strikes_rtn240() {
        for(int i=0; i<9; i++) {
            game.roll(10);
        }
        game.roll(0);
        game.roll(0);
        int expected = 240;
        int actual = game.getTotalScore();
        assertEquals(expected, actual);
    }

    @Test
    void rollAllStrikes_rtn300() {
        for(int i=0; i<12; i++) {
            game.roll(10);
        }
        int expected = 300;
        int actual = game.getTotalScore();
        assertEquals(expected, actual);
    }

}