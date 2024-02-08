package view;

import controllers.*;
import component.Fonts;

import java.awt.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ManageEmployeesUI extends JPanel{
    JTable tblMain;
    JTabbedPane tabPane;
    JTextField txtSearch;
    CardLayout cardLayout;    
    JPanel pnlBottom, pnlTop; // MainPanel Top and Bottom
    JScrollPane scrPaneTblMain;
    ListSelectionModel lstSelectionModel;

    Object[] columns = {"Id", "Name","Gender","Job Title", "Salary"};
    JComboBox<Object> cbxFilters = new JComboBox<>(columns);

    JButton btnSearch, btnAdd, btnEdit, btnRemove;
    JPanel pnlAddEmployee, pnlEditEmployee, pnlMainPanel, pnlCard;

    DefaultTableModel tblModelMain = new DefaultTableModel(columns, 0);;

    // Used for Edit Employee Information
    JTextField txtName;
    JTextField txtBirthDate;
    JComboBox<Object> cbxGender;
    JTextField txtAddress;
    JTextField txtPhoneNumber;
    JTextField txtEmail;
    JPasswordField txtPassword;
    JSpinner spnrSalary;
    JComboBox<Object> cbxJobTitle;

    Object[] selectedEmployee = null;

    static GridBagConstraints gbc = new GridBagConstraints();
    
    Controller controller;

    ManageEmployeesUI(){

        
        try {
            controller = new Controller();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeComponents();
        
        populateTable();

        setupMainPanel();
        setupAddEmployeePanel();
        setupEditEmployeePanel();

        add(pnlCard);

        setupActionListening();

    }
    
    private void initializeComponents() {
        txtSearch = new JTextField(20);
        
        btnSearch = new JButton("Search", null);
        btnAdd = new JButton("Add Employee", null);
        btnEdit = new JButton("Edit Employee", null);
        btnRemove = new JButton("Remove Employee", null);

        cardLayout = new CardLayout();
        
        pnlTop = new JPanel(new FlowLayout());
        pnlBottom = new JPanel(new FlowLayout());

        pnlMainPanel = new JPanel(new GridBagLayout());
        pnlAddEmployee = new JPanel(new GridBagLayout());        
        pnlEditEmployee = new JPanel(new GridBagLayout());

        pnlCard = new JPanel(cardLayout);

        tblMain = new JTable(tblModelMain);
        scrPaneTblMain = new JScrollPane(tblMain);
    }

    void populateTable(){
        if(tblModelMain.getRowCount() != 0){
            tblModelMain.setRowCount(0);
        }

        Object[][] tableData = Controller.retrieveEmployeeTableData();
        Controller.sortById(tableData);
        
        for(Object[] obj : tableData){
            tblModelMain.addRow(obj);
        }
    }

    private void setupMainPanel(){
        pnlTop.add(txtSearch);
        pnlTop.add(new JLabel("Filters:"));
        pnlTop.add(cbxFilters);

        pnlBottom.add(btnAdd);
        pnlBottom.add(btnEdit);        
        pnlBottom.add(btnRemove);    

        pnlCard.add(pnlMainPanel, "pnlMainPanel");
        pnlCard.add(pnlAddEmployee, "pnlAddBook");
        pnlCard.add(pnlEditEmployee, "pnlEditBook");;

        JLabel title = new JLabel("Manage Employees");
        title.setFont(Fonts.headingFont);
        
        gbc.gridx=0;
        gbc.fill = GridBagConstraints.VERTICAL;

        gbc.insets = new Insets(10, 10, 10, 0);
        pnlMainPanel.add(title, gbc);
        pnlMainPanel.add(pnlTop, gbc);
        gbc.insets = new Insets(10, 10, 10, 0);
        pnlMainPanel.add(scrPaneTblMain, gbc);
        gbc.insets = new Insets(25, 10, 10, 0);
        pnlMainPanel.add(pnlBottom, gbc);
    }

    private void setupAddEmployeePanel(){
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 0, 100, 0);
        JLabel lblAddEmployees = new JLabel("Add Employees");
        lblAddEmployees.setFont(Fonts.headingFont);
        pnlAddEmployee.add(lblAddEmployees, gbc);
        
        JLabel lblName = new JLabel("Name:");
        JLabel lblBirthDate = new JLabel("BirthDate:");
        JLabel lblGender = new JLabel("Gender:");
        JLabel lblAddress = new JLabel("Address:");
        JLabel lblPhoneNumber = new JLabel("PhoneNumber:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblPassword = new JLabel("Password:");
        JLabel lblSalary = new JLabel("Salary:");
        JLabel lblJobTitle = new JLabel("JobTitle:");

        JTextField txtName = new JTextField(null, 50);
        JTextField txtBirthDate = new JTextField("BirthDate:");
        JComboBox<Object> cbxGender = new JComboBox<>(Controller.getGenders());
        JTextField txtAddress = new JTextField(null, 50);
        JTextField txtPhoneNumber = new JTextField(null, 50);
        JTextField txtEmail = new JTextField(null, 50);
        JPasswordField txtPassword = new JPasswordField(null, 50);
        JSpinner spnrSalary = new JSpinner(new SpinnerNumberModel(5000, 1500, 900000, 500));
        JComboBox<Object> cbxJobTitle = new JComboBox<>(Controller.getJobTitle());

    
        lblAddEmployees.setHorizontalAlignment(SwingConstants.CENTER);

        
        JComponent components[] = {
            lblName,
            txtName,
            lblBirthDate,
            txtBirthDate,
            lblGender,
            cbxGender,
            lblAddress,
            txtAddress,
            lblPhoneNumber,
            txtPhoneNumber,
            lblEmail,
            txtEmail,
            lblPassword,
            txtPassword,
            lblSalary,
            spnrSalary,
            lblJobTitle,
            cbxJobTitle
        };
        
        
        JPanel pnlInner;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for(int i = 0; i < components.length; i+=2){
            pnlInner = new JPanel();
            pnlInner.setLayout(new BoxLayout(pnlInner,BoxLayout.X_AXIS));
            
            if(components[i] instanceof JLabel) components[i].setFont(Fonts.headingFont1B);
            if(components[i+1] instanceof JLabel) components[i+1] .setFont(Fonts.headingFont1);

            
            pnlInner.add(components[i]);
            pnlInner.add(Box.createRigidArea(new Dimension(50,5)));
            pnlInner.add(components[i+1]);
            

            gbc.insets = new Insets(5,2,5,2);
            gbc.gridx = 0;  
            gbc.gridy += 1;
            pnlAddEmployee.add(pnlInner,gbc);
            
        }

        JButton btnBackAddEmployee = new JButton("Back");
        JButton btnAddBook = new JButton("Add");
        JPanel pnlBttmButtons = new JPanel(new FlowLayout());
        pnlBttmButtons.add(btnBackAddEmployee);
        pnlBttmButtons.add(btnAddBook);
        
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.insets = new Insets(100, 0, 0, 0);
        pnlAddEmployee.add(pnlBttmButtons, gbc);
        
        btnBackAddEmployee.addActionListener(e->{
            cardLayout.show(pnlCard, "pnlMainPanel");
        });

        btnAddBook.addActionListener(e -> {
            // TODO
        });

    }

    private void setupEditEmployeePanel(){
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 0, 100, 0);
        JLabel lblEditEmployees = new JLabel("Add Employees");
        lblEditEmployees.setFont(Fonts.headingFont);
        pnlEditEmployee.add(lblEditEmployees, gbc);

        JLabel lblName = new JLabel("Name:");
        JLabel lblBirthDate = new JLabel("BirthDate:");
        JLabel lblGender = new JLabel("Gender:");
        JLabel lblAddress = new JLabel("Address:");
        JLabel lblPhoneNumber = new JLabel("PhoneNumber:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblPassword = new JLabel("Password:");
        JLabel lblSalary = new JLabel("Salary:");
        JLabel lblJobTitle = new JLabel("JobTitle:");

        txtName = new JTextField(null, 50);
        txtBirthDate = new JTextField(null, 50);
        cbxGender = new JComboBox<>(Controller.getGenders());
        txtAddress = new JTextField(null, 50);
        txtPhoneNumber = new JTextField(null, 50);
        txtEmail = new JTextField(null, 50);
        txtPassword = new JPasswordField(null, 50);
        spnrSalary = new JSpinner(new SpinnerNumberModel(5000, 1500, 900000, 500));
        cbxJobTitle = new JComboBox<>(Controller.getJobTitle());

    
        lblEditEmployees.setHorizontalAlignment(SwingConstants.CENTER);

        JComponent components[] = { 
            lblName,
            txtName,  
            lblBirthDate,
            txtBirthDate,
            lblGender,
            cbxGender,
            lblAddress,
            txtAddress,
            lblPhoneNumber,
            txtPhoneNumber,
            lblEmail,
            txtEmail, 
            lblPassword,
            txtPassword,
            lblSalary,
            spnrSalary,
            lblJobTitle,
            cbxJobTitle
        };

        JPanel pnlInner;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for(int i = 0; i < components.length; i+=2){
            pnlInner = new JPanel();
            pnlInner.setLayout(new BoxLayout(pnlInner,BoxLayout.X_AXIS));
            
            if(components[i] instanceof JLabel) components[i].setFont(Fonts.headingFont1B);
            if(components[i+1] instanceof JLabel) components[i+1] .setFont(Fonts.headingFont1);
            
            pnlInner.add(components[i]);
            pnlInner.add(Box.createRigidArea(new Dimension(50,5)));
            pnlInner.add(components[i+1]);

            gbc.insets = new Insets(5,2,5,2);
            gbc.gridx = 0;  
            gbc.gridy += 1;
            pnlEditEmployee.add(pnlInner,gbc);
            
        }

        JButton btnBackEditEmployee = new JButton("Back");
        JButton btnEditBook = new JButton("Edit");
        JPanel pnlBttmButtons = new JPanel(new FlowLayout());
        pnlBttmButtons.add(btnBackEditEmployee);
        pnlBttmButtons.add(btnEditBook);
        
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.insets = new Insets(100, 0, 0, 0);
        pnlEditEmployee.add(pnlBttmButtons, gbc);
        
        btnBackEditEmployee.addActionListener(e->{
            cardLayout.show(pnlCard, "pnlMainPanel");
        });

        btnEditBook.addActionListener(e -> {
            // TODO
        });
    }

    private void setupActionListening(){
        btnAdd.addActionListener(e -> {
            cardLayout.show(pnlCard, "pnlAddBook");
        });

        btnEdit.addActionListener(e -> {
            cardLayout.show(pnlCard, "pnlEditBook");
        });

        btnRemove.addActionListener(e ->{
            int i = tblMain.getSelectedRow();

            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove \n\n\"" + 
            tblMain.getValueAt(i, 1) + "\" \n\nEmployee from the database (Cannot be Undone)?", "Remove Confirmation", JOptionPane.YES_NO_OPTION);

            if(i != -1 && choice == JOptionPane.YES_OPTION){
                tblModelMain.removeRow(i);
            }
            
            if(tblMain.getValueAt(i, 0) instanceof Integer){
                Integer id = (Integer)tblMain.getValueAt(i, 0);   
                try {
                    controller.removeEmployee(id);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }             
            }
        });

        ListSelectionModel lstModel = tblMain.getSelectionModel();
        lstModel.addListSelectionListener(e ->{
            if(tblMain.getSelectedRow() != -1){
                selectedEmployee = Controller.retrieveEditEmployeeInformation((int)tblMain.getValueAt(tblMain.getSelectedRow(), 0));
                if(selectedEmployee != null){
                    // for(Object o :  selectedEmployee){
                    //     System.out.println(o);
                    // }
                    txtName.setText(String.valueOf( selectedEmployee[0]));
                    txtBirthDate.setText(String.valueOf( selectedEmployee[1]));
                    cbxGender.setSelectedItem(String.valueOf( selectedEmployee[2]));
                    txtAddress.setText(String.valueOf( selectedEmployee[3]));
                    txtPhoneNumber.setText(String.valueOf( selectedEmployee[4]));
                    txtEmail.setText(String.valueOf( selectedEmployee[5]));
                    txtPassword.setText(String.valueOf( selectedEmployee[6]));
                    spnrSalary.setValue((double)selectedEmployee[7]);
                    cbxJobTitle.setSelectedItem(selectedEmployee[8]);  
                    pnlEditEmployee.repaint();
                }
            }
        });

        txtSearch.getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent e) {
                tableFilter(cbxFilters.getSelectedIndex());
            }
            public void removeUpdate(DocumentEvent e) {
                tableFilter(cbxFilters.getSelectedIndex());
            }
            public void changedUpdate(DocumentEvent e) {
                tableFilter(cbxFilters.getSelectedIndex());
            }
            
        });
        
    }

    void tableFilter(int i){
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tblModelMain);
        
        if(txtSearch.getText().isEmpty()){
            tblMain.setRowSorter(null);
            sorter.setRowFilter(null);
            populateTable();
        }
        else{
            tblMain.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter(txtSearch.getText(), i));
        }
    }

    
}
