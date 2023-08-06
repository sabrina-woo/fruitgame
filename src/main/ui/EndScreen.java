package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.*;

import model.Fruit;
import model.Game;

import persistance.JsonReader;
import persistance.JsonWriter;

import static java.awt.Color.*;

public class EndScreen extends JPanel implements ActionListener {
    private static final String GAME_OVER = "Game Over";
    private Game game;
    private JButton save;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/savedGame.json";

    //Constructs a game panel
    //EFFECTS: c
    public EndScreen(Game g) {
        setPreferredSize(new Dimension(g.getX(), g.getY()));
        setBackground(Color.PINK);
        this.game = g;
        addFruit();
        removeFruit();
        saveButton();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBasketFruit(g);
        getGameOverText(g);
        getScore(g);
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

    private void getGameOverText(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", 20, 50));
        FontMetrics fm = g.getFontMetrics();
        centreString(GAME_OVER, g, fm, game.getY() / 2);
        g.setColor(WHITE);
    }

    private void getScore(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString("Score: " + game.getScore(), g, fm, (game.getY() / 2) + 40);
        g.setColor(WHITE);
    }

    private void centreString(String str, Graphics g, FontMetrics fm, int posY) {
        int width = fm.stringWidth(str);
        g.drawString(str, (game.getX() - width) / 2, posY);
    }

    private void addFruit() {
        JButton btn = new JButton("Add fruit");
        btn.setActionCommand("add fruit");
        btn.addActionListener(this);
        add(btn);
    }

    private void removeFruit() {
        JButton btn = new JButton("Remove fruit");
        btn.setActionCommand("remove fruit");
        btn.addActionListener(this);
        add(btn);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add fruit")) {
            game.getFruitInBasket().add(game.createOneFruitRandom());
        }
        if (e.getActionCommand().equals("remove fruit")) {
            game.removeRandomFruit();
        }
        if (e.getActionCommand().equals("save")) {
            saveGame();
        }
    }

    private void saveButton() {
        save = new JButton("Save");
        save.setActionCommand("save");
        save.addActionListener(this);
        add(save);
    }

    // EFFECTS: saves the workroom to file
    public void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file");
        }
    }

}