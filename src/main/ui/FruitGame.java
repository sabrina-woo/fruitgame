package ui;

import model.Event;
import model.EventLog;
import model.Game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.Timer;


//Represents the main window in which the fruit game is played

public class FruitGame extends JFrame implements WindowListener {

    private static final int INTERVAL = 5;
    private Game game;
    private GamePanel gp;
    private EndScreen es;
    EventLog eventLog;



    //Constructs main window
    // effects: sets up window in which Space Invaders game will be played
    public FruitGame() throws IOException {
        super("Fruit Game");
        int screenWidth = getWidthDimensions();
        int screenHeight = getHeightDimensions();
        setSize(getWidthDimensions(), getHeightDimensions());

        eventLog = EventLog.getInstance();

        game = new Game(screenWidth, screenHeight);
        gp = new GamePanel(game, this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);

        start();

    }

    //MODIFIES: this
    //EFFECTS: adds GamePanel gp, adds KeyListener and Timer
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

    //MODIFIES: this
    //EFFECTS: centres the JPanels on screen
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    //MODIFIES: this
    //EFFECTS: if game is ended, removes gp, adds es, revalidates and repaints
    //otherwise, updates game and repaints gamepanel
    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (game.isEnded()) {
                    es = new EndScreen(game);
                    remove(gp);
                    add(es);
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

    //EFFECTS: moves the player in the corresponding arrow key direction
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

    //MODIFIES: this
    //EFFECTS: validates
    public void validateFruitGame() {
        validate();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        for (Event event : eventLog) {
            System.out.println(event.toString());
        }
    }


    @Override
    public void windowClosed(WindowEvent e) {
        for (Event event : eventLog) {
            System.out.println(event.toString());
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        for (Event event : eventLog) {
            System.out.println(event.toString());
        }
    }

//    public void printLog(EventLog el) {
//        for (Event next : el) {
//            System.out.println(next.toString());
//        }
//    }

    //EFFECTS: calls FruitGame to construct the game
    public static void main(String[] args) throws IOException {
        new FruitGame();
    }


}


