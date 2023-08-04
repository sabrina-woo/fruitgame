package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import model.Basket;
import model.Enemy;
import model.Fruit;
import model.Game;

import static java.awt.Color.*;

public class GamePanel extends JPanel {
    private static final String GAME_OVER = "Game Over";
    private Game game;

    //Constructs a game panel
    //EFFECTS: c
    public GamePanel(Game g) {
        setPreferredSize(new Dimension(g.getX(), g.getY()));
        setBackground(Color.PINK);
        this.game = g;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
        if (game.isEnded()) {
            gameOver(g);
        }
    }

    private void drawGame(Graphics g) {
        drawBasket(g);
        drawFruit(g);
        drawEnemies(g);
    }

    private void drawBasket(Graphics g) {
        Basket basket = game.getBasket();
        g.setColor(Basket.COLOR);
        g.fillRect(basket.getX() - Basket.SIZE_X / 2, basket.getY() - Basket.SIZE_Y, Basket.SIZE_X, Basket.SIZE_Y);
        g.setColor(Basket.COLOR);
    }

    //EFFECTS: renders each fruit in a list of fruit
    private void drawFruit(Graphics g) {
        List<Fruit> fruit = game.getFruit();
        for (Fruit currentFruit : fruit) {
            drawEachFruit(g, currentFruit);
        }
    }

    private void drawEachFruit(Graphics g, Fruit fruit) {
        g.setColor(Fruit.COLOR);
        g.fillOval(fruit.getX() - Fruit.SIZE_X / 2, fruit.getY() - Fruit.SIZE_Y / 2, Fruit.SIZE_X, Fruit.SIZE_Y);
        g.setColor(Fruit.COLOR);
    }

    private void drawEnemies(Graphics g) {
        List<Enemy> enemy = game.getEnemies();
        for (Enemy currentEnemy : enemy) {
            drawEachEnemy(g, currentEnemy);
        }
    }

    private void drawEachEnemy(Graphics g, Enemy enemy) {
        g.setColor(Enemy.COLOR);
        g.fillRect(enemy.getX() - Enemy.SIZE_X / 2, enemy.getY() - Enemy.SIZE_Y / 2, Enemy.SIZE_X, Enemy.SIZE_Y);
        g.setColor(Enemy.COLOR);
    }

    private void gameOver(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(GAME_OVER, g, fm, game.getX() / 2);
        g.setColor(WHITE);
    }

    private void centreString(String str, Graphics g, FontMetrics fm, int posY) {
        int width = fm.stringWidth(str);
        g.drawString(str, (game.getX() - width) / 2, posY);
    }




}
