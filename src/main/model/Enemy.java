package model;

import java.util.ArrayList;
import java.util.List;

//Represents an enemy
public class Enemy {
    private int posX;
    private int posY;

    //constructs an enemy at the given x and y location
    public Enemy(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    //MODIFIES: this
    //EFFECTS: moves an enemy down one unit
    public void move() {
        this.posY = this.posY + 1;
    }

    public int getX() {
        return this.posX;
    }

    public int getY() {
        return this.posY;
    }

}