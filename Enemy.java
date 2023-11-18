package model;


import java.awt.*;

//Represents an enemy
public class Enemy extends FallingObject {

    public static final Color COLOR = new Color(0, 0, 0);
    public static final int SIZE_X = 15;
    public static final int SIZE_Y = 9;

    //constructs an enemy at the given x and y location
    public Enemy(int x, int y) {
        super(x, y);
        this.name = "enemy";
    }

}