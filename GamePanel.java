package ui;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.*;

import static java.awt.Color.*;

public class GamePanel extends JPanel implements ActionListener {
    private static final String GAME_OVER = "Game Over!";
    private static final int GAME_OVER_TEXT_SIZE = 50;
    private static final int SCORE_TEXT_SIZE = GAME_OVER_TEXT_SIZE/2;

    private static final int CENTRE_STRING_TEXT = 2;
    private int buttonNumber = 0;

    private Game game;
    private FruitGame fruitGame;
    private JButton restartButton;

    public BufferedImage blueberry;
    public BufferedImage banana;
    public BufferedImage orange;
    public BufferedImage strawberry;
    public BufferedImage watermelon;
    public BufferedImage basket;


    //Constructs a game panel
    public GamePanel(Game g, FruitGame fruitGame) {
        setLayout(null);
        setPreferredSize(new Dimension(g.getX(), g.getY()));
        setBackground(new Color(236, 177, 177));
        this.game = g;
        this.fruitGame = fruitGame;
        makeRestartButton();
        getImage();
    }

    //repaints the game
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!game.isEnded()) {
            drawGame(g);
        } else {
            makeGameOverComponents(g);
        }
    }

    private void makeGameOverComponents(Graphics g) {
        drawGameOverText(g);
        drawGameOverScore(g);
        addRestartButton();
        game.getBasket().setXBasket(game.getX()+100);
        repaint();
        revalidate();
    }

    private void makeRestartButton() {
        restartButton = new JButton("Restart");
            restartButton.setOpaque(true);
            restartButton.setFont(new Font("Arial", 20, 20));
            restartButton.setBackground(new Color(224, 139, 139));
            restartButton.setForeground(new Color(224, 139, 139));

            restartButton.setActionCommand("restart");
            restartButton.addActionListener(this);

            int centreScreenX = game.getX() / 2;
            int centreScreenY = game.getY() / 2;
            int buttonSizeX = 100;
            int buttonSizeY = 100;

            int buttonYPosition = centreScreenY - buttonSizeY / 2 + GAME_OVER_TEXT_SIZE + SCORE_TEXT_SIZE;

            restartButton.setBounds(centreScreenX - buttonSizeX / 2, buttonYPosition, 100, 50);

    }

    private void addRestartButton() {
        if (buttonNumber == 0) {
            buttonNumber++;
            add(restartButton);
        }
    }

    private void drawGameOverText(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", 20, GAME_OVER_TEXT_SIZE));
        FontMetrics fm = g.getFontMetrics();
        centreString(GAME_OVER, g, fm, game.getY() / CENTRE_STRING_TEXT - GAME_OVER_TEXT_SIZE);
        g.setColor(WHITE);
    }

    private void drawGameOverScore(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", 20, SCORE_TEXT_SIZE));
        FontMetrics fm = g.getFontMetrics();
        centreString("Score: " + game.getScore(), g, fm, game.getY() / CENTRE_STRING_TEXT);
        g.setColor(WHITE);
    }

    //draws basket, fruit, enemies, and score
    private void drawGame(Graphics g) {
        drawBasket(g);
        drawFallingObjects(g);
        drawScore(g);
    }

    // renders player's score
    private void drawScore(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString("Score: " + game.getScore(), g, fm, game.getY() / 9);
        g.setColor(WHITE);
    }

    //creates graphic for player's basket
    private void drawBasket(Graphics g) {

        Graphics2D graphic = (Graphics2D) g;

        graphic.drawImage(basket, game.getBasket().getX(), game.getBasket().getY(), Basket.SIZE_X, Basket.SIZE_Y, null);

    }

    //creates graphic for each falling object in the game
    private void drawFallingObjects(Graphics g) {
        for (int i = 0; i < game.getFallingObjects().size(); i++) {
            FallingObject object = game.getFallingObjects().get(i);
            if (object.getName().equals("enemy")) {
                Enemy enemy = (Enemy) object;
                drawEachEnemy(g, enemy);
            } else {
                Fruit fruit = (Fruit) object;
                drawEachFruit(g, fruit);
            }
        }
    }

    //creates graphic for each enemy in the game
    private void drawEachEnemy(Graphics g, Enemy enemy) {
        g.setColor(Enemy.COLOR);
        g.fillRect(enemy.getX() - Enemy.SIZE_X / 2, enemy.getY() - Enemy.SIZE_Y / 2, Enemy.SIZE_X, Enemy.SIZE_Y);
        g.setColor(Enemy.COLOR);
    }


    //creates graphic for a single fruit by the fruit name
    private void drawEachFruit(Graphics g, Fruit fruit) {

        Graphics2D graphic = (Graphics2D) g;

        if (fruit.getName().equals("blueberry")) {
            graphic.drawImage(blueberry, fruit.getX(), fruit.getY(), Fruit.SIZE_X, Fruit.SIZE_Y, null);
        } else if (fruit.getName().equals("banana")) {
            graphic.drawImage(banana, fruit.getX(), fruit.getY(), Fruit.SIZE_X, Fruit.SIZE_Y, null);
        } else if (fruit.getName().equals("orange")) {
            graphic.drawImage(orange, fruit.getX(), fruit.getY(), Fruit.SIZE_X, Fruit.SIZE_Y, null);
        } else if (fruit.getName().equals("strawberry")) {
            graphic.drawImage(strawberry, fruit.getX(), fruit.getY(), Fruit.SIZE_X, Fruit.SIZE_Y, null);
        } else {
            graphic.drawImage(watermelon, fruit.getX(), fruit.getY(), Fruit.SIZE_X, Fruit.SIZE_Y, null);
        }

    }

    //loads game images
    public void getImage() {
        try {
            blueberry = ImageIO.read(getClass().getResourceAsStream("/fruit/blueberry.png"));
            watermelon = ImageIO.read(getClass().getResourceAsStream("/fruit/watermelon.png"));
            banana = ImageIO.read(getClass().getResourceAsStream("/fruit/banana.png"));
            orange = ImageIO.read(getClass().getResourceAsStream("/fruit/orange.png"));
            strawberry = ImageIO.read(getClass().getResourceAsStream("/fruit/strawberry.png"));

            basket = ImageIO.read(getClass().getResourceAsStream("/basket.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // centres the given Graphic
    private void centreString(String str, Graphics g, FontMetrics fm, int posY) {
        int width = fm.stringWidth(str);
        g.drawString(str, (game.getX() - width) / 2, posY);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("restart")) {
            removeButtons();
            restartGame();
        }
    }

    private void restartGame() {
//        Game newGame = new Game(game.getX(), game.getY());
//        this.game = newGame;
//        fruitGame.setGame(newGame);
//        fruitGame.revalidate();
//        fruitGame.repaint();

        fruitGame.dispose();
        new FruitGame();
    }

    private void removeButtons() {
        remove(restartButton);
        this.buttonNumber = 0;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
