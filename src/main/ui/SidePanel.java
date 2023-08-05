package ui;

import model.Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SidePanel extends JPanel implements ActionListener {

    private static final String SCORE_TXT = "Score: ";

    private Game game;
    private JLabel sideBar;
    private JLabel scoreLabel;
    private int gameHeight;
    private int gameWidth;

    public SidePanel(Game g) {
        this.game = g;
        gameWidth = game.getX();
        gameHeight = game.getY();
        scoreLabel();
//        saveButton();
    }

    private void saveButton() {
        JButton btn = new JButton("Save");
        btn.setActionCommand("saved");
        btn.addActionListener(this);
        add(btn);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("saved")) {
            game.setIsEnded(true);
        }
    }

    public void update() {
        scoreLabel.setText(SCORE_TXT + game.getScore());
        repaint();
    }

    private void scoreLabel() {
        int scorePanelWidth = gameWidth / 5;
        int scorePanelHeight = gameHeight / 8;
        setBackground(new Color(255, 209, 231, 186));
        scoreLabel = new JLabel("Score: " + game.getScore());
        scoreLabel.setPreferredSize(new Dimension(scorePanelWidth, scorePanelHeight));
        add(scoreLabel);
        add(Box.createHorizontalStrut(10));
    }


}
