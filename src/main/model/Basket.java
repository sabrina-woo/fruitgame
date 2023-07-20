package model;

import java.util.ArrayList;

//Class representing a basket/player
public class Basket {
    private int direction;
    private int posX;
    private int posY;
    private ArrayList<Fruit> fruitInBasket;

    public static final int posYConstant = 20;
    public static final int posXStarting = 10;

    //Constructs the basket/player
    public Basket() {
        this.posX = posXStarting;
        this.posY = posYConstant;
        this.direction = 0;
        this.fruitInBasket = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: moves the basket by the value of direction
    public void move(int i) {
        this.posX = this.posX + i;
    }

    public int getX() {
        return this.posX;
    }

    public int getY() {
        return this.posY;
    }

    public int getDirection() {
        return this.direction;
    }

    public ArrayList<Fruit> getFruitInBasket() {
        return this.fruitInBasket;
    }

    public void setPosition(int i) {
        this.posX = i;
        this.posY = i;
    }

    public void setXBasket(int i) {
        this.posX = i;
    }

    public void setYBasket(int i) {
        this.posY = i;
    }


}