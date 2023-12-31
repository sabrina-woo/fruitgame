package model;


import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestFruit {
    Fruit testFruit;
    Fruit fruit;


    @BeforeEach
    void runBefore() {
        testFruit = new Fruit(0, 0);
        fruit = new Fruit(5, 5);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testFruit.getX());
        assertEquals(0, testFruit.getY());
        assertEquals(5, fruit.getX());
        assertEquals(5, fruit.getY());
    }

    @Test
    void testMove() {
        assertEquals(0, testFruit.getY());
        testFruit.move();
        assertEquals(1, testFruit.getY());
        testFruit.move();
        assertEquals(2, testFruit.getY());
    }

    @Test
    void testToJson() {
        JSONObject jsonObject = testFruit.toJson();

        assertEquals(0, jsonObject.getInt("x position"));
        assertEquals(0, jsonObject.getInt("y position"));


    }
}
