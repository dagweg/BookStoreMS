package view;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import controllers.Controller;

public class AuthorAndPublisherUI extends JPanel{
    
    JPanel pnlAuthor = new JPanel(new GridBagLayout());
    JPanel pnlPublisher = new JPanel(new GridBagLayout());
    JPanel pnlAuthAndPub =new JPanel();
    JPanel pnlTable = new JPanel();

    DefaultTableModel tblAuthorModel = new DefaultTableModel();
    DefaultTableModel tblPublisherModel = new DefaultTableModel();

    JTable tblAuthor = new JTable(tblAuthorModel);
    JTable tblPublisher = new JTable(tblPublisherModel);

    JScrollPane scrpAuthor = new JScrollPane(tblAuthor);
    JScrollPane scrpPublisher = new JScrollPane(tblPublisher);

    Object[] colsAuthor = {"Name", "SSN", "Age", "Gender", "Address", "Phone", "Email", "DateOfBirth"};
    Object[] colsPublisher = {"PublisherID", "Name", "Email"};

    Integer rowHeight =25;

    GridBagConstraints gbc = new GridBagConstraints();

    Controller controller;

    AuthorAndPublisherUI(){

        try {
            controller = new Controller();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setupAuthorPanel();
        setupPublisherPanel();
        setupTables();
        populateTables();

        pnlAuthAndPub.setLayout(new BoxLayout(pnlAuthAndPub, BoxLayout.Y_AXIS));
        pnlAuthAndPub.add(pnlAuthor);
        pnlAuthAndPub.add(pnlPublisher);
        
        add(pnlAuthAndPub);
        add(Box.createRigidArea(new Dimension(25,0)));
        add(pnlTable);
        

    }

    void setupAuthorPanel(){
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 0,0,0);
        JLabel lblTitle = new JLabel("Author Information");
        pnlAuthor.add(lblTitle,gbc);

        JLabel lblName = new JLabel("Name");
        JLabel lblSSN = new JLabel("SSN");
        JLabel lblAge = new JLabel("Age");
        JLabel lblGender = new JLabel("Gender");
        JLabel lblAddress = new JLabel("Address");
        JLabel lblPhone = new JLabel("Phone");
        JLabel lblEmail = new JLabel("Email");
        JLabel lblDateOfBirth = new JLabel("DateOfBirth");

        JTextField txtName = new JTextField(null, 10);
        JTextField txtSSN = new JTextField(null, 10);

        NumberFormat nf = NumberFormat.getIntegerInstance();
        JFormattedTextField txtAge = new JFormattedTextField(nf);

        JComboBox<Object> cbxGender = new JComboBox<>(Controller.getGender());

        JTextField txtAddress = new JTextField(null, 10);
        JTextField txtPhone = new JTextField(null, 10);
        JTextField txtEmail = new JTextField(null, 10);
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        JFormattedTextField txtDateOfBirth = new JFormattedTextField(df);
        txtDateOfBirth.setText("Year-Month-Day");
        txtDateOfBirth.setColumns(10);

        JButton btnAddAuthor = new JButton("Add");
        JButton btnRemoveAuthor = new JButton("Remove");

        JComponent components[] = {
            lblName,
            txtName,   
            lblSSN,
            txtSSN,   
            lblAge,
            txtAge,   
            lblGender,
            cbxGender,   
            lblAddress,
            txtAddress,   
            lblPhone,
            txtPhone,   
            lblEmail,
            txtEmail,   
            lblDateOfBirth,
            txtDateOfBirth
        };

        JPanel pnlInnerComp = new JPanel();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        for(int i = 0; i < components.length; i+=2){
            pnlInnerComp.setLayout(new BoxLayout(pnlInnerComp, BoxLayout.X_AXIS));
            pnlInnerComp = new JPanel();

            gbc.gridy += 1;
            pnlInnerComp.add(components[i]);
            pnlInnerComp.add(Box.createRigidArea(new Dimension(10,0)));
            pnlInnerComp.add(components[i+1]);

            //gbc.gridx += 1;
            gbc.insets = new Insets(5, 2, 5, 2);
            pnlAuthor.add(pnlInnerComp, gbc);
        }

        
        JPanel pnlButtons = new JPanel();
        pnlButtons.add(btnAddAuthor);
        pnlButtons.add(btnRemoveAuthor);
        gbc.gridy += 1;
        pnlAuthor.add(pnlButtons,gbc);


        btnAddAuthor.addActionListener(e -> {
            String 
            Name = txtName.getText(),
            SSN = txtName.getText(),
            Age = txtName.getText(),
            Gender = txtName.getText(),
            Address = txtName.getText(),
            Phone = txtName.getText(),
            Email = txtName.getText(),
            DateOfBirth = txtName.getText();

            String[] fields = {Name,SSN,Age,Gender,Address,Phone,Email,DateOfBirth};
            
            for(String s : fields){
                if(s.isEmpty()){
                    JOptionPane.showMessageDialog(new JOptionPane(),s + " field is empty!", "Invalid Author Field", JOptionPane.OK_OPTION);
                    return;
                }
            }
            
            try {
                controller.addAuthor(Name,SSN, Age, Gender, Address, Phone, Email, DateOfBirth);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("ADD AUTHOR UNSUCCESSFUL");
            }
            populateTables();
        }); 

        btnRemoveAuthor.addActionListener(e ->{
            int i = tblAuthor.getSelectedRow();

            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove \n\n\"" + 
            tblAuthor.getValueAt(i, 1) + "\" \n\nbook from the database (Cannot be Undone)?", "Remove Confirmation", JOptionPane.YES_NO_OPTION);

            if(tblAuthor.getValueAt(i, 0) instanceof Integer){
                Integer id = (Integer)tblAuthor.getValueAt(i, 0);   
                try {
                    controller.removeBook(id);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }             
            }
            populateTables(); // refreshes the table
        });
    }

    void setupPublisherPanel(){
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 0,0,0);
        JLabel lblTitle = new JLabel("Publisher Information");
        pnlPublisher.add(lblTitle,gbc);

        JLabel lblName = new JLabel("Name");
        JLabel lblEmail = new JLabel("Email");

        JTextField txtName = new JTextField(null, 10);
        JTextField txtEmail = new JTextField(null, 10);

        JButton btnAddPublisher = new JButton("Add");
        JButton btnRemovePublisher = new JButton("Remove");

        JComponent components[] = {
            lblName,
            txtName,      
            lblEmail,
            txtEmail
        };

        JPanel pnlInnerComp = new JPanel();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        for(int i = 0; i < components.length; i+=2){
            pnlInnerComp.setLayout(new BoxLayout(pnlInnerComp, BoxLayout.X_AXIS));
            pnlInnerComp = new JPanel();

            gbc.gridy += 1;
            pnlInnerComp.add(components[i]);
            pnlInnerComp.add(Box.createRigidArea(new Dimension(10,0)));
            pnlInnerComp.add(components[i+1]);

            //gbc.gridx += 1;
            gbc.insets = new Insets(5, 2, 5, 2);
            pnlPublisher.add(pnlInnerComp, gbc);
        }

        
        JPanel pnlButtons = new JPanel();
        pnlButtons.add(btnAddPublisher);
        pnlButtons.add(btnRemovePublisher);
        gbc.gridy += 1;
        pnlPublisher.add(pnlButtons, gbc);


        btnAddPublisher.addActionListener(e -> {
            String 
            Name = txtName.getText(),
            Email = txtName.getText();

            String[] fields = {Name, Email};
            
            for(String s : fields){
                if(s.isEmpty()){
                    JOptionPane.showMessageDialog(new JOptionPane(),s + " field is empty!", "Invalid Author Field", JOptionPane.OK_OPTION);
                    return;
                }
            }
            
            try {
                controller.addPublisher(Name, Email);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("ADD AUTHOR UNSUCCESSFUL");
            }
            populateTables();
        }); 

        btnRemovePublisher.addActionListener(e ->{
            int i = tblPublisher.getSelectedRow();

            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove \n\n\"" + 
            tblPublisher.getValueAt(i, 1) + "\" \n\nbook from the database (Cannot be Undone)?", "Remove Confirmation", JOptionPane.YES_NO_OPTION);

            if(tblPublisher.getValueAt(i, 0) instanceof Integer){
                Integer id = (Integer)tblPublisher.getValueAt(i, 0);   
                try {
                    controller.removeBook(id);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }             
            }
            populateTables(); // refreshes the table
        });
    
    }

    void setupTables(){
        JLabel lblAuthorTable = new JLabel("Author Table");
        JLabel lblPublisherTable = new JLabel("Publisher Table");
        lblAuthorTable.setHorizontalTextPosition(JLabel.CENTER);
        lblPublisherTable.setHorizontalTextPosition(JLabel.CENTER);

        tblAuthorModel.setColumnIdentifiers(colsAuthor);
        tblPublisherModel.setColumnIdentifiers(colsPublisher);

        tblAuthor.setRowHeight(rowHeight);
        tblPublisher.setRowHeight(rowHeight);

        scrpAuthor.setPreferredSize(new Dimension(600,250));
        scrpPublisher.setPreferredSize(new Dimension(600,250));
        

        pnlTable.setLayout(new BoxLayout(pnlTable, BoxLayout.Y_AXIS));
        pnlTable.add(lblAuthorTable);
        pnlTable.add(scrpAuthor);
        pnlTable.add(Box.createRigidArea(new Dimension(tblAuthor.getWidth(), 40)));
        pnlTable.add(lblPublisherTable);
        pnlTable.add(scrpPublisher);

    }

    // void populateTable(){
    //     if(tblModelMain.getRowCount() != 0){
    //         tblModelMain.setRowCount(0);
    //     }

    //     Object[][] tableData = Controller.retrieveAllBookObjects();
    //     //controller.sortById(tableData);
        
    //     for(Object[] obj : tableData){
    //         tblModelMain.addRow(obj);
    //     }
    // }

    void populateTables(){
        if(tblAuthorModel.getRowCount() != 0){
            tblAuthorModel.setRowCount(0);
        }
        if(tblPublisherModel.getRowCount() != 0){
            tblPublisherModel.setRowCount(0);
        }

        Object[][] authorTable = controller.getAuthorTable();
        Object[][] publisherTable = controller.getPublisherTable();
        for(Object[] obj : authorTable){
            tblAuthorModel.addRow(obj);
            System.out.println(obj);
        }
        for(Object[] obj : publisherTable){
            tblPublisherModel.addRow(obj);
        }
    }

    
}
