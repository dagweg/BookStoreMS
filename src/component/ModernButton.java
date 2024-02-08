package component;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ModernButton extends JButton {

    private static final Color BUTTON_COLOR = new Color(33, 150, 243);
    private static final Color BUTTON_HOVER_COLOR = new Color(21, 101, 162);
    private static final Color BUTTON_PRESSED_COLOR = new Color(14, 67, 107);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

    private static final int ARC_WIDTH = 20;
    private static final int ARC_HEIGHT = 20;
    private static final Border BUTTON_BORDER = BorderFactory.createEmptyBorder(10, 20, 10, 20);

    public ModernButton(String text,Color c) {
        super(text);

        setFont(new Font("Arial", Font.BOLD, 14));
        setForeground(BUTTON_TEXT_COLOR);
        setBackground(c);
        setBorder(BUTTON_BORDER);
        setFocusPainted(false);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(BUTTON_HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(c);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                setBackground(BUTTON_PRESSED_COLOR);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                setBackground(BUTTON_HOVER_COLOR);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(BUTTON_PRESSED_COLOR);
        } else if (getModel().isRollover()) {
            g.setColor(BUTTON_HOVER_COLOR);
        } else {
            g.setColor(BUTTON_COLOR);
        }
        g.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Do nothing to remove default button border
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ModernButton button = new ModernButton("Modern Button",Color.WHITE);
        frame.getContentPane().add(button, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}