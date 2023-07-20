package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;

import static model.Basket.posXStarting;
import static model.Basket.posYConstant;


class TestBasket {

    private Basket testBasket;
    private ArrayList<Fruit> fallingFruit;

    @BeforeEach
    void runBefore() {
        testBasket = new Basket();
        fallingFruit = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertEquals(posXStarting, testBasket.getX());
        assertEquals(posYConstant, testBasket.getY());
        assertEquals(0, testBasket.getDirection());
        assertEquals(fallingFruit, testBasket.getFruitInBasket());
    }

    @Test
    void testSetPosition() {
        //Starts off not moving at all
        testBasket.move(0);
        assertEquals(posXStarting, testBasket.getX());

        //Moves once to the right
        testBasket.move(1);
        assertEquals(posXStarting + 1, testBasket.getX());

        //Stops moving
        testBasket.move(0);
        assertEquals(posXStarting + 1, testBasket.getX());

        //Moves once to the left
        testBasket.move(-1);
        assertEquals(10, testBasket.getX());

    }


}