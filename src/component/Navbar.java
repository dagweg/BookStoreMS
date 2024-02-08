package component;




import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Navbar extends JPanel {

    private static final Color NAVBAR_BACKGROUND_COLOR = new Color(33, 45,34);
    private static final Color USERNAME_TEXT_COLOR = Color.WHITE;
    private static final Font USERNAME_FONT = new Font("Arial", Font.BOLD, 18);

    private static final int AVATAR_SIZE = 40;
    private static final int AVATAR_PADDING = 10;

    private BufferedImage avatarImage;
    private JLabel usernameLabel;

    public Navbar(String username, URL avatarUrl) {
        try {
            avatarImage = ImageIO.read(avatarUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(300, 60));
        setBackground(NAVBAR_BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        JPanel avatarPanel = new JPanel();
        avatarPanel.setLayout(new FlowLayout(FlowLayout.LEFT, AVATAR_PADDING, AVATAR_PADDING));
        avatarPanel.setBackground(NAVBAR_BACKGROUND_COLOR);

        JLabel avatarLabel = new JLabel(new ImageIcon(avatarImage.getScaledInstance(AVATAR_SIZE, AVATAR_SIZE, Image.SCALE_SMOOTH)));
        avatarLabel.setPreferredSize(new Dimension(AVATAR_SIZE, AVATAR_SIZE));
        avatarLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        avatarPanel.add(avatarLabel);

        usernameLabel = new JLabel(username);
        usernameLabel.setFont(USERNAME_FONT);
        usernameLabel.setForeground(USERNAME_TEXT_COLOR);
        usernameLabel.setBorder(new EmptyBorder(0, AVATAR_PADDING, 0, 0));
        avatarPanel.add(usernameLabel);

        add(avatarPanel, BorderLayout.WEST);



        //this is a panel that containt the login/logout,settings buttons

        JPanel sessionPanel = new JPanel();
        sessionPanel.setBackground(NAVBAR_BACKGROUND_COLOR);

        ModernButton signIO =  new ModernButton("sign In/Out",NAVBAR_BACKGROUND_COLOR);
        ModernButton accounts = new ModernButton("Accounts",NAVBAR_BACKGROUND_COLOR);
        
        sessionPanel.add(accounts);
        sessionPanel.add(signIO);

        add(sessionPanel,BorderLayout.EAST);

    }

    public void setUsername(String username) {
        usernameLabel.setText(username);
    }
}