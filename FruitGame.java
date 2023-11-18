package ui;

import model.Game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.JFrame;


//Represents the main window in which the fruit game is played
public class FruitGame extends JFrame implements Runnable {

    private GamePanel gamePanel;
    private Thread gameThread;
    private int FPS = 50;

    //Constructs main window
    public FruitGame() {
        super("Fruit Game");
        int screenWidth = getWidthDimensions();
        int screenHeight = getHeightDimensions();
        setResizable(false);
        setUndecorated(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game(screenWidth, screenHeight);
        gamePanel = new GamePanel(game, this);

        start();

    }

    //starts the game
    private void start() {
        add(gamePanel);
        addKeyListener(new KeyHandler());
        centreOnScreen();
        setVisible(true);
        pack();
        startGameThread();
    }

    //centres JPanels on screen
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //refreshes the game at the corresponding FPS
    @Override
    public void run() {
        double refreshInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / refreshInterval;

            lastTime = currentTime;
            if (delta >= 1) {
                gamePanel.repaint();
                gamePanel.getGame().update();
                delta--;
            }
        }
    }

    //moves the player in the direction of the corresponding arrow key
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT) {
                gamePanel.getGame().getBasket().move(-10);
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                gamePanel.getGame().getBasket().move(10);
            } else {
                gamePanel.getGame().getBasket().move(0);
            }
        }
    }

    private int getWidthDimensions() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        return (screen.width - getWidth()) / 2;
    }

    private int getHeightDimensions() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        return (screen.height - getHeight()) / 2;
    }

    public void setGame(Game game) {
        gamePanel.setGame(game);
        repaint();
        revalidate();
    }
}