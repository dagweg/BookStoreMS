package view;


import javax.swing.plaf.basic.BasicTabbedPaneUI;

import com.mysql.cj.log.Log;

import component.Navbar;
import model.Administrator;
import model.Employee;
import model.Person;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class Dashboard extends JFrame {

    private JTabbedPane tabbedPane;
    private JPanel booksPannel, dashBoard, AuthorPublisherPanel,pointOfSalePanel;
    private JTable table1, table2, table3;
    private JPanel DashboardBody;
    Navbar navbar;

    public Dashboard(Person user) {

        setTitle("Dashboard");
        setSize(1200, 720);
        setMinimumSize(new Dimension(1200,720));
        setLocationRelativeTo(null);// to position the window at the center of the screen.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DashboardBody = new JPanel();
        DashboardBody.setLayout(new BorderLayout());
        // this is where we added the navebar
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        URL avatarUrl = getClass().getResource("avatar.png");
        Navbar navbar = new Navbar(Login.user.getName(), avatarUrl);

        panel.add(navbar, BorderLayout.NORTH);
        // Create the tabbed pane

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

        tabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
                return 250; // Set the width of each tab to 100 pixels
            }

            @Override
            protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
                return 50; // Set the height of each tab to 40 pixels
            }

            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int width,
                    int height, boolean isSelected) {
                Color color = isSelected ? new Color(40, 40, 40) : new Color(30, 30, 30);
                g.setColor(color);

                g.fillRect(x, y, width, height);

            }

            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int width,
                    int height, boolean isSelected) {
                g.setColor(Color.white);

            }

        });

        // Create the panels for each tab
        booksPannel = new Home();
        dashBoard = new JPanel();
        AuthorPublisherPanel = new AuthorAndPublisherUI();
        pointOfSalePanel = new PointOfSaleUI();
        JScrollPane POSScrollPane =new JScrollPane(pointOfSalePanel);
        

        // Add tables to the panels
        table1 = new JTable(10, 3);
        table2 = new JTable(5, 4);
        table3 = new JTable(8, 2);

        booksPannel.add(new JScrollPane(table1));
        dashBoard.add(new JScrollPane(table2));
        AuthorPublisherPanel.add(new JScrollPane(table3));
        BookList book = new BookList();

        // Add the panels to the tabbed pane
        tabbedPane.addTab("Dashboard", null, new Home(), getTitle());
        tabbedPane.addTab("Manage Books", new ImageIcon("avatar.png"), new ManageBooksUI(),"this is where the books are located\n a previleged person can add, read or delete books \n from this lovation.");
        tabbedPane.addTab("Point of Sale", null,POSScrollPane, "Table 2");
        tabbedPane.addTab("Sales Report", null, new BookReportUI(), "Table 2");
        tabbedPane.addTab("Authors", null, AuthorPublisherPanel, "Table 3");

        if(user instanceof Administrator){
             tabbedPane.addTab("Manage Employees", null, new ManageEmployeesUI(), "Table 2");
        }
        if(user instanceof Employee){
            Employee employee = (Employee)user;

            

        }

        tabbedPane.setForeground(new Color(200, 200, 200));

        // Set the tabbed pane to fill the entire frame
        DashboardBody.setBackground(new Color(30, 30, 30));
        DashboardBody.add(tabbedPane, BorderLayout.CENTER);

        Container container = this.getContentPane();

        container.add(navbar, BorderLayout.NORTH);
        container.add(DashboardBody, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Dashboard(Login.user);
    }
}