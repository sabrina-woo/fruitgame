package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//Represents the game
public class Game {

    private Basket basket;
    private List<FallingObject> fallingObjects;

    private boolean ended;

    int maxNumberOfFallingObjects = 15;

    private int screenX;
    private int screenY;

    //creates a game with an empty list of fruit and enemies, and the score starting at zero
    public Game(int x, int y) {
        this.screenX = x;
        this.screenY = y;
        this.basket = new Basket(x, y);
        this.fallingObjects = new ArrayList<>();
        this.ended = false;
    }

    //updates the game every tick
    public void update() {
        move();
        createFallingObjects();
        handle();
    }

    //every tick, makes a number of falling objects so that the number of falling objects is maxNumberOfFallingObjects
    public void createFallingObjects() {
        int numberOfFallingObjectToGenerate = maxNumberOfFallingObjects - fallingObjects.size();
        FallingObject object = null;
        for (int i = 0; i < numberOfFallingObjectToGenerate; i++) {
            Random random = new Random();
            int num = random.nextInt(maxNumberOfFallingObjects);
            if (num > 6) {
                object = new Enemy(random.nextInt(screenX + 1), 1);
            } else {
                object = createNewFruit();
            }
            fallingObjects.add(object);
        }
    }

    private FallingObject createNewFruit() {
        Random random = new Random();

        int num = random.nextInt(5);
        if (num == 1) {
            return new Fruit(random.nextInt(screenX + 1), 1, "blueberry", 2);
        } else if (num == 2) {
            return new Fruit(random.nextInt(screenX + 1), 1, "banana", 2);
        }  else if (num == 3) {
            return new Fruit(random.nextInt(screenX + 1), 1, "orange", 3);
        }  else if (num == 4) {
            return new Fruit(random.nextInt(screenX + 1), 1, "strawberry", 3);
        } else {
            return new Fruit(random.nextInt(screenX + 1), 1, "watermelon", 4);
        }

    }

    //removes falling objects if they are off-screen or hit the basket
    public void handle() {
        for (int i = 0; i < this.fallingObjects.size(); i++) {
            FallingObject object = this.fallingObjects.get(i);
            i = i + handleOffScreen(object);
            i = i + handleHitObjects(object);
        }
    }

    //removes falling objects if they are off-screen
    public int handleOffScreen(FallingObject object) {
        int index = 0;
        if (object.getY() > this.screenY) {
            fallingObjects.remove(object);
            index = -1;
        }
        return index;
    }

    //removes falling objects if they are hit by the basket
    public int handleHitObjects(FallingObject object) {
        int index = 0;
        if (hasHitXCoordinate(object) && hasHitYCoordinate(object)) {
            fallingObjects.remove(object);
            index = -1;
            if (object.getName().equals("enemy")) {
                this.ended = true;
            } else {
                handleFruit(object);

            }
        }
        return index;
    }

    //adds Fruit to the basket
    public void handleFruit(FallingObject object) {
        Fruit fruit = (Fruit) object;
        this.basket.addFruitInBasket(fruit);
    }

    //returns true if a falling object has hit the player/basket
    public boolean hasHitXCoordinate(FallingObject object) {
        //true if the right side of the object is within the left side of the basket
        boolean withinLeftSide = object.getX() + FallingObject.SIZE_X >= basket.getX();

        //true if the left side of the object is within the right side of the basket
        boolean withinRightSide = object.getX() <= basket.getX() + Basket.SIZE_X;

        boolean hasHit = withinLeftSide && withinRightSide;

        return hasHit;
    }

    public boolean hasHitYCoordinate(FallingObject object) {
        // withinTopBasket is true if the top of the object is within the top of the basket
        boolean withinTopBasket = object.getY() >= basket.getY();

        // withinBottomBasket is true if the bottom of the object is within the bottom of the basket
        boolean withinBottomBasket = object.getY() + FallingObject.SIZE_Y <= basket.getY() + Basket.SIZE_Y;

        boolean hasHit = withinTopBasket && withinBottomBasket;

        return hasHit;
    }

    //moves each falling object to a new position on tick
    public void move() {
        for (FallingObject current : this.fallingObjects) {
            current.move();
        }
    }

    // Adds a fruit to fallingFruit
    public void addFallingFruit(Fruit fruit) {
        this.fallingObjects.add(fruit);
    }

    //Adds an enemy to fallingEnemies
    public void addFallingEnemies(Enemy enemy) {
        this.fallingObjects.add(enemy);
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

    public List<Fruit> getFruitInBasket() {
        return this.basket.getFruitInBasket();
    }

    public int getScore() {
        return this.getFruitInBasket().size();
    }

    public List<FallingObject> getFallingObjects() {
        return this.fallingObjects;
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

}


