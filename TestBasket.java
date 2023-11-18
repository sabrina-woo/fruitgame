package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Basket.basketYPos;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;



class TestBasket {

    private Basket testBasket;
    private ArrayList<Fruit> fallingFruit;
    private final int posXConstructor = 10;
    private final int posYConstructor = 10;

    @BeforeEach
    void runBefore() {
        testBasket = new Basket(posXConstructor, posYConstructor);
        fallingFruit = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertEquals(posXConstructor / 2, testBasket.getX());
        assertEquals(posYConstructor - basketYPos, testBasket.getY());
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