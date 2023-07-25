package ui;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        //Create and start the game
        try {
            TerminalGame terminalGame = new TerminalGame();
            terminalGame.start();;
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run game. Saved file not found");
        }

//        TerminalGame terminalGame = new TerminalGame();
//        terminalGame.start();

    }
}
