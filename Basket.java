package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//Class representing a basket/player
public class Basket {

    public static final int SIZE_X = 45;
    public static final int SIZE_Y = 45;
    private int direction;
    private int posX;
    private int posY;
    private ArrayList<Fruit> fruitInBasket;

    public static final Color COLOR = new Color(94, 61, 34);
    public static final int basketYPos = 51;

    //Constructs the basket/player
    public Basket(int x, int y) {
        this.posX = x / 2;
        this.posY = y - basketYPos;
        this.direction = 0;
        this.fruitInBasket = new ArrayList<>();
    }

    public void addFruitInBasket(Fruit fruit) {
        this.fruitInBasket.add(fruit);
    }

    //moves the basket by the value of direction
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