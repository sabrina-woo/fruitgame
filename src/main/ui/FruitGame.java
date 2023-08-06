package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.Timer;

public class FruitGame extends JFrame {

    private static final int INTERVAL = 5;
    private Game game;
    private GamePanel gp;
    private EndScreen es;
//    private StartScreen ss;

    //Constructs main window
    // effects: sets up window in which Space Invaders game will be played

    public FruitGame() throws IOException {
        super("Fruit Game");
        int screenWidth = getWidthDimensions();
        int screenHeight = getHeightDimensions();
        setSize(getWidthDimensions(), getHeightDimensions());

        game = new Game(screenWidth, screenHeight);
        gp = new GamePanel(game, this);
        es = new EndScreen(game);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);

        start();

    }

    private void start() {
        add(gp);
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
        addTimer();
    }


    private int getWidthDimensions() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        return (screen.width - getWidth()) / 2;
    }

    private int getHeightDimensions() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        return (screen.height - getHeight()) / 2;
    }

    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (game.isEnded()) {
                    add(es);
                    remove(gp);
                    revalidate();
                    es.repaint();
                } else {
                    gp.repaint();
                    game.update();
                }
                }
        });
        t.start();
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT) {
                game.getBasket().move(-10);
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                game.getBasket().move(10);
            } else if (keyCode == KeyEvent.VK_UP) {
                gp.saveGame();
            } else {
                game.getBasket().move(0);
            }
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void validateFruitGame() {
        validate();
    }


    public static void main(String[] args) throws IOException {
        new FruitGame();
    }

}


