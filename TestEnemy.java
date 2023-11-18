package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestEnemy {
    Enemy testEnemy;
    Enemy enemy;


    @BeforeEach
    void runBefore() {
        testEnemy = new Enemy(0, 0);
        enemy = new Enemy(5, 5);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testEnemy.getX());
        assertEquals(0, testEnemy.getY());
        assertEquals(5, enemy.getX());
        assertEquals(5, enemy.getY());
    }

    @Test
    void testMove() {
        assertEquals(0, testEnemy.getY());
        testEnemy.move();
        assertEquals(1, testEnemy.getY());
        testEnemy.move();
        assertEquals(2, testEnemy.getY());
    }

}