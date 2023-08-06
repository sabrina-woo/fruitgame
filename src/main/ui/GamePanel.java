package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

import model.Basket;
import model.Enemy;
import model.Fruit;
import model.Game;

import persistance.JsonReader;
import persistance.JsonWriter;

import static com.sun.java.accessibility.util.AWTEventMonitor.removeActionListener;
import static java.awt.Color.*;

public class GamePanel extends JPanel implements ActionListener {
    private static final String GAME_OVER = "Game Over";
    private Game game;
    private JButton save;
    private JButton load;
    private JButton no;
    private FruitGame fruitGame;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/savedGame.json";


    //Constructs a game panel
    //EFFECTS: c
    public GamePanel(Game g, FruitGame fruitGame) throws IOException {
        setPreferredSize(new Dimension(g.getX(), g.getY()));
        setBackground(Color.PINK);
        this.game = g;
        this.fruitGame = fruitGame;
        loadButton();
        noLoadButton();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
        revalidate();
//        promptSaveButton();
    }

    private void drawGame(Graphics g) {
        drawBasket(g);
        drawFruit(g);
        drawEnemies(g);
        drawBasketFruit(g);
        getScore(g);
    }

    private void getScore(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString("Score: " + game.getScore(), g, fm, game.getY() / 9);
        g.setColor(WHITE);
    }

    private void drawBasket(Graphics g) {
        Basket basket = game.getBasket();
        g.setColor(Basket.COLOR);
        g.fillRect(basket.getX() - Basket.SIZE_X / 2, basket.getY() - Basket.SIZE_Y / 2, Basket.SIZE_X, Basket.SIZE_Y);

        g.setColor(Basket.COLOR);
    }

    //EFFECTS: renders each fruit in a list of fruit
    private void drawFruit(Graphics g) {
        List<Fruit> fruit = game.getFruit();
        for (Fruit currentFruit : fruit) {
            drawEachFruit(g, currentFruit);
        }
    }

    private void drawBasketFruit(Graphics g) {
        List<Fruit> fruit = game.getFruitInBasket();
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

    private void centreString(String str, Graphics g, FontMetrics fm, int posY) {
        int width = fm.stringWidth(str);
        g.drawString(str, (game.getX() - width) / 2, posY);
    }

    private void loadButton() {
        load = new JButton("Yes");
        load.setActionCommand("load");
        load.addActionListener(this);
        add(load);
    }

    private void noLoadButton() {
        no = new JButton("no");
        no.setActionCommand("no");
        no.addActionListener(this);
        add(no);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("load")) {
            loadGame();
            this.remove(load);
            this.remove(no);
            fruitGame.validateFruitGame();

        }
        if (e.getActionCommand().equals("no")) {
            this.remove(no);
            this.remove(load);
            fruitGame.validateFruitGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadGame() {
        try {
            this.game = jsonReader.read();
            fruitGame.setGame(game);
        } catch (IOException e) {
            fruitGame.setGame(game);
        }
    }

    // EFFECTS: saves the workroom to file
    public void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            fruitGame.setGame(game);
        }
    }




}
