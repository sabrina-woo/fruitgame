package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.*;

import model.Fruit;
import model.Game;

import persistance.JsonWriter;

import static java.awt.Color.*;

public class EndScreen extends JPanel implements ActionListener {
    private static final String GAME_OVER = "Game Over";
    private Game game;
    private JButton save;
    private JsonWriter jsonWriter;
    private ImageIcon ferretImage;
    private JLabel imageAsLabel;
    private JPanel ferretPanel;

    private static final String JSON_STORE = "./data/savedGame.json";

    //Constructs an ending screen
    public EndScreen(Game g) {
        setPreferredSize(new Dimension(g.getX(), g.getY()));
        setBackground(Color.PINK);
        jsonWriter = new JsonWriter(JSON_STORE);

        this.game = g;

        ferretPanel = new JPanel();
        ferretPanel.setPreferredSize(new Dimension(g.getX() / 5, g.getY() / 5));
        add(ferretPanel);

        loadImage();

        addFruit();
        removeFruit();
        removeFruitTopHalf();
        saveButton();
    }

    //MODIFIES: this
    //EFFECTS: repaints components
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBasketFruit(g);
        getGameOverText(g);
        getScore(g);
    }

    //MODIFIES: this
    //EFFECTS: renders the fruit caught by the player
    private void drawBasketFruit(Graphics g) {
        List<Fruit> fruit = game.getFruitInBasket();
        for (Fruit currentFruit : fruit) {
            drawEachFruit(g, currentFruit);
        }
    }

    //MODIFIES: this
    //EFFECTS: renders a single fruit
    private void drawEachFruit(Graphics g, Fruit fruit) {
        g.setColor(Fruit.COLOR);
        g.fillOval(fruit.getX() - Fruit.SIZE_X / 2, fruit.getY() - Fruit.SIZE_Y / 2, Fruit.SIZE_X, Fruit.SIZE_Y);
        g.setColor(Fruit.COLOR);
    }

    //MODIFIES: this
    //EFFECTS: renders the game over text
    private void getGameOverText(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", 20, 50));
        FontMetrics fm = g.getFontMetrics();
        centreString(GAME_OVER, g, fm, game.getY() / 2);
        g.setColor(WHITE);
    }

    //MODIFIES: this
    //EFFECTS: renders the player's score
    private void getScore(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString("Score: " + game.getScore(), g, fm, (game.getY() / 2) + 40);
        g.setColor(WHITE);
    }

    //MODIFIES: this
    //EFFECTS: centres the given Graphic
    private void centreString(String str, Graphics g, FontMetrics fm, int posY) {
        int width = fm.stringWidth(str);
        g.drawString(str, (game.getX() - width) / 2, posY);
    }

    //MODIFIES: this
    //EFFECTS: creates an addFruit button
    private void addFruit() {
        JButton btn = new JButton("Add fruit");
        btn.setActionCommand("add fruit");
        btn.addActionListener(this);
        add(btn);
    }

    //MODIFIES: this
    //EFFECTS: creates a removeFruit button
    private void removeFruit() {
        JButton btn = new JButton("Remove fruit");
        btn.setActionCommand("remove fruit");
        btn.addActionListener(this);
        add(btn);
    }

    //MODIFIES: this
    //EFFECTS: creates a button that removes all fruit on the top half of the screen
    private void removeFruitTopHalf() {
        JButton btn = new JButton("Remove fruit top Screen");
        btn.setActionCommand("remove fruit top half of screen");
        btn.addActionListener(this);
        add(btn);
    }

    //MODIFIES: this
    //EFFECTS: takes in button input
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add fruit")) {
            game.addOneFruitRandom();
        }
        if (e.getActionCommand().equals("remove fruit")) {
            game.removeRandomFruit();
        }
        if (e.getActionCommand().equals("remove fruit top half of screen")) {
            game.removeTopHalf();
        }
        if (e.getActionCommand().equals("save")) {
            saveGame();
        }
    }

    //MODIFIES: this
    //EFFECTS: creates a save button
    private void saveButton() {
        save = new JButton("Save");
        save.setActionCommand("save");
        save.addActionListener(this);
        add(save);
    }

    //MODIFIES: this
    // EFFECTS: saves the workroom to file
    public void saveGame() {
        try {
            game.setIsEnded(false);
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file");
        }
    }

    private void loadImage() {
        String sep = System.getProperty("file.separator");
        ferretImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "pic.PNG");
        imageAsLabel = new JLabel(ferretImage);
        ferretPanel.add(imageAsLabel);
    }

}