package view;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import controllers.*;
import javax.swing.*;
import model.*;

import component.ModernButton;

public class Login {
    //create a universal user data

    public static Person user  = new Person();
    public static boolean logInSuccessfull = false;

    //===========================
    JFrame frame = new JFrame();
    //--------------

    JPanel panelImage, panelRegister, panelButton, panelEmail, panelPass;
    JLabel emailLabel, passWordL, title, image;
    JTextField emailText;
    JPasswordField passWord;
    JRadioButton employeeButton, adminButton;
    ButtonGroup group;
    ModernButton loginButton;
    Font font;
    ImageIcon icon;
    String useremailText, password;
    boolean checkFlag = true;
    private ImageIcon[] images;
    private int currentImageIndex;

    public Login() {
        frame.setTitle("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 2));
        frame.setSize(900, 560);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        panelImage = new JPanel(new BorderLayout());
        panelRegister = new JPanel(new GridBagLayout());

        // Create GridBagConstraints for positioning and sizing components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        emailText = new JTextField(20);
        passWord = new JPasswordField(20);
        employeeButton = new JRadioButton("Employee");
        adminButton = new JRadioButton("Administer");
        adminButton.setForeground(Color.WHITE);
        employeeButton.setSize(100, 100);
        adminButton.setSize(50, 50);
        adminButton.setBackground(Color.DARK_GRAY);
        employeeButton.setForeground(Color.WHITE);
        employeeButton.setBackground(Color.DARK_GRAY);
        group = new ButtonGroup();
        group.add(employeeButton);
        group.add(adminButton);
        loginButton = new ModernButton("LOGIN",new Color(30,30,100));

        panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelButton.add(adminButton);
        panelButton.add(employeeButton);
        panelButton.setBackground(Color.DARK_GRAY);
        title = new JLabel("LOGIN");
        title.setForeground(Color.WHITE);
        font = new Font("italic", Font.BOLD, 13);
        title.setFont(font);

        emailLabel = new JLabel("email :");
        emailLabel.setForeground(Color.WHITE);
        panelEmail = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEmail.setBackground(Color.DARK_GRAY);
        panelEmail.add(emailLabel);
        panelEmail.add(emailText);

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelPass = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passWordL = new JLabel("Password :");
        passWordL.setForeground(Color.WHITE);
        panelPass.add(passWordL);
        panelPass.add(passWord);
        panelPass.setBackground(Color.DARK_GRAY);
        panelRegister.add(panelPass, gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelRegister.add(title, gbc);

        panelRegister.setBackground(Color.DARK_GRAY);
        gbc.gridy = 1;
        gbc.gridwidth = 1;

        panelRegister.add(panelEmail, gbc);

        gbc.gridy = 3;
        panelRegister.add(panelButton, gbc);
        adminButton.addItemListener(new ItemsHandler());
        employeeButton.addItemListener(new ItemsHandler());

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelRegister.add(loginButton, gbc);

        image = new JLabel();
        // URL url = getClass().getResource("th (1).jpg");
        // if (url != null) {
        // icon = new ImageIcon(url);
        // image.setIcon(icon);
        // image.setHorizontalAlignment(JLabel.CENTER);
        // image.setVerticalAlignment(JLabel.CENTER);
        // panelImage.add(image, BorderLayout.CENTER);
        // } else {
        // JOptionPane.showMessageDialog(this, "Could not find image file");
        // }
        images = new ImageIcon[3];
        images[0] = new ImageIcon(getClass().getResource("../resources/cover2.jpg"));
        images[1] = new ImageIcon(getClass().getResource("../resources/coverMain.jpg"));
        images[2] = new ImageIcon(getClass().getResource("../resources/coverMain.jpg"));
        // images[3] = new
        // ImageIcon(getClass().getResource("istockphoto-468420738-612x612.jpg"));
        currentImageIndex = 0;

        // Set the first image on the label
        image = new JLabel(images[currentImageIndex]);

        // Add the image to the panelImage
        image.setHorizontalAlignment(JLabel.CENTER);
        image.setVerticalAlignment(JLabel.CENTER);
        panelImage.add(image, BorderLayout.CENTER);
        panelImage.setBackground(Color.DARK_GRAY);
        // Create a Timer to switch images at a fixed interval
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentImageIndex = (currentImageIndex + 1) % images.length;
                image.setIcon(images[currentImageIndex]);
            }
        });
        timer.start();
        // Add the main panel and image panel to the frame
        frame.add(panelRegister);
        frame.add(panelImage);
        

        // Add a ComponentListener to the frame to resize the components when the window
        // is resized
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeComponents();
            }
        });
        loginButton.addActionListener(new ButtonHandler());
        
        // Set the size of the frame and make it visible
        
    }

    private void resizeComponents() {
        // Get the current size of the panelRegister panel
        Dimension size = panelRegister.getSize();

        // Update the preferred size of the emailText and password fields
        emailText.setPreferredSize(new Dimension(size.width - 20, emailText.getPreferredSize().height));
        passWord.setPreferredSize(new Dimension(size.width - 20, passWord.getPreferredSize().height));

        // Scale the image to fit inside the panel while preserving aspect ratio
        int width = panelImage.getWidth();
        int height = panelImage.getHeight();
        if (icon != null && width > 0 && height > 0) {
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImg);
            image.setIcon(scaledIcon);
        }

        // Repaint the panel to reflect the updated preferred sizes
        panelRegister.revalidate();
        panelRegister.repaint();
    }

    public static void main(String[] args) {
        new Login();
    }

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {

             
            loginController login = new loginController(useremailText, password);
            if (checkFlag) {
                
                if (login.checkAdmin()) {
                    
                    logInSuccessfull = true;
                    user = new Administrator();
                    user.setName(useremailText);
                    frame.setVisible(false);
                    new Dashboard(user);
                    System.out.println("login succesfull");
                } else {
                    logInSuccessfull = false;
                    
                    System.out.println("login not succesfull");
                }
            } else {
                if (login.checkEmployee()) {
                    logInSuccessfull = true;
                    user  =  new Employee();
                    user.setName(useremailText);
                    frame.setVisible(false);
                    new Dashboard(user);
                    System.out.println("login succesfull");
                } else {
                    logInSuccessfull = false;
                    System.out.println("login not succesfull");
                }
            }
        }
    }

    private class ItemsHandler implements ItemListener {
        public void itemStateChanged(ItemEvent ie) {
            if (adminButton.isSelected()) {
                // work
                useremailText = emailText.getText();
                password = new String(passWord.getPassword());
               
                
                checkFlag = true;
            } else if (employeeButton.isSelected()) {
                useremailText = emailText.getText();
                password = new String(passWord.getPassword());
                checkFlag = false;
            }
        }
    }
}