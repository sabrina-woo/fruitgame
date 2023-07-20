package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Basket;
import model.Enemy;
import model.Fruit;

//Represents the game
public class Game {
    public static final int TICKS = 20;
    private Basket basket;
    private List<Fruit> fallingFruit;
    private List<Fruit> fruitInBasket;
    private List<Enemy> fallingEnemies;
    private boolean ended;
    private int score;
    private int screenX;
    private int screenY;
    int maxNumberOfFallingFruit = 6;
    int maxNumberOfFallingEnemies = 3;

    //Constructs a game
    //EFFECTS: creates a game with an empty list of fruit and enemies, and the score starting at zero
    public Game(int x, int y) {
        this.screenX = x;
        this.screenY = y;
        this.basket = new Basket();
        this.fallingFruit = new ArrayList<>();
        this.fallingEnemies = new ArrayList<>();
        this.score = 0;
        this.fruitInBasket = new ArrayList<>();
        this.ended = false;
    }

    //EFFECTS: updates the fruit and enemies
    public void tick() {
        moveFruit(fallingFruit);
        moveEnemies(fallingEnemies);
        createFruit();
        createEnemies();
        handleEnemies();
        handleFood();
    }

    //EFFECTS: creates one fruit at a random x position and the given y position
    public Fruit createOneFruit() {
        Random random = new Random();
        int posX = this.screenX;
        Fruit fruit = new Fruit(random.nextInt(40), 1);

        return fruit;
    }

    //MODIFIES: this
    //EFFECTS: generates the number of fruit required to make the number of falling fruits the same number as
    // maxNumberOfFallingFruit
    public void createFruit() {
        int numberOfFruitsToGenerate = maxNumberOfFallingFruit - fallingFruit.size();
        for (int i = 0; i < numberOfFruitsToGenerate; i++) {
            Fruit fruit = createOneFruit();
            fallingFruit.add(fruit);
        }
    }

    //EFFECTS: creates one enemy at a random x position and the given y position
    public Enemy createOneEnemy() {
        Random random = new Random();
        int posY = this.screenX;
        //change x to the highest point in game board after figuring how the grid axis works
        Enemy enemy = new Enemy(random.nextInt(40), 1);
        return enemy;
    }

    //MODIFIES: this
    //EFFECTS: generates the number of enemies required to make the number of falling enemies the same number as
    // maxNumberOfFallingEnemies
    public void createEnemies() {
        int numberOfEnemiesToGenerate = maxNumberOfFallingEnemies - fallingEnemies.size();
        for (int i = 0; i < numberOfEnemiesToGenerate; i++) {
            Enemy enemy = createOneEnemy();
            fallingEnemies.add(enemy);
        }
    }

    //MODIFIES: this
    //EFFECTS: remove fruit that are either off the screen or touched by the player
    public void handleFood() {
        for (int i = 0; i < this.fallingFruit.size(); i++) {
            Fruit fruit = this.fallingFruit.get(i);
            if (fruit.getY() > this.screenY) {
                fallingFruit.remove(fruit);
                i--;
            }
            if (basket.getX() == fruit.getX() && basket.getY() == fruit.getY()) {
                fallingFruit.remove(fruit);
                this.fruitInBasket.add(fruit);
                this.score++;
                i--;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: remove enemies that are off the screen, and ends the game if the player touches an enemy
    public void handleEnemies() {
        for (int i = 0; i < this.fallingEnemies.size(); i++) {
            Enemy enemy = this.fallingEnemies.get(i);
            if (enemy.getY() > this.screenY) {
                fallingEnemies.remove(enemy);
                i--;
            }
            if (basket.getX() == enemy.getX() && basket.getY() == enemy.getY()) {
                this.ended = true;
            }

        }
    }

    //MODIFIES: this
    //EFFECTS: gets the new position of every fruit in the list of fruit on tick
    public List<Fruit> moveFruit(List<Fruit> fruit) {
        List<Fruit> newFruitPositions = new ArrayList<>();
        for (Fruit currentFruit : fruit) {
            currentFruit.move();
            newFruitPositions.add(currentFruit);

        }
        return newFruitPositions;
    }

    //MODIFIES: this
    //EFFECTS: gets the new position of every enemy in the list of enemies on tick
    public List<Enemy> moveEnemies(List<Enemy> enemies) {
        List<Enemy> newEnemyPositions = new ArrayList<>();
        for (Enemy currentEnemy : enemies) {
            currentEnemy.move();
            newEnemyPositions.add(currentEnemy);

        }
        return newEnemyPositions;
    }


    public boolean isEnded() {
        return this.ended;
    }

    public int getX() {
        return this.screenX;
    }

    public int getY() {
        return this.screenY;
    }

    public List<Fruit> getFruit() {
        return this.fallingFruit;
    }

    public List<Fruit> getFruitInBasket() {
        return this.fruitInBasket;
    }

    public int getScore() {
        return this.score;
    }

    public Basket getBasket() {
        return this.basket;
    }

    public void setBasket(int i) {
        basket.setPosition(i);
    }

    public void setBasketX(int i) {
        basket.setXBasket(i);
    }

    public void setBasketY(int i) {
        basket.setYBasket(i);
    }

    public List<Enemy> getEnemies() {
        return this.fallingEnemies;
    }

}


