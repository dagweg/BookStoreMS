package component;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Card extends JPanel {
    private Dimension size;
    private String title;
    private Image image;

    public Card(String title, Dimension size, Color backgroundColor, Image image) {
        this.title = title;
        this.size = size;
        this.image = image;
       // this.setLayout(new BorderLayout());

        setPreferredSize(size);
        setBackground(backgroundColor);
        setFont(new Font("Roboto", Font.BOLD, 30));
        setForeground(new Color(100,100,100));
         // Create a rounded border with a corner radius of 20 pixels
        // Create a rounded border with a corner radius of 20 pixels
        setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.WHITE, 0),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
    }


     @Override
    protected void paintBorder(Graphics g) {
        // Graphics2D g2d = (Graphics2D) g.create();

        // // Set rendering hints for smooth border painting
        // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
    @Override

    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();

            // Set rendering hints for smooth image scaling
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Scale and draw the image in the center of the panel
            int imgWidth = image.getWidth(null);
            int imgHeight = image.getHeight(null);
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            double scale = Math.min((double) panelWidth / imgWidth, (double) panelHeight / imgHeight);
            int scaledWidth = (int) (imgWidth * scale);
            int scaledHeight = (int) (imgHeight * scale);
            int x = (panelWidth - scaledWidth) / 2;
            int y = (panelHeight - scaledHeight) / 2;
            g2d.drawImage(image, x, y, scaledWidth, scaledHeight, null);

            g2d.dispose();
        }



        // Draw the title text in the top-left corner of the panel
        g.setFont(getFont());
        g.setColor(Color.darkGray);
        g.drawString(title, 10, 30);
       
        
    }
    
}