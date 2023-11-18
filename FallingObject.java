package model;


public abstract class FallingObject {
    protected int posX;
    protected int posY;
    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 20;

    protected String name;

    public FallingObject(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    public void move() {
        this.posY = this.posY + 3;
    }

    public int getX() {
        return this.posX;
    }

    public int getY() {
        return this.posY;
    }

    public String getName() {
        return this.name;
    }

}