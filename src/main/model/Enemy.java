package model;

import org.json.JSONObject;
import persistance.Writable;

//Represents an enemy
public class Enemy implements Writable {
    private int posX;
    private int posY;

    //constructs an enemy at the given x and y location
    public Enemy(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x position", posX);
        json.put("y position", posY);
        return json;
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