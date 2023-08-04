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
        testBasket = new Basket(posXStarting, posYConstant);
        fallingFruit = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertEquals(5, testBasket.getX());
        assertEquals(5, testBasket.getY());
        assertEquals(0, testBasket.getDirection());
        assertEquals(fallingFruit, testBasket.getFruitInBasket());
    }

    @Test
    void testSetPosition() {
        //Starts off not moving at all
        testBasket.move(0);
        assertEquals(5, testBasket.getX());

        //Moves once to the right
        testBasket.move(1);
        assertEquals(5 + 1, testBasket.getX());

        //Stops moving
        testBasket.move(0);
        assertEquals(5 + 1, testBasket.getX());

        //Moves once to the left
        testBasket.move(-1);
        assertEquals(5, testBasket.getX());

    }


}