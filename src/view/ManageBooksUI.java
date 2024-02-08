package view;

import controllers.*;
//import model.Genre;
import component.Fonts;
import component.ModernButton;

import java.awt.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ManageBooksUI extends JPanel {
    
    Controller controller;

    JPanel pnlBottom, pnlTop; // MainPanel Top and Bottom
    JTable tblMain;
    JPanel pnlAddBook, pnlEditBook, pnlDiscountBook, pnlManageBooks, pnlCard;
    JComboBox<Object> cbxFilters;
    Object[] objFilters = {"Id", "Title", "Author", "Price"};
    JButton btnSearch, btnAdd, btnEdit, btnDiscount, btnRemove;
    ModernButton refreshBtn  = new ModernButton("Refresh", new Color(200,200,200));
    CardLayout cardLayout;    
    JTextField txtSearch;
    JScrollPane scrPaneTblMain;
    JTabbedPane tabPane;
    ListSelectionModel lstSelectionModel;
    DefaultTableModel tblModelMain;

    Integer tblSelectedRow;

    Object[] selectedBook; // keeps track of the currently selected book from table
    
    // For Editing Book purposes
    JTextField txtTitle = new JTextField();
    JComboBox<Object> cbxAuthor = new JComboBox<Object> ();
    JComboBox<Object> cbxPublisher = new JComboBox<Object> ();
    JFormattedTextField txtPublicationDate = new JFormattedTextField();
    JComboBox<Object> cbxGenre = new JComboBox<Object> ();
    JFormattedTextField txtPrice = new JFormattedTextField();
    JSpinner spnrQuantity = new JSpinner();

    JLabel lblSelectedBookName;
    JLabel lblSelectedBookNameValue;
    JLabel lblPrevPrice;
    JLabel lblPrevPriceVal;
    JLabel lblDiscountedPrice;

    

    static GridBagConstraints gbc = new GridBagConstraints();

    public ManageBooksUI() {
        
        try {
            controller = new Controller();
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        initializeComponents();
        
        populateTable();

        setupMainPanel();
        setupAddBooksPanel();
        setupEditBooksPanel();
        setupDiscountBooksPanel();

        
        scrPaneTblMain.setPreferredSize(new Dimension(750,400));
        tblMain.setRowHeight(25);
        // tblMain.setCellSelectionEnabled(false);
        // tblMain.setRowSelectionAllowed(true);
        
        
    
        add(pnlCard);

        setupActionListening();
    }

    void initializeComponents(){
        txtSearch = new JTextField(20);
        
        btnSearch = new JButton("Search", null);
        btnAdd = new JButton("Add Book", null);
        btnEdit = new JButton("Edit Book", null);
        btnDiscount = new JButton("Discount Book", null);
        btnRemove = new JButton("Remove Book", null);
        
        cardLayout = new CardLayout();
        
        pnlTop = new JPanel(new FlowLayout());
        pnlBottom = new JPanel(new FlowLayout());

        pnlAddBook = new JPanel(new GridBagLayout());        
        pnlEditBook = new JPanel(new GridBagLayout());
        pnlDiscountBook = new JPanel(new GridBagLayout());
        pnlManageBooks = new JPanel(new GridBagLayout());

        pnlCard = new JPanel(cardLayout);

        tblModelMain = new DefaultTableModel(new Object[]{"Id", "Title","Author","Price"}, 0);
        tblMain = new JTable(tblModelMain);

        scrPaneTblMain = new JScrollPane(tblMain);

        cbxFilters = new JComboBox<>(objFilters);
        
    }

    void populateTable(){
        if(tblModelMain.getRowCount() != 0){
            tblModelMain.setRowCount(0);
        }

        Object[][] tableData = Controller.retrieveAllBookObjects();
        //controller.sortById(tableData);
        
        for(Object[] obj : tableData){
            tblModelMain.addRow(obj);
        }
    }

    void setupMainPanel(){

        pnlTop.add(txtSearch);
        pnlTop.add(new JLabel("Filters:"));
        pnlTop.add(cbxFilters);
        refreshBtn.setBounds(0,100, 20, 20);
        pnlTop.add(refreshBtn);

        pnlBottom.add(btnAdd);
        pnlBottom.add(btnEdit);
        pnlBottom.add(btnDiscount);        
        pnlBottom.add(btnRemove);    

        // pnlManageBooks.setPreferredSize(new Dimension(500, 800));
        // scrpnManageBooks = new JScrollPane(pnlManageBooks);
        // pnlAddBook.setPreferredSize(new Dimension(400, 400));
        // scrpnAddBook = new JScrollPane(pnlAddBook);
        // pnlEditBook.setPreferredSize(new Dimension(400, 400));
        // scrpnEditBook = new JScrollPane(pnlEditBook);
        // pnlDiscountBook.setPreferredSize(new Dimension(400, 400));
        // scrpnDiscountBook = new JScrollPane(pnlDiscountBook);

        pnlCard.add(pnlManageBooks, "pnlManageBooks");
        pnlCard.add(pnlAddBook, "pnlAddBook");
        pnlCard.add(pnlEditBook, "pnlEditBook");;
        pnlCard.add(pnlDiscountBook, "pnlDiscountBook");



        JLabel title = new JLabel("Manage Books");
        title.setFont(Fonts.headingFont);
        
        gbc.gridx=0;
        gbc.fill = GridBagConstraints.VERTICAL;

        gbc.insets = new Insets(10, 10, 10, 0);
        pnlManageBooks.add(title, gbc);
        pnlManageBooks.add(pnlTop, gbc);
        gbc.insets = new Insets(10, 10, 10, 0);
        pnlManageBooks.add(scrPaneTblMain, gbc);
        gbc.insets = new Insets(25, 10, 10, 0);
        pnlManageBooks.add(pnlBottom, gbc);
    }

    void setupAddBooksPanel(){
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 0, 100, 0);
        JLabel lblAddBooks = new JLabel("Add Books to Store");
        lblAddBooks.setFont(Fonts.headingFont);
        pnlAddBook.add(lblAddBooks, gbc);
        
        JLabel lblTitle = new JLabel("Title:");
        JLabel lblAuthor = new JLabel("Author:");
        //JLabel lblauthorID = new JLabel("Author Email:");
        JLabel lblPublisher = new JLabel("Publisher:");
        //JLabel lblPublisherEmail = new JLabel("Publisher Email:");
        JLabel lblPublicationDate = new JLabel("Publication Date:");
        JLabel lblGenre = new JLabel("Genre:");
        JLabel lblPrice = new JLabel("Price:");
        JLabel lblQuantity = new JLabel("Stock Quantity:");

        
        JTextField txtTitle = new JTextField(null, 50);
        
        Object[][] publisherIdAndName = controller.getPublisherIdAndName();
        Object[][] authorIdAndName = controller.getAuthorIdAndName();
        
        JComboBox<Object> cbxAuthor = new JComboBox<>(controller.getAuthorNames(authorIdAndName));
        JComboBox<Object> cbxPublisher = new JComboBox<>(controller.getPublisherNames(publisherIdAndName));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JFormattedTextField txtPublicationDate = new JFormattedTextField(dateFormat);
        txtPublicationDate.setText("Year-Month-Day");
        
        JComboBox<Object> cbxGenre = new JComboBox<>(Controller.getGenres());

        NumberFormat numFormatPrice = new DecimalFormat("0.00");
        JFormattedTextField txtPrice = new JFormattedTextField(numFormatPrice);
        txtPrice.setColumns(50);

        SpinnerNumberModel spnrModel = new SpinnerNumberModel(5,0,9999,1);
        JSpinner spnrQuantity = new JSpinner(spnrModel);

        lblAddBooks.setHorizontalAlignment(SwingConstants.CENTER);

        // DONT CHANGE THE ORDER
        JComponent components[] = {
            lblTitle,
            txtTitle,
            lblAuthor,
            cbxAuthor,
            lblPublisher,
            cbxPublisher,
            lblPublicationDate,
            txtPublicationDate,
            lblGenre,
            cbxGenre,
            lblPrice,
            txtPrice,
            lblQuantity,
            spnrQuantity
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
            pnlAddBook.add(pnlInner,gbc);
            
        }

        JButton btnBackAddBook = new JButton("Back");
        JButton btnAddBook = new JButton("Add");
        JButton btnClearFields = new JButton("Clear");
        JPanel pnlBttmButtons = new JPanel(new FlowLayout());
        pnlBttmButtons.add(btnBackAddBook);
        pnlBttmButtons.add(btnAddBook);
        pnlBttmButtons.add(btnClearFields);
        
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.insets = new Insets(100, 0, 0, 0);
        pnlAddBook.add(pnlBttmButtons, gbc);
        btnBackAddBook.addActionListener(e->{
            cardLayout.show(pnlCard, "pnlManageBooks");
        });

        btnAddBook.addActionListener(e -> {

            int authIndex = cbxAuthor.getSelectedIndex();
            String authorId = Integer.toString((int)authorIdAndName[authIndex][0]);

            int pubIndex = cbxPublisher.getSelectedIndex();
            String pubId = Integer.toString((int)authorIdAndName[pubIndex][0]);

            
            String title = txtTitle.getText()
            ,author = (String)cbxAuthor.getSelectedItem()
            ,price = txtPrice.getText()
            ,pubName = (String)cbxPublisher.getSelectedItem()
            ,genre = Controller.getName((model.Genre)cbxGenre.getSelectedItem())
            ,quantity = Integer.toString((Integer)spnrQuantity.getValue())
            ,pubDate = txtPublicationDate.getText();
            
            // Validate Input Errors
            String[] fields = {title, author, authorId, price, pubName, pubId, genre, quantity, pubDate};
            for(String s : fields){
                if(s.isEmpty()){
                    JOptionPane.showMessageDialog(new JOptionPane(),s + " field is empty!", "Invalid Field", JOptionPane.OK_OPTION);
                    return;
                }
            }

            JOptionPane.showConfirmDialog(null, "A book with the following fields will be added to the database. Are you sure?", "Confirm Book", JOptionPane.YES_NO_OPTION);
            btnClearFields.doClick(1);

            // Add to Database
            try {
                controller.addBook(title, price, authorId, pubId, LocalDate.parse(pubDate).toString(), genre, quantity);
            } catch (SQLException e1) {
                System.out.println("something went wrong and couldn't add the book ");
                e1.printStackTrace();
            }
            populateTable();
        });

        btnClearFields.addActionListener(e -> {
            for(JComponent c : components){
                if(c instanceof JTextField){
                    ((JTextField)c).setText("");
                }
            }
        });

        refreshBtn.addActionListener(e->{
            populateTable();
        }
        );
                       
    }

    void setupEditBooksPanel(){
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 0, 100, 0);
        JLabel lblEditBooks = new JLabel("Edit Book Information");
        lblEditBooks.setFont(Fonts.headingFont);
        pnlEditBook.add(lblEditBooks, gbc);
        
        JLabel lblTitle = new JLabel("Title:");
        JLabel lblAuthor = new JLabel("Author:");
        JLabel lblPublisher = new JLabel("Publisher:");
        JLabel lblPublicationDate = new JLabel("Publication Date:");
        JLabel lblGenre = new JLabel("Genre:");
        JLabel lblPrice = new JLabel("Price:");
        JLabel lblQuantity = new JLabel("Stock Quantity:");

        
        txtTitle = new JTextField(null, 50);
        
        Object[][] publisherIdAndName = controller.getPublisherIdAndName();
        Object[][] authorIdAndName = controller.getAuthorIdAndName();
        
        cbxAuthor = new JComboBox<>(controller.getAuthorNames(authorIdAndName));
        cbxPublisher = new JComboBox<>(controller.getPublisherNames(publisherIdAndName));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        txtPublicationDate = new JFormattedTextField(dateFormat);
        txtPublicationDate.setText("Year-Month-Day");
        
        cbxGenre = new JComboBox<>(Controller.getGenres());

        NumberFormat numFormatPrice = new DecimalFormat("0.00");
        txtPrice = new JFormattedTextField(numFormatPrice);
        txtPrice.setColumns(50);

        SpinnerNumberModel spnrModel = new SpinnerNumberModel(5,0,9999,1);
        spnrQuantity = new JSpinner(spnrModel);

        lblEditBooks.setHorizontalAlignment(SwingConstants.CENTER);

        // DONT CHANGE THE ORDER
        JComponent editComponents[] = {
            lblTitle,
            txtTitle,
            lblAuthor,
            cbxAuthor,
            lblPublisher,
            cbxPublisher,
            lblPublicationDate,
            txtPublicationDate,
            lblGenre,
            cbxGenre,
            lblPrice,
            txtPrice,
            lblQuantity,
            spnrQuantity
        };
        
        
        JPanel pnlInner, pnlInnerPrev;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;
        // add components
        for(int i = 0; i < editComponents.length; i+=2){

            pnlInnerPrev = new JPanel();
            pnlInner = new JPanel();
            pnlInner.setLayout(new BoxLayout(pnlInner,BoxLayout.X_AXIS));
            
            if(editComponents[i] instanceof JLabel) editComponents[i].setFont(Fonts.headingFont1B);
            if(editComponents[i+1] instanceof JLabel) editComponents[i+1] .setFont(Fonts.headingFont1);
            //if(editComponents[i+2] instanceof JLabel) editComponents[i+2] .setFont(Fonts.headingFont1);

            
            //pnlInnerPrev.add(editComponents[i]);
            pnlInner.add(Box.createRigidArea(new Dimension(50,5)));
            pnlInner.add(editComponents[i]);
            pnlInner.add(Box.createRigidArea(new Dimension(50,5)));
            pnlInner.add(editComponents[i+1]);
            
            
            pnlEditBook.add(pnlInnerPrev,gbc);
            gbc.insets = new Insets(5,0,0,0);
            gbc.gridy += 1;
            pnlEditBook.add(pnlInner,gbc);
            
        }

        JButton btnBackEditBook = new JButton("Back");
        JButton btnEditBook = new JButton("Apply Edit");
        JButton btnClearFields = new JButton("Clear");
        JPanel pnlBttmButtons = new JPanel(new FlowLayout());
        pnlBttmButtons.add(btnBackEditBook);
        pnlBttmButtons.add(btnEditBook);
        pnlBttmButtons.add(btnClearFields);
        
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.insets = new Insets(100, 0, 0, 0);
        pnlEditBook.add(pnlBttmButtons, gbc);
        btnBackEditBook.addActionListener(e->{
            cardLayout.show(pnlCard, "pnlManageBooks");
        });

        btnEditBook.addActionListener(e -> {

            int authIndex = cbxAuthor.getSelectedIndex();
            String authorId = Integer.toString((int)authorIdAndName[authIndex][0]);

            int pubIndex = cbxPublisher.getSelectedIndex();
            String pubId = Integer.toString((int)authorIdAndName[pubIndex][0]);
            
            String title = txtTitle.getText()
            ,author = (String)cbxAuthor.getSelectedItem()
            ,price = txtPrice.getText()
            ,pubName = (String)cbxPublisher.getSelectedItem()
            ,genre = Controller.getName((model.Genre)cbxGenre.getSelectedItem())
            ,quantity = Integer.toString((Integer)spnrQuantity.getValue())
            ,pubDate = txtPublicationDate.getText();

            // Validate Input Errors
            String[] fields = {title, author, authorId, price, pubName, pubId, genre, quantity, pubDate};
            for(String s : fields){
                if(s.isEmpty()){
                    JOptionPane.showMessageDialog(new JOptionPane(),s + " field is empty!", "Invalid Field", JOptionPane.OK_OPTION);
                    return;
                }
            }

            JOptionPane.showConfirmDialog(null, "A book with the following fields will be edited from the database. Are you sure?", "Confirm Edit", JOptionPane.YES_NO_OPTION);

            int i = tblMain.getSelectedRow();
            String bid = Integer.toString((int)tblMain.getValueAt(i,0));

            try {
                Controller.editBook(title, price, authorId,  pubId,  pubDate,  genre, quantity, bid);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            populateTable(); // refreshes the table
        });

        btnClearFields.addActionListener(e -> {
            for(JComponent c : editComponents){
                if(c instanceof JTextField){
                    ((JTextField)c).setText("");
                }
            }
        });
    }
    
    void setupDiscountBooksPanel(){

        GridBagConstraints gbc = new GridBagConstraints();
        JLabel lblDiscountBooks = new JLabel("Discount Book");
        lblDiscountBooks.setFont(Fonts.headingFontB); //* Separate it into controller class (the font)**/
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 100, 0);
        pnlDiscountBook.add(lblDiscountBooks, gbc);


        

        lblSelectedBookName = new JLabel("Book Name");
        lblSelectedBookNameValue = new JLabel("SELECteD NAMe");
        lblDiscountedPrice = new JLabel("Discounted Price");
       

        SpinnerNumberModel spnrNumberModel = new SpinnerNumberModel(0, 0, 100, 0.33); //**  Must call getPrice(BookId) from the controller **/
        JSpinner spnrDiscountedPrice = new JSpinner(spnrNumberModel);


        JComponent components[] = {
            lblSelectedBookName, lblSelectedBookNameValue, lblDiscountedPrice, spnrDiscountedPrice
        };

        btnDiscount.addActionListener(e -> {
            String bn= (String)tblMain.getValueAt(tblMain.getSelectedRow(), 1);
            Double bp= (Double)tblMain.getValueAt(tblMain.getSelectedRow(), 3);
            lblSelectedBookNameValue.setText(bn);
            spnrDiscountedPrice.setValue(bp);
        });


        JPanel pnlInner;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        for(int i = 0; i < components.length; i+=2){
            
            gbc.gridx = 0;
            gbc.gridy += 1;
            gbc.insets = new Insets(5, 0, 0, 0);

            pnlInner = new JPanel();
            pnlInner.setLayout(new BoxLayout(pnlInner, BoxLayout.X_AXIS));

            if(components[i] instanceof JLabel) components[i].setFont(Fonts.headingFont1B);
            if(components[i+1] instanceof JLabel) components[i+1].setFont(Fonts.headingFont1);

            pnlInner.add(components[i]);
            pnlInner.add(Box.createRigidArea(new Dimension(50, 10)));
            pnlInner.add(components[i+1]);

            
            pnlDiscountBook.add(pnlInner, gbc);
        }

        JButton btnDiscount = new JButton("Apply Discount");
        JButton btnBackDiscountBook = new JButton("Back");
        JPanel pnlBttmButtons = new JPanel(new FlowLayout());

        pnlBttmButtons.add(btnBackDiscountBook);
        pnlBttmButtons.add(btnDiscount);

        gbc.gridy += 1;
        gbc.insets = new Insets(100, 0, 0, 0);
        pnlDiscountBook.add(pnlBttmButtons, gbc);

        btnBackDiscountBook.addActionListener(e->{
            cardLayout.show(pnlCard, "pnlManageBooks");
        });

        btnDiscount.addActionListener(e -> {
            Integer bid = (Integer)tblMain.getValueAt(tblMain.getSelectedRow(),0);
            Double basePrice = (Double)tblMain.getValueAt(tblMain.getSelectedRow(), 3);
            try {
                Controller.discountBook(bid, basePrice);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            populateTable(); // refreshes the table
        });
    }

    void setupActionListening(){
        btnAdd.addActionListener(e -> {
            cardLayout.show(pnlCard, "pnlAddBook");
        });

        btnEdit.addActionListener(e -> {
            System.out.println("EDIT PRESSED");
            //JOptionPane.showMessageDialog("mu", e, TOOL_TIP_TEXT_KEY, ABORT, null);
            if(tblMain.getSelectedRow() != -1){
                System.out.println((int)tblMain.getValueAt(tblMain.getSelectedRow(), 0));
                selectedBook = Controller.retrieveEditBookInformation((int)tblMain.getValueAt(tblMain.getSelectedRow(), 0));
                if(selectedBook != null){
                    // for(Object o : selectedBook){
                    //     System.out.println(o);
                    // }
                    System.out.println("SELECTED ROW BEING DISPLAYED..");
                    txtTitle.setText((String)selectedBook[0]);
                    cbxAuthor.setSelectedItem(String.valueOf(selectedBook[1]));
                    cbxPublisher.setSelectedItem(String.valueOf(selectedBook[2]));
                    txtPublicationDate.setText(String.valueOf(selectedBook[3]));
                    cbxGenre.setSelectedItem(String.valueOf(selectedBook[4]));
                    txtPrice.setText(String.valueOf(selectedBook[5]));
                    spnrQuantity.setValue((int)selectedBook[6]);  
                    pnlEditBook.repaint();
                }
            }
            cardLayout.show(pnlCard, "pnlEditBook");
        });

        btnDiscount.addActionListener(e -> {
            cardLayout.show(pnlCard, "pnlDiscountBook");
        });
        
        btnRemove.addActionListener(e ->{
            int i = tblMain.getSelectedRow();

            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove \n\n\"" + 
            tblMain.getValueAt(i, 1) + "\" \n\nbook from the database (Cannot be Undone)?", "Remove Confirmation", JOptionPane.YES_NO_OPTION);

            if(tblMain.getValueAt(i, 0) instanceof Integer){
                Integer id = (Integer)tblMain.getValueAt(i, 0);   
                try {
                    controller.removeBook(id);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }             
            }
            populateTable(); // refreshes the table
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

