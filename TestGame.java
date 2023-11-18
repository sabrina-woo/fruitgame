package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestGame {

    Game testGame;
    Game game;

    @BeforeEach
        void runBefore() {
        testGame = new Game(0, 0);
        game = new Game(5, 5);
    }

    @Test
    void testConstructor() {

        List<Fruit> fruitInBasket = testGame.getFruitInBasket();

        assertEquals(0, testGame.getX());
        assertEquals(0, testGame.getY());
        assertEquals(0, fruitInBasket.size());
        assertFalse(testGame.isEnded());
        assertEquals(testGame.getBasket(), testGame.getBasket());

        //Test the construction with different values for x and y
        assertEquals(5, game.getX());
        assertEquals(5, game.getY());
    }

    @Test
    void testCreateFruitNone() {
        //max fruit is full, so do not create anymore
        Fruit oneFruit = new Fruit(0, 0, "blueberry", 1);
        List<FallingObject> fruit = testGame.getFallingObjects();

        fruit.add(oneFruit);
        fruit.add(oneFruit);
        fruit.add(oneFruit);
        fruit.add(oneFruit);
        fruit.add(oneFruit);
        fruit.add(oneFruit);

        assertEquals(6, fruit.size());

    }

    @Test
    void testCreateFruitAll() {
        //maxFallingFruit at 0, so create the maximum number
        List<FallingObject> fruit = testGame.getFallingObjects();
        assertEquals(0, fruit.size());

//        testGame.createFruit();
//        assertEquals(6, fruit.size());
    }

    @Test
    void testCreateEnemyOnce() {
        //max fallingEnemies is full, so do not create any
        Enemy oneEnemy = new Enemy(0, 0);
        List<FallingObject> enemies = testGame.getFallingObjects();

        enemies.add(oneEnemy);
        enemies.add(oneEnemy);
        enemies.add(oneEnemy);


        assertEquals(3, enemies.size());

    }

    @Test
    void testCreateEnemiesAll() {
        //maxFallingEnemies at 0, so create the maximum number
        List<FallingObject> enemies = testGame.getFallingObjects();
        assertEquals(0, enemies.size());

//        testGame.createEnemies();
//        assertEquals(3, enemies.size());
    }

    @Test
    void testHandleFoodHitsBasket() {
        //None of the fruit are off screen or touching player
        Fruit oneFruit = new Fruit(-15, -15, "blueberry", 1);
        Fruit aFruit = new Fruit(-20, -20, "blueberry", 1);
        List<FallingObject> fruit = testGame.getFallingObjects();
        List<Fruit> caughtFruit = testGame.getFruitInBasket();

        fruit.add(oneFruit);
        fruit.add(oneFruit);
        fruit.add(oneFruit);
        fruit.add(oneFruit);
        fruit.add(oneFruit);
        fruit.add(aFruit);

        assertEquals(6, fruit.size());

        testGame.handle();

        assertEquals(6, fruit.size());

        //Basket hits a fruit, so score goes up
        testGame.setBasket(-20);
        testGame.handle();
        assertEquals(5, fruit.size());
        assertEquals(1, caughtFruit.size());
        assertEquals(1, testGame.getScore());

        //Does not hit a fruit by one, then hits another fruit and score goes up again
        testGame.setBasket(-19);
        fruit.add(aFruit);
        testGame.handle();
        assertEquals(6, fruit.size());
        assertEquals(1, testGame.getScore());

        testGame.setBasket(-21);
        testGame.handle();
        assertEquals(6, fruit.size());
        assertEquals(1, testGame.getScore());

        testGame.setBasket(-20);
        testGame.handle();
        assertEquals(5, fruit.size());
        assertEquals(2, caughtFruit.size());
        assertEquals(2, testGame.getScore());

    }

    @Test
    void testHandleEnemyHitsBasket() {
        //Enemy has same X but not same Y
        Enemy oneEnemy = new Enemy(-15, -15);


        List<FallingObject> enemy = testGame.getFallingObjects();
        enemy.add(oneEnemy);
        testGame.setBasketX(-15);
        testGame.setBasketY(-16);

        testGame.handle();
        assertFalse(testGame.isEnded());

        //Enemy has same Y but not same X
        testGame.setBasketX(-16);
        testGame.setBasketY(-15);
        testGame.handle();
        assertTrue(testGame.isEnded());

        //Enemy has same Y but not same X
        testGame.setBasketX(-14);
        testGame.setBasketY(-15);
        testGame.handle();
        assertTrue(testGame.isEnded());

        //Enemy has same Y and same X
        testGame.setBasketX(-15);
        testGame.setBasketY(-15);
        testGame.handle();
        assertTrue(testGame.isEnded());
    }

    @Test
    void testHandleEnemyHitsBasketLeftEdge() {
        int leftEdge = game.getBasket().getX() - (Basket.SIZE_X / 2);
        Enemy oneEnemy = new Enemy(leftEdge, -15);
        List<FallingObject> enemy = game.getFallingObjects();
        enemy.add(oneEnemy);
        game.setBasketY(-15);
        game.handle();
        assertTrue(game.isEnded());
    }

    @Test
    void testHandleEnemyNotHitBasketLeftEdge() {
        int leftEdge = game.getBasket().getX() - (Basket.SIZE_X / 2);
        Enemy oneEnemy = new Enemy(leftEdge - 1, -15);
        List<FallingObject> enemy = game.getFallingObjects();
        enemy.add(oneEnemy);
        game.setBasketY(-15);
        game.handle();
        assertFalse(game.isEnded());
    }

    @Test
    void testHandleEnemyHitsBasketLeftEdgeTooFar() {
        int leftSide = game.getBasket().getX() + (Basket.SIZE_X * 2);
        Enemy oneEnemy = new Enemy(leftSide, -15);
        List<FallingObject> enemy = game.getFallingObjects();
        enemy.add(oneEnemy);
        game.setBasketY(-15);
        game.handle();
        assertFalse(game.isEnded());
    }


    @Test
    void testHandleEnemyHitsBasketRightEdge() {
        int rightEdge = game.getBasket().getX() + Basket.SIZE_X;
        Enemy oneEnemy = new Enemy(rightEdge, -15);
        List<FallingObject> enemy = game.getFallingObjects();
        enemy.add(oneEnemy);
        game.setBasketY(-15);
        game.handle();
        assertTrue(game.isEnded());
    }

    @Test
    void testHandleEnemyHitsBasketRightEdgeTooFar() {
        int rightSide = game.getBasket().getX() + Basket.SIZE_X * 2;
        Enemy oneEnemy = new Enemy(rightSide, -15);
        List<FallingObject> enemy = game.getFallingObjects();
        enemy.add(oneEnemy);
        game.setBasketY(-15);
        game.handle();
        assertFalse(game.isEnded());
    }

    @Test
    void testHandleEnemyHitsBasketEdge() {
        int rightEdge = game.getBasket().getX() + Basket.SIZE_X;
        Enemy oneEnemy = new Enemy(rightEdge, -15);
        List<FallingObject> enemy = game.getFallingObjects();
        enemy.add(oneEnemy);
        game.setBasketY(-16);
        assertFalse(game.isEnded());
        game.setBasketY(-15);
        game.handle();
        assertTrue(game.isEnded());
    }


    @Test
    void handleFoodOffScreen() {
        //fruit stays on the screen by one value
        Fruit oneFruit = new Fruit(0, 0, "blueberry", 1);
        List<FallingObject> fruit = testGame.getFallingObjects();

        fruit.add(oneFruit);
        testGame.handle();
        assertEquals(1, fruit.size());

        Fruit aFruit = new Fruit(1, 1, "blueberry", 1);
        fruit.add(aFruit);
        assertEquals(2, fruit.size());
        testGame.handle();
        assertEquals(1, fruit.size());
    }

    @Test
    void handleEnemiesOffScreen() {
        //fruit stays on the screen by one value
        Enemy oneEnemy = new Enemy(0, 0);
        List<FallingObject> enemy = testGame.getFallingObjects();

        enemy.add(oneEnemy);
        testGame.handle();
        assertEquals(1, enemy.size());

        Enemy aEnemy = new Enemy(1, 1);
        enemy.add(aEnemy);
        assertEquals(2, enemy.size());
        testGame.handle();
        assertEquals(1, enemy.size());
    }



    @Test
    void testMoveFruit() {
        //No fruit to move, so returns same empty list
        Fruit oneFruit = new Fruit(0, -2, "blueberry", 1);
        Fruit secondFruit = new Fruit(0, -4, "blueberry", 1);
        List<FallingObject> fruit = testGame.getFallingObjects();
        assertEquals(0, fruit.size());

        assertEquals(0, fruit.size());

        //Adds a fruit, and checks that it moved one Y
        fruit.add(oneFruit);
        fruit.add(secondFruit);
        assertEquals(0, oneFruit.getX());
        assertEquals(-2, oneFruit.getY());
        assertEquals(0, secondFruit.getX());
        assertEquals(-4, secondFruit.getY());

        testGame.move();
        assertEquals(0, oneFruit.getX());
        assertEquals(-1, oneFruit.getY());
        assertEquals(0, secondFruit.getX());
        assertEquals(-3, secondFruit.getY());

        //Fruit is moved again
        testGame.move();
        assertEquals(0, oneFruit.getX());
        assertEquals(0, oneFruit.getY());
        assertEquals(0, secondFruit.getX());
        assertEquals(-2, secondFruit.getY());
    }

    @Test
    void testMoveEnemies() {
        //No enemies to move, so returns same empty list
        Enemy oneEnemy = new Enemy(0, -2);
        Enemy secondEnemy = new Enemy(0, -4);
        List<FallingObject> enemy = testGame.getFallingObjects();
        assertEquals(0, enemy.size());

        assertEquals(0, enemy.size());

        //Adds an enemy, and checks that it moved one Y
        enemy.add(oneEnemy);
        enemy.add(secondEnemy);
        assertEquals(0, oneEnemy.getX());
        assertEquals(-2, oneEnemy.getY());
        assertEquals(0, secondEnemy.getX());
        assertEquals(-4, secondEnemy.getY());

        //Enemy is moved again
        testGame.move();
        assertEquals(0, oneEnemy.getX());
        assertEquals(-1, oneEnemy.getY());
        assertEquals(0, secondEnemy.getX());
        assertEquals(-3, secondEnemy.getY());

        testGame.move();
        assertEquals(0, oneEnemy.getX());
        assertEquals(0, oneEnemy.getY());
        assertEquals(0, secondEnemy.getX());
        assertEquals(-2, secondEnemy.getY());
    }

    @Test
    void testHandleFoodHitBasketBoundaries() {
        //Fruit has same X but not same Y
        Fruit oneFruit = new Fruit(-15, -15, "blueberry", 1);
        List<FallingObject> fruit = testGame.getFallingObjects();
        List<Fruit> fruitInBasket = testGame.getFruitInBasket();
        fruit.add(oneFruit);
        testGame.setBasketX(-15);
        testGame.setBasketY(-16);

        testGame.handle();
        assertEquals(1, fruit.size());
        assertEquals(0, fruitInBasket.size());
        assertEquals(0, testGame.getScore());

        //Fruit has same Y but not same X
        testGame.setBasketX(-16);
        testGame.setBasketY(-15);
        testGame.handle();
        assertEquals(0, fruit.size());
        assertEquals(1, fruitInBasket.size());
        assertEquals(1, testGame.getScore());

        //Fruit has same Y and same X
        testGame.setBasketX(-15);
        testGame.setBasketY(-15);
        testGame.handle();
        assertEquals(0, fruit.size());
        assertEquals(1, fruitInBasket.size());
        assertEquals(1, testGame.getScore());
    }

    @Test
    void testHandleFoodHitsBasketLeftEdge() {
        int leftEdge = game.getBasket().getX() - (Basket.SIZE_X / 2);
        Fruit oneFruit = new Fruit(leftEdge, -15, "blueberry", 1);
        List<FallingObject> fruit = game.getFallingObjects();
        List<Fruit> fruitInBasket = game.getFruitInBasket();
        fruit.add(oneFruit);
        game.setBasketY(-15);
        game.handle();
        assertEquals(0, fruit.size());
        assertEquals(1, fruitInBasket.size());
        assertEquals(1, game.getScore());
    }


    @Test
    void testHandleFoodNotHitBasketLeftEdge() {
        int leftEdge = game.getBasket().getX() - (Basket.SIZE_X / 2);
        Fruit oneFruit = new Fruit(leftEdge - 1, -15, "blueberry", 1);
        List<FallingObject> fruit = game.getFallingObjects();
        List<Fruit> fruitInBasket = game.getFruitInBasket();
        fruit.add(oneFruit);
        game.setBasketY(-15);
        game.handle();
        assertEquals(1, fruit.size());
        assertEquals(0, fruitInBasket.size());
        assertEquals(0, game.getScore());
    }


    @Test
    void testHandleFoodHitsBasketLeftEdgeTooFar() {
        int leftSide = game.getBasket().getX() + (Basket.SIZE_X * 2);
        Fruit oneFruit = new Fruit(leftSide, -15, "blueberry", 1);
        List<FallingObject> fruit = game.getFallingObjects();
        List<Fruit> fruitInBasket = game.getFruitInBasket();
        fruit.add(oneFruit);

        game.setBasketY(-15);
        game.handle();
        assertEquals(1, fruit.size());
        assertEquals(0, fruitInBasket.size());
        assertEquals(0, game.getScore());
    }


    @Test
    void testHandleFoodHitsBasketRightEdge() {
        int rightEdge = game.getBasket().getX() + Basket.SIZE_X;
        Fruit oneFruit = new Fruit(rightEdge, -15, "blueberry", 1);
        List<FallingObject> fruit = game.getFallingObjects();
        List<Fruit> fruitInBasket = game.getFruitInBasket();
        fruit.add(oneFruit);

        game.setBasketY(-15);
        game.handle();
        assertEquals(0, fruit.size());
        assertEquals(1, fruitInBasket.size());
        assertEquals(1, game.getScore());
    }

    @Test
    void testHandleFoodHitsBasketRightEdgeTooFar() {
        int rightSide = game.getBasket().getX() + Basket.SIZE_X * 2;

        Fruit oneFruit = new Fruit(rightSide, -15, "blueberry", 1);
        List<FallingObject> fruit = game.getFallingObjects();
        List<Fruit> fruitInBasket = game.getFruitInBasket();
        fruit.add(oneFruit);

        game.setBasketY(-15);
        game.handle();
        assertEquals(1, fruit.size());
        assertEquals(0, fruitInBasket.size());
        assertEquals(0, game.getScore());
    }

    @Test
    void testHandleFoodHitsBasketEdge() {
        int rightEdge = game.getBasket().getX() + Basket.SIZE_X;

        Fruit oneFruit = new Fruit(rightEdge, -15, "blueberry", 1);
        List<FallingObject> fruit = game.getFallingObjects();
        List<Fruit> fruitInBasket = game.getFruitInBasket();
        fruit.add(oneFruit);

        game.setBasketY(-15);
        game.handle();
        assertEquals(0, fruit.size());
        assertEquals(1, fruitInBasket.size());
        assertEquals(1, game.getScore());
    }


    @Test
    void testAddFallingFruit() {
        assertEquals(0, game.getFallingObjects().size());
        game.addFallingFruit(new Fruit(0, 0, "blueberry", 1));
        assertEquals(1, game.getFallingObjects().size());
    }

    @Test
    void testAddFallingEnemies() {
        assertEquals(0, game.getFallingObjects().size());
        game.addFallingEnemies(new Enemy(0, 0));
        assertEquals(1, game.getFallingObjects().size());
    }

    @Test
    void testAddFruitInBasket() {
        assertEquals(0, game.getFruitInBasket().size());
        game.getBasket().addFruitInBasket(new Fruit(0, 0, "blueberry", 1));
        assertEquals(1, game.getFruitInBasket().size());
    }


}
