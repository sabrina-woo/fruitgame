package model;

import java.awt.*;


//Represents a fruit
public class Fruit extends FallingObject {

    public static final Color COLOR = new Color(229, 89, 89);
    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 20;
    public int fallSpeed;

    //constructs a fruit at the given x and y location
    public Fruit(int x, int y, String name, int fallSpeed) {
        super(x, y);
        this.name = name;
        this.fallSpeed = fallSpeed;
    }

    @Override
    public void move() {
        this.posY = this.posY + fallSpeed;
    }



}
