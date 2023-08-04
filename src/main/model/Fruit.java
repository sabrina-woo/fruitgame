package model;

import org.json.JSONObject;
import persistance.Writable;

import java.awt.*;

//Represents a fruit
public class Fruit implements Writable {
    private int posX;
    private int posY;

    public static final Color COLOR = new Color(229, 89, 89);
    public static final int SIZE_X = 15;
    public static final int SIZE_Y = 9;

    //constructs a fruit at the given x and y location
    public Fruit(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    //EFFECTS: places the x and y position of a fruit into a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x position", posX);
        json.put("y position", posY);
        return json;
    }

    //MODIFIES: this
    //EFFECTS: moves a fruit down one unit
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
