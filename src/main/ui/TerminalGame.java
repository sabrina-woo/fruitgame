package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
import java.util.List;

import model.Basket;
import model.Enemy;
import model.Fruit;
import model.Game;

//Represents the  window where the fruit game is played
//NOTE: A lot of the code in the TerminalGame class is similar to the code found in the tutorial at: https://github.students.cs.ubc.ca
//CPSC210/SnakeConsole-Lanterna/blob/main/src/main/java/com/mazenk/snake/ui/TerminalGame.java
// with the functionality changed for the purpose of my game
public class TerminalGame {
    private Game game;
    private Screen screen;
    private WindowBasedTextGUI gameGUI;

    //EFFECTS: sets up the window where the game is played and starts the game
    public void start() throws IOException, InterruptedException {
        screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();

        TerminalSize terminalSize = screen.getTerminalSize();

        game = new Game((terminalSize.getColumns() - 1) / 2, terminalSize.getRows() - 2);

        beginTicks();

    }

    //EFFECTS: Begins ticking cycle and continues the ticking cycle if the game is not ended,
    // otherwise if the game is ended, exits the game.
    private void beginTicks() throws IOException, InterruptedException {
        while (!game.isEnded() || gameGUI.getActiveWindow() != null) {
            tick();
            Thread.sleep(1000L / Game.TICKS);
        }
        System.exit(0);
    }

    //EFFECTS: on each tick, calls the render function to render updated positions and takes new user input
    private void tick() throws IOException {
        handleUserInput();
        game.tick();
        screen.setCursorPosition(new TerminalPosition(0, 0));
        screen.clear();
        render();
        screen.refresh();
        screen.setCursorPosition(new TerminalPosition(screen.getTerminalSize().getColumns() - 1, 0));

    }

    //MODIFIES: this
    //EFFECTS: takes user arrow key input and moves the user's position depending on the input
    private void handleUserInput() throws IOException {
        KeyStroke stroke = screen.pollInput();

        if (stroke == null) {
            return;
        }
        if (stroke.getCharacter() != null) {
            return;
        }
        int direction = directionFrom(stroke.getKeyType());

        game.getBasket().move(direction);

    }

    //EFFECTS: returns an integer depending on the arrow key pressed
    public int directionFrom(KeyType type) {
        if (type == KeyType.ArrowRight) {
            return 1;
        } else if (type == KeyType.ArrowLeft) {
            return -1;
        } else {
            return 0;
        }
    }

    //EFFECTS: renders the game screen if the game is not over, or renders the ending scene if the game is over
    private void render() {
        if (game.isEnded()) {
            if (gameGUI == null) {
                drawEndScreen();
            }
            return;
        }

        drawScore();
        drawBasket();
        drawFruit();
        drawEnemies();
    }

    //MODIFIES: this
    //EFFECTS: renders the end screen
    private void drawEndScreen() {
        gameGUI = new MultiWindowTextGUI(screen);
        new MessageDialogBuilder()
                .setTitle("Game Over")
                .setText("Score: " + game.getScore())
                .build()
                .showDialog(gameGUI);
    }

    //EFFECTS: renders the game score
    private void drawScore() {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(1, 0, "Score: ");

        text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(8, 0, String.valueOf(game.getScore()));
    }

    //EFFECTS: renders the player
    private void drawBasket() {
        Basket basket = game.getBasket();
        drawPosition(basket.getX(), basket.getY(), TextColor.ANSI.BLUE, '█', true);
    }

    //EFFECTS: renders each fruit in a list of fruit
    private void drawFruit() {
        List<Fruit> fruit = game.getFruit();
        for (Fruit currentFruit : fruit) {
            drawPosition(currentFruit.getX(), currentFruit.getY(), TextColor.ANSI.GREEN, '⬤', false);
        }

    }

    //EFFECTS: renders each enemy in a list of enemies
    private void drawEnemies() {
        List<Enemy> enemy = game.getEnemies();
        for (Enemy currentEnemy : enemy) {
            drawPosition(currentEnemy.getX(), currentEnemy.getY(), TextColor.ANSI.RED, '⬤', false);
        }
    }

    //EFFECTS: renders an object
    private void drawPosition(int x, int y, TextColor colour, char c, boolean wide) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(colour);
        text.putString(x * 2, y + 1, String.valueOf(c));
    }

}
