package model;

//Represents a fruit
public class Fruit {
    private int posX;
    private int posY;

    //constructs a fruit at the given x and y location
    public Fruit(int x, int y) {
        this.posX = x;
        this.posY = y;
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
