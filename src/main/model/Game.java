package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.JsonReader;
import persistance.JsonWriter;
import persistance.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Represents the game
public class Game implements Writable {
    public static final int TICKS = 20;
    private Basket basket;
    private List<Fruit> fallingFruit;
//    private List<Fruit> fruitInBasket;
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
//        this.fruitInBasket = new ArrayList<>();
        this.ended = false;
    }

    //REQUIRES: TICKS >= 1
    //MODIFIES: this
    //EFFECTS: updates the fruit and enemies
    public void tick() {
        moveFruit(this.fallingFruit);
        moveEnemies(this.fallingEnemies);
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

    //REQUIRES: maxNumberOfFallingFruit >= 0
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
        Enemy enemy = new Enemy(random.nextInt(40), 1);
        return enemy;
    }

    //REQUIRES: maxNumberOfFallingEnemies >= 0
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
                this.basket.addFruitInBasket(fruit);
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
        for (Fruit currentFruit : fruit) {
            currentFruit.move();

        }
        return fruit;
    }

    //MODIFIES: this
    //EFFECTS: gets the new position of every enemy in the list of enemies on tick
    public List<Enemy> moveEnemies(List<Enemy> enemies) {
        for (Enemy currentEnemy : enemies) {
            currentEnemy.move();

        }
        return enemies;
    }

    //MODIFIES: this
    //EFFECTS: Adds a fruit to fallingFruit
    public void addFallingFruit(Fruit fruit) {
        this.fallingFruit.add(fruit);
    }

    //MODIFIES: this
    //EFFECTS: Adds an enemy to fallingEnemies
    public void addFallingEnemies(Enemy enemy) {
        this.fallingEnemies.add(enemy);
    }

//    //MODIFIES: this
//    //EFFECTS: Adds a fruit to fruitInBasket
//    public void addFruitInBasket(Fruit fruit) {
//        this.basket.addFruitInBasket(fruit);
//    }

    //EFFECTS: puts game information into JSON file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("basket x", basket.getX());
        json.put("basket y", basket.getY());
        json.put("falling fruit", fallingFruitToJson());
        json.put("basket fruit", basketFruitToJson());
        json.put("falling enemies", fallingEnemiesToJson());
        json.put("score", score);
        json.put("screen x", screenX);
        json.put("screen y", screenY);
        json.put("ended", ended);
        json.put("max fruit", maxNumberOfFallingFruit);
        json.put("max enemies", maxNumberOfFallingEnemies);
        return json;
    }

    //EFFECTS: returns falling fruit  in this game as a JSON array
    public JSONArray fallingFruitToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Fruit f : this.fallingFruit) {
            jsonArray.put(f.toJson());
        }
        return jsonArray;
    }

    //EFFECTS: returns falling enemies  in this game as a JSON array
    public JSONArray fallingEnemiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Enemy e : this.fallingEnemies) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }

    //EFFECTS: returns fruit in basket in this game as a JSON array
    public JSONArray basketFruitToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Fruit f : this.basket.getFruitInBasket()) {
            jsonArray.put(f.toJson());
        }
        return jsonArray;
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
        return this.basket.getFruitInBasket();
    }

    public int getScore() {
        return this.score;
    }

    public List<Enemy> getEnemies() {
        return this.fallingEnemies;
    }

    public Basket getBasket() {
        return this.basket;
    }

    public void setScore(int score) {
        this.score = score;
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

    public void setIsEnded(boolean b) {
        this.ended = b;
    }


}


