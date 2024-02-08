package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import controllers.Controller;

public class BookReportUI extends JPanel {
    JLabel word;
    JPanel panel;
    JButton btnBack;
    JTable tblMain;
    JTextField txtSearch;
    JLabel lblTitle, lblSearch, lblFilter;

    
    private DefaultTableModel model;

    Controller controller;

    String[] columnNames = { "Id", "Title", "Profit from Sale", "Author", "Quantity Sold"};
    Object[][] data = Controller.retrieveAllBookObjects();
    
    JComboBox<String> cbxFilter = new JComboBox<>(columnNames);

    public BookReportUI() {
        
        lblTitle = new JLabel("BOOK SALES REPORT ");
        Font font = new Font("italic", Font.BOLD, 20);
        lblTitle.setFont(font);
        panel = new JPanel(new GridBagLayout(), true);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setBackground(Color.WHITE);

        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel3.add(lblTitle);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(panel3, constraints);
        add(panel);
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblSearch = new JLabel("Search : ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        txtSearch = new JTextField(null, 20);
        panel2.add(lblSearch);
        panel2.add(txtSearch);
        panel2.add(cbxFilter);
        panel.add(panel2, constraints);
        lblFilter = new JLabel("lblFilter");

        constraints.gridx = 0;
        constraints.gridy = 3;

        
        JPanel Panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));

      
        Panel1.add(lblFilter);
        Panel1.add(lblTitle);
        panel.add(Panel1, constraints);

        JPanel panel4 = new JPanel();
        
        model = new DefaultTableModel(data, columnNames);
        tblMain = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblMain);
        panel4.add(scrollPane);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        panel4.setBorder(border);

        

        panel4.setSize(100, 100);
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.setBorder(border);
        panel.add(panel4, constraints);

        setupActionListening();

        setVisible(true);
    }

    public static void main(String[] args) {
        new BookReportUI();
    }
    
    void setupActionListening(){
        txtSearch.getDocument().addDocumentListener(new DocumentListener(){                
            public void insertUpdate(DocumentEvent e) {
                filterTable(cbxFilter.getSelectedIndex());
            }
            public void removeUpdate(DocumentEvent e) {
                filterTable(cbxFilter.getSelectedIndex());
            }
            public void changedUpdate(DocumentEvent e) {
                filterTable(cbxFilter.getSelectedIndex());
            }
        });
    }

    void filterTable(int i){    
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        tblMain.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(txtSearch.getText(), i));
    }
    
}
