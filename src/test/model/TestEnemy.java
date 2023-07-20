package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestEnemy {
    Fruit testEnemy;
    Fruit enemy;


    @BeforeEach
    void runBefore() {
        testEnemy = new Fruit(0, 0);
        enemy = new Fruit(5, 5);
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