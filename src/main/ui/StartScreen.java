//package ui;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class StartScreen extends JPanel implements ActionListener {
//
//    private JLabel label;
//    private JTextField field;
//
//
//    public StartScreen() {
//
//        setBackground(Color.PINK);
//        g.setColor(new Color(255, 255, 255, 0));
//        g.setFont(new Font("Arial", 20, 50));
//        FontMetrics fm = g.getFontMetrics();
//        centreString("Press PLAY to start game", g, fm, HEIGHT / 2);
//        centreString("Press LOAD to load a saved game", g, fm, HEIGHT / 2 + 200);
//        makeStartButton();
//    }
//
//    private void makeStartButton() {
//        setPreferredSize(new Dimension(300, 150));
//        setLayout(new FlowLayout());
//        JButton btn = new JButton("Start");
//        btn.setActionCommand("start");
//        btn.addActionListener(this); // Sets "this" object as an action listener for btn
//        this.field = new JTextField(5);
//        label = new JLabel("flag");
//
//        add(this.field);
//        add(btn);
//        add(label);
//        pack();
//        setLocationRelativeTo(null);
//        setVisible(true);
//        setResizable(false);
//
//    }
//
//    private void centreString(String str, Graphics g, FontMetrics fm, int posY) {
//        int width = fm.stringWidth(str);
//        g.drawString(str, (WIDTH - width) / 2, posY);
//    }
//
//    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand().equals("myButton")) {
//            label.setText(field.getText());
//        }
//    }
//}
