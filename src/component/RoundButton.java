package component;

import javax.swing.*;
import java.awt.*;

public class RoundButton extends JButton {

    public RoundButton(String text) {
        super(text);
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.lightGray);
        } else {
            g.setColor(getBackground());
        }
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }

    @Override
    public boolean contains(int x, int y) {
        if (super.contains(x, y)) {
            int centerX = getSize().width / 2;
            int centerY = getSize().height / 2;
            int radius = getSize().width / 2;
            return ((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY)) <= radius * radius;
        } else {
            return false;
        }
    }
}