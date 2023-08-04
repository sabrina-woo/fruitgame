package ui;

import model.Game;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class SidePanel extends JPanel {

    private static final String SCORE_TXT = "Score: ";
    private static final int LBL_WIDTH = 200;
    private static final int LBL_HEIGHT = 30;
    private Game game;
    private JLabel scoreLabel;

    public SidePanel(Game g) {
        game = g;
        setBackground(new Color(255, 180, 214, 186));
        scoreLabel = new JLabel(SCORE_TXT + game.getFruitInBasket().size());
        scoreLabel.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        add(scoreLabel);
        add(Box.createHorizontalStrut(10));
    }

    public void update() {
        scoreLabel.setText(SCORE_TXT + game.getScore());
        repaint();
    }


}
