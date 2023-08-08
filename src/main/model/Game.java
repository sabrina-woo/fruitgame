package model;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import static java.lang.Integer.valueOf;

//Represents the game
public class Game implements Writable {

    private Basket basket;
    private List<Fruit> fallingFruit;
    private List<Enemy> fallingEnemies;
    private boolean ended;
    private int score;

    int maxNumberOfFallingFruit = 6;
    int maxNumberOfFallingEnemies = 3;

    private int screenX;
    private int screenY;
    public static final int TICKS = 20;
    private EventLog eventLog;

    //Constructs a game
    //EFFECTS: creates a game with an empty list of fruit and enemies, and the score starting at zero
    public Game(int x, int y) {
        this.screenX = x;
        this.screenY = y;
        this.basket = new Basket(x, y);
        this.fallingFruit = new ArrayList<>();
        this.fallingEnemies = new ArrayList<>();
        this.score = 0;
        this.ended = false;
        this.eventLog = EventLog.getInstance();
    }

    //REQUIRES: TICKS >= 1
    //MODIFIES: this
    //EFFECTS: updates the fruit and enemies
    public void update() {
        moveFruit(this.fallingFruit);
        moveEnemies(this.fallingEnemies);
        createFruit();
        createEnemies();
        handleEnemies();
        handleFood();
    }


    //MODIFIES: this
    //EFFECTS: creates one fruit at a random x position and the given y position
    public Fruit createOneFruit() {
        Random random = new Random();
        int posX = this.screenX;
        Fruit fruit = new Fruit(random.nextInt(screenX + 1), 1);

        return fruit;
    }

    //MODIFIES: this
    //EFFECTS: creates one fruit at a random x position and a random y position
    public void addOneFruitRandom() {
        Random random = new Random();
        int posX = this.screenX;
        Fruit fruit = new Fruit(random.nextInt(screenX + 1), random.nextInt(screenY + 1));

        this.getBasket().getFruitInBasket().add(fruit);
    }

    //MODIFIES: this
    //EFFECTS: creates one fruit at a random x position and a random y position
    public void removeTopHalf() {
        for (int i = 0; i < this.getFruitInBasket().size(); i++) {
            Fruit fruit = this.getFruitInBasket().get(i);
            if (fruit.getY() < this.screenY / 2) {
                this.getBasket().getFruitInBasket().remove(fruit);
                i--;
                eventLog.logEvent(new Event("Fruit on top half of screen removed"));
            }
        }
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
            eventLog.logEvent(new Event("Fruit has been created"));
        }
    }

    //EFFECTS: creates one enemy at a random x position and the given y position
    public Enemy createOneEnemy() {
        Random random = new Random();
        int posY = this.screenX;
        Enemy enemy = new Enemy(random.nextInt(screenX + 1), 1);
        eventLog.logEvent(new Event("Enemy has been created"));
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
                eventLog.logEvent(new Event("Fruit off the screen was removed"));
            }

            boolean leftBasketEdge =  fruit.getX() >= basket.getX() - (Basket.SIZE_X / 2);
            boolean rightBasketEdge =  fruit.getX() <= basket.getX() + Basket.SIZE_X;
            boolean valueRange = leftBasketEdge && rightBasketEdge;

            if (valueRange && basket.getY() == fruit.getY()) {
                fallingFruit.remove(fruit);
                this.basket.addFruitInBasket(fruit);
                this.score++;
                i--;
                eventLog.logEvent(new Event("Fruit has been added to basket"));
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
                eventLog.logEvent(new Event("Enemy has been removed from the screen"));
            }

            boolean leftBasketEdge =  enemy.getX() >= basket.getX() - (Basket.SIZE_X / 2);
            boolean rightBasketEdge =  enemy.getX() <= basket.getX() + Basket.SIZE_X;
            boolean valueRange = leftBasketEdge && rightBasketEdge;

            if (valueRange && basket.getY() == enemy.getY()) {
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

    public void removeRandomFruit() {
        int size = this.getFruitInBasket().size();
        Random random = new Random();
        if (size > 0) {
            this.getFruitInBasket().remove(random.nextInt(size));
        }
    }

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
        return this.getFruitInBasket().size();
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


