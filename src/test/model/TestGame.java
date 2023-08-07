package model;

import org.json.JSONArray;
import org.json.JSONObject;
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
        List<Fruit> fruit = testGame.getFruit();
        List<Enemy> enemies = testGame.getEnemies();
        List<Fruit> fruitInBasket = testGame.getFruitInBasket();

        assertEquals(0, testGame.getX());
        assertEquals(0, testGame.getY());
        assertEquals(0, fruit.size());
        assertEquals(0, enemies.size());
        assertEquals(0, fruitInBasket.size());
        assertFalse(testGame.isEnded());
        assertEquals(testGame.getBasket(), testGame.getBasket());

        //Test the construction with different values for x and y
        assertEquals(5, game.getX());
        assertEquals(5, game.getY());
    }

    @Test
    void testCreateOneFruit() {
        //No fruit so far
        List<Fruit> fruit = new ArrayList<>();
        assertEquals(0, fruit.size());

        //Creates one fruit
        Fruit oneFruit = testGame.createOneFruit();
        fruit.add(oneFruit);
        assertEquals(1, fruit.size());
        assertEquals(oneFruit, fruit.get(0));
        assertTrue(fruit.contains(oneFruit));
    }

    @Test
    void testCreateOneEnemy() {
        //No enemies so far
        List<Enemy> enemy = new ArrayList<>();
        assertEquals(0, enemy.size());

        //Creates one fruit
        Enemy oneEnemy = testGame.createOneEnemy();
        enemy.add(oneEnemy);
        assertEquals(1, enemy.size());
        assertEquals(oneEnemy, enemy.get(0));
        assertTrue(enemy.contains(oneEnemy));
    }

    @Test
    void testCreateFruitNone() {
        //max fruit is full, so do not create any
        Fruit oneFruit = new Fruit(0, 0);
        List<Fruit> fruit = testGame.getFruit();

        fruit.add(oneFruit);
        fruit.add(oneFruit);
        fruit.add(oneFruit);
        fruit.add(oneFruit);
        fruit.add(oneFruit);
        fruit.add(oneFruit);

        assertEquals(6, fruit.size());

        testGame.createFruit();
        assertEquals(6, fruit.size());
    }

    @Test
    void testCreateFruitAll() {
        //maxFallingFruit at 0, so create the maximum number
        List<Fruit> fruit = testGame.getFruit();
        assertEquals(0, fruit.size());

        testGame.createFruit();
        assertEquals(6, fruit.size());
    }

    @Test
    void testCreateEnemyOnce() {
        //max fallingEnemies is full, so do not create any
        Enemy oneEnemy = new Enemy(0, 0);
        List<Enemy> enemies = testGame.getEnemies();

        enemies.add(oneEnemy);
        enemies.add(oneEnemy);
        enemies.add(oneEnemy);


        assertEquals(3, enemies.size());

        testGame.createFruit();
        assertEquals(3, enemies.size());
    }

    @Test
    void testCreateEnemiesAll() {
        //maxFallingEnemies at 0, so create the maximum number
        List<Enemy> enemies = testGame.getEnemies();
        assertEquals(0, enemies.size());

        testGame.createEnemies();
        assertEquals(3, enemies.size());
    }

    @Test
    void testHandleFoodHitsBasket() {
        //None of the fruit are off screen or touching player
        Fruit oneFruit = new Fruit(-15, -15);
        Fruit aFruit = new Fruit(-20, -20);
        List<Fruit> fruit = testGame.getFruit();
        List<Fruit> caughtFruit = testGame.getFruitInBasket();

        fruit.add(oneFruit);
        fruit.add(oneFruit);
        fruit.add(oneFruit);
        fruit.add(oneFruit);
        fruit.add(oneFruit);
        fruit.add(aFruit);

        assertEquals(6, fruit.size());

        testGame.handleFood();

        assertEquals(6, fruit.size());

        //Basket hits a fruit, so score goes up
        testGame.setBasket(-20);
        testGame.handleFood();
        assertEquals(5, fruit.size());
        assertEquals(1, caughtFruit.size());
        assertEquals(1, testGame.getScore());

        //Does not hit a fruit by one, then hits another fruit and score goes up again
        testGame.setBasket(-19);
        fruit.add(aFruit);
        testGame.handleFood();
        assertEquals(6, fruit.size());
        assertEquals(1, testGame.getScore());

        testGame.setBasket(-21);
        testGame.handleFood();
        assertEquals(6, fruit.size());
        assertEquals(1, testGame.getScore());

        testGame.setBasket(-20);
        testGame.handleFood();
        assertEquals(5, fruit.size());
        assertEquals(2, caughtFruit.size());
        assertEquals(2, testGame.getScore());

    }

    @Test
    void testHandleEnemyHitsBasket() {
        //Enemy has same X but not same Y
        Enemy oneEnemy = new Enemy(-15, -15);


        List<Enemy> enemy = testGame.getEnemies();
        enemy.add(oneEnemy);
        testGame.setBasketX(-15);
        testGame.setBasketY(-16);

        testGame.handleEnemies();
        assertFalse(testGame.isEnded());

        //Enemy has same Y but not same X
        testGame.setBasketX(-16);
        testGame.setBasketY(-15);
        testGame.handleEnemies();
        assertTrue(testGame.isEnded());

        //Enemy has same Y but not same X
        testGame.setBasketX(-14);
        testGame.setBasketY(-15);
        testGame.handleEnemies();
        assertTrue(testGame.isEnded());

        //Enemy has same Y and same X
        testGame.setBasketX(-15);
        testGame.setBasketY(-15);
        testGame.handleEnemies();
        assertTrue(testGame.isEnded());
    }

    @Test
    void testHandleEnemyHitsBasketLeftEdge() {
        int leftEdge = game.getBasket().getX() - (Basket.SIZE_X / 2);
        Enemy oneEnemy = new Enemy(leftEdge, -15);
        List<Enemy> enemy = game.getEnemies();
        enemy.add(oneEnemy);
        game.setBasketY(-15);
        game.handleEnemies();
        assertTrue(game.isEnded());
    }

    @Test
    void testHandleEnemyHitsBasketLeftEdgeTooFar() {
        int leftSide = game.getBasket().getX() + (Basket.SIZE_X * 2);
        Enemy oneEnemy = new Enemy(leftSide, -15);
        List<Enemy> enemy = game.getEnemies();
        enemy.add(oneEnemy);
        game.setBasketY(-15);
        game.handleEnemies();
        assertFalse(game.isEnded());
    }

    @Test
    void testHandleEnemyHitsBasketRightEdge() {
        int rightEdge = game.getBasket().getX() + Basket.SIZE_X;
        Enemy oneEnemy = new Enemy(rightEdge, -15);
        List<Enemy> enemy = game.getEnemies();
        enemy.add(oneEnemy);
        game.setBasketY(-15);
        game.handleEnemies();
        assertTrue(game.isEnded());
    }

    @Test
    void testHandleEnemyHitsBasketRightEdgeTooFar() {
        int rightSide = game.getBasket().getX() + Basket.SIZE_X * 2;
        Enemy oneEnemy = new Enemy(rightSide, -15);
        List<Enemy> enemy = game.getEnemies();
        enemy.add(oneEnemy);
        game.setBasketY(-15);
        game.handleEnemies();
        assertFalse(game.isEnded());
    }

    @Test
    void testHandleEnemyHitsBasketEdge() {
        int rightEdge = game.getBasket().getX() + Basket.SIZE_X;
        Enemy oneEnemy = new Enemy(rightEdge, -15);
        List<Enemy> enemy = game.getEnemies();
        enemy.add(oneEnemy);
        game.setBasketY(-16);
        assertFalse(game.isEnded());
        game.setBasketY(-15);
        game.handleEnemies();
        assertTrue(game.isEnded());
    }


    @Test
    void handleFoodOffScreen() {
        //fruit stays on the screen by one value
        Fruit oneFruit = new Fruit(0, 0);
        List<Fruit> fruit = testGame.getFruit();

        fruit.add(oneFruit);
        testGame.handleFood();
        assertEquals(1, fruit.size());

        Fruit aFruit = new Fruit(1, 1);
        fruit.add(aFruit);
        assertEquals(2, fruit.size());
        testGame.handleFood();
        assertEquals(1, fruit.size());
    }

    @Test
    void handleEnemiesOffScreen() {
        //fruit stays on the screen by one value
        Enemy oneEnemy = new Enemy(0, 0);
        List<Enemy> enemy = testGame.getEnemies();

        enemy.add(oneEnemy);
        testGame.handleEnemies();
        assertEquals(1, enemy.size());

        Enemy aEnemy = new Enemy(1, 1);
        enemy.add(aEnemy);
        assertEquals(2, enemy.size());
        testGame.handleEnemies();
        assertEquals(1, enemy.size());
    }



    @Test
    void testMoveFruit() {
        //No fruit to move, so returns same empty list
        Fruit oneFruit = new Fruit(0, -2);
        Fruit secondFruit = new Fruit(0, -4);
        List<Fruit> fruit = testGame.getFruit();
        assertEquals(0, fruit.size());

        assertEquals(0, fruit.size());
        assertEquals(fruit, testGame.moveFruit(fruit));

        //Adds a fruit, and checks that it moved one Y
        fruit.add(oneFruit);
        fruit.add(secondFruit);
        assertEquals(0, oneFruit.getX());
        assertEquals(-2, oneFruit.getY());
        assertEquals(0, secondFruit.getX());
        assertEquals(-4, secondFruit.getY());

        testGame.moveFruit(fruit);
        assertEquals(0, oneFruit.getX());
        assertEquals(-1, oneFruit.getY());
        assertEquals(0, secondFruit.getX());
        assertEquals(-3, secondFruit.getY());

        //Fruit is moved again
        testGame.moveFruit(fruit);
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
        List<Enemy> enemy = testGame.getEnemies();
        assertEquals(0, enemy.size());

        assertEquals(0, enemy.size());
        assertEquals(enemy, testGame.moveEnemies(enemy));

        //Adds an enemy, and checks that it moved one Y
        enemy.add(oneEnemy);
        enemy.add(secondEnemy);
        assertEquals(0, oneEnemy.getX());
        assertEquals(-2, oneEnemy.getY());
        assertEquals(0, secondEnemy.getX());
        assertEquals(-4, secondEnemy.getY());

        //Enemy is moved again
        testGame.moveEnemies(enemy);
        assertEquals(0, oneEnemy.getX());
        assertEquals(-1, oneEnemy.getY());
        assertEquals(0, secondEnemy.getX());
        assertEquals(-3, secondEnemy.getY());

        testGame.moveEnemies(enemy);
        assertEquals(0, oneEnemy.getX());
        assertEquals(0, oneEnemy.getY());
        assertEquals(0, secondEnemy.getX());
        assertEquals(-2, secondEnemy.getY());
    }

    @Test
    void testHandleFoodHitBasketBoundaries() {
        //Fruit has same X but not same Y
        Fruit oneFruit = new Fruit(-15, -15);
        List<Fruit> fruit = testGame.getFruit();
        List<Fruit> fruitInBasket = testGame.getFruitInBasket();
        fruit.add(oneFruit);
        testGame.setBasketX(-15);
        testGame.setBasketY(-16);

        testGame.handleFood();
        assertEquals(1, fruit.size());
        assertEquals(0, fruitInBasket.size());
        assertEquals(0, testGame.getScore());

        //Fruit has same Y but not same X
        testGame.setBasketX(-16);
        testGame.setBasketY(-15);
        testGame.handleFood();
        assertEquals(0, fruit.size());
        assertEquals(1, fruitInBasket.size());
        assertEquals(1, testGame.getScore());

        //Fruit has same Y and same X
        testGame.setBasketX(-15);
        testGame.setBasketY(-15);
        testGame.handleFood();
        assertEquals(0, fruit.size());
        assertEquals(1, fruitInBasket.size());
        assertEquals(1, testGame.getScore());
    }

    @Test
    void testTick() {
        List<Fruit> fruit = testGame.getFruit();
        List<Enemy> enemies = testGame.getEnemies();

        assertEquals(0, fruit.size());
        assertEquals(0, enemies.size());

        testGame.update();

        assertEquals(0, fruit.size());
        assertEquals(0, enemies.size());
    }

    @Test
    void testAddFallingFruit() {
        assertEquals(0, game.getFruit().size());
        game.addFallingFruit(new Fruit(0, 0));
        assertEquals(1, game.getFruit().size());
    }

    @Test
    void testAddFallingEnemies() {
        assertEquals(0, game.getEnemies().size());
        game.addFallingEnemies(new Enemy(0, 0));
        assertEquals(1, game.getEnemies().size());
    }

    @Test
    void testAddFruitInBasket() {
        assertEquals(0, game.getFruitInBasket().size());
        game.getBasket().addFruitInBasket(new Fruit(0, 0));
        assertEquals(1, game.getFruitInBasket().size());
    }

    @Test
    void testToJson() {
        JSONObject jsonObject = testGame.toJson();

        assertEquals(0, jsonObject.getInt("score"));
        assertEquals(0, jsonObject.getInt("screen x"));
        assertEquals(0, jsonObject.getInt("screen y"));
        assertFalse(jsonObject.getBoolean("ended"));
        assertEquals(-15, jsonObject.getInt("basket y"));
        assertEquals(0, jsonObject.getInt("basket x"));
    }

    @Test
    void testFallingFruitToJson() {
        JSONArray jsonArray = testGame.fallingFruitToJson();
        assertTrue(jsonArray.isEmpty());
        testGame.addFallingFruit(new Fruit(0, 0));
        JSONArray jsonArrayTwo = testGame.fallingFruitToJson();
        assertFalse(jsonArrayTwo.isEmpty());
    }

    @Test
    void testFallingEnemiesToJson() {
        JSONArray jsonArray = testGame.fallingEnemiesToJson();
        assertTrue(jsonArray.isEmpty());
        testGame.addFallingEnemies(new Enemy(0, 0));
        JSONArray jsonArrayTwo = testGame.fallingEnemiesToJson();
        assertFalse(jsonArrayTwo.isEmpty());
    }


    @Test
    void testBasketFruitToJson() {
        JSONArray jsonArray = testGame.basketFruitToJson();
        assertTrue(jsonArray.isEmpty());
        testGame.getBasket().addFruitInBasket(new Fruit(0, 0));
        JSONArray jsonArrayTwo = testGame.basketFruitToJson();
        assertFalse(jsonArrayTwo.isEmpty());

    }
}
