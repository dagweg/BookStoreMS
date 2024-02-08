
package view;

import controllers.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.util.*;

public class PointOfSaleUI extends JPanel {
    JFrame frame;
    JButton btnCheckOut, btnAdd, btnRemove, btnBack;
    ImageIcon imgArrowLeft, imgArrowRight, imgSearch;
    JTextField txtSearch;
    JRadioButton rbAuthor, rbTitle, rbPrice;

    JComboBox<Object> cbxFilters;
    Object[] objFilters = { "Id", "Title", "Author", "Price" };

    JLabel lblFilters, lblTotal;
    JPanel pnlFilters, pnlTop, pnlSearch, pnlCenter, pnlLeftCenter, pnlCenterCenter, pnlRightCenter, pnlBottom,
            pnlTotalPrice;
    JTable tblMain, tblCheckout;
    JScrollPane scrPaneMain, scrPaneCheckout;
    DefaultTableModel tblModelMain, tblModelCheckout;
    ButtonGroup btnGrpFilters;
    Border margin5 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    Object[] selectedRwTblMain, selectedRwTblCheckout;

    static String fontName;
    static Font fntTitle, headingFont, headingFontB, headingFont1, headingFont1B;
    static Dimension dimWindow = Toolkit.getDefaultToolkit().getScreenSize();

    static {
        fontName = "Tahoma";
        fntTitle = new Font(fontName, 0, 40);
        headingFont = new Font(fontName, 0, 20);
        headingFontB = new Font(fontName, Font.BOLD, 20);
        headingFont1 = new Font(fontName, 0, 15);
        headingFont1B = new Font(fontName, Font.BOLD, 15);
    }

    private enum Columns {
        ID, TITLE, AUTHOR, PRICE
    };

    private Object[] tblColumns = new Object[] { "Id", "Title", "Author", "Price" }; // for setting the table columns
    // private ArrayList<Object[]> tblContents = new ArrayList<>();
    // private Integer marginCenterPnl, width = 1500, height = 750;

    public PointOfSaleUI() {

        setLayout(new BorderLayout());
        initializeComponents();
        loadDataToTable();
        customizeComponents();
        addComponents();
        setEventHandling();

        setFrameMargin(30);
        add(new JPanel());

        add(pnlTop, BorderLayout.NORTH);
        add(pnlCenter);
        add(pnlBottom, BorderLayout.SOUTH);
        // setBounds(((int) dimWindow.getWidth() - width) / 2, ((int)
        // dimWindow.getHeight() - height) / 2, width, height);
        setVisible(true);

    }

    void initializeComponents() {
        //lblToalValue = new JLabel(fontName, null, 0);
        lblTotal = new JLabel(fontName, null, 0);
        lblFilters = new JLabel("Filters: ");
        pnlTop = new JPanel();
        pnlCenter = new JPanel(new FlowLayout());
        pnlBottom = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        pnlSearch = new JPanel();
        pnlFilters = new JPanel();
        pnlLeftCenter = new JPanel();
        pnlCenterCenter = new JPanel();
        pnlRightCenter = new JPanel(new BorderLayout());
        pnlTotalPrice = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        btnCheckOut = new JButton("Checkout");

        int d = 25;
        imgArrowLeft = new ImageIcon("src/resources/arrow_left.png");
        imgArrowRight = new ImageIcon("src/resources/arrow_right.png");
        Image img = imgArrowLeft.getImage().getScaledInstance(d, d, Image.SCALE_SMOOTH);
        imgArrowLeft.setImage(img);
        img = imgArrowRight.getImage().getScaledInstance(d, d, Image.SCALE_SMOOTH);
        imgArrowRight.setImage(img);
        btnAdd = new JButton(imgArrowRight);
        btnRemove = new JButton(imgArrowLeft);

        // imgSearch = new ImageIcon("resources/search.png");
        // img = imgSearch.getImage().getScaledInstance(d, d, Image.SCALE_SMOOTH);
        // imgSearch.setImage(img);
        // btnSearch = new JButton(imgSearch);

        // rbAuthor = new JRadioButton("Author");
        // rbTitle = new JRadioButton("Title");
        // rbPrice = new JRadioButton("Price");

        tblModelMain = new DefaultTableModel(tblColumns, 0);
        tblMain = new JTable(tblModelMain);
        scrPaneMain = new JScrollPane(tblMain);

        tblModelCheckout = new DefaultTableModel(tblColumns, 0);
        tblCheckout = new JTable(tblModelCheckout);
        scrPaneCheckout = new JScrollPane(tblCheckout);

        txtSearch = new JTextField(20);
        txtSearch.setPreferredSize(new Dimension(50, 38));

        cbxFilters = new JComboBox<>(objFilters);

        // btnGrpFilters = new ButtonGroup();
    }

    void customizeComponents() {
        lblTotal.setFont(headingFont);
        lblTotal.setText("Total: $");
        // lblTotalValue.setFont(headingFont);
        // lblTotalValue.setText("0");
        // rbTitle.setSelected(true);

        tblMain.setBorder(new EtchedBorder());
        tblMain.setFont(headingFont1);
        tblMain.setRowHeight(35);
        setTblColumnWidth(tblMain);

        scrPaneMain.setPreferredSize(new Dimension(600, 450));

        tblCheckout.setBorder(new EtchedBorder());
        tblCheckout.setFont(headingFont1);
        tblCheckout.setRowHeight(35);
        setTblColumnWidth(tblCheckout);
        scrPaneCheckout.setPreferredSize(new Dimension(600, 450));

        pnlLeftCenter.add(scrPaneMain);
        pnlLeftCenter.setBorder(margin5);
        pnlRightCenter.setBorder(margin5);
        pnlCenter.setBorder(margin5);
        pnlBottom.setBorder(margin5);

        btnCheckOut.setFont(headingFont);
        btnCheckOut.setPreferredSize(new Dimension(150, 40));
        txtSearch.setFont(headingFont);

    }

    void addComponents() {
        pnlCenterCenter.add(btnRemove);
        pnlCenterCenter.add(btnAdd);
        pnlTotalPrice.add(lblTotal);
        //pnlTotalPrice.add(lblTotalValue);
        pnlRightCenter.add(pnlTotalPrice, BorderLayout.SOUTH);
        pnlRightCenter.add(scrPaneCheckout, BorderLayout.CENTER);
        pnlCenter.add(pnlLeftCenter);
        pnlCenter.add(pnlCenterCenter);
        pnlCenter.add(pnlRightCenter);
        pnlTop.add(new JPanel());
        pnlBottom.add(btnCheckOut);

        pnlFilters.add(new JLabel("Filters: "));
        pnlFilters.add(cbxFilters);
        pnlSearch.add(txtSearch);
        pnlTop.add(pnlSearch);
        pnlTop.add(pnlFilters);
    }

    static void setTblColumnWidth(JTable tbl) {
        TableColumn tc = tbl.getColumnModel().getColumn(0);
        tc.setPreferredWidth(50);
        tc.setMaxWidth(100);
        tc = tbl.getColumnModel().getColumn(2);
        tc.setPreferredWidth(150);
        tc.setMaxWidth(200);
        tc = tbl.getColumnModel().getColumn(3);
        tc.setPreferredWidth(100);
        tc.setMaxWidth(150);
    }

    static GridBagConstraints gridBagConstraints(int x, int y, int width, int height) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        return gbc;
    }

    void setFrameMargin(Integer margin) {
        JPanel wP = new JPanel();
        wP.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, margin));
        JPanel eP = new JPanel();
        eP.setBorder(BorderFactory.createEmptyBorder(0, margin, 0, 0));
        add(wP, BorderLayout.WEST);
        add(eP, BorderLayout.EAST);
    }

    static boolean inTable(Object[] obj, JTable tbl) {
        for (int i = 0; i < tbl.getRowCount(); i++) {
            // Assuming 0th index represents ID, if ID inside the tbl, return true
            if (tbl.getValueAt(i, 0).equals(obj[0])) {
                return true;
            }
        }
        return false;
    }

    void loadDataToTable() {
        ArrayList<Object[]> objs = Controller.retrieveAllBooks();
        for (Object oa[] : objs)
            tblModelMain.addRow(oa);
    }

    private void setEventHandling() {
        ListSelectionModel lstSelectionModelMain = tblMain.getSelectionModel();
        lstSelectionModelMain.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblMain.getSelectedRow() != -1 && tblMain.getSelectedColumn() != -1) {
                int selectRow = tblMain.getSelectedRow();
                int colSize = tblMain.getColumnCount();
                selectedRwTblMain = new Object[colSize];
                for (int col = 0; col < colSize; col++) {
                    selectedRwTblMain[col] = tblMain.getValueAt(selectRow, col);
                }
            }
        });

        ListSelectionModel lstSelectionModelCheckout = tblCheckout.getSelectionModel();
        lstSelectionModelCheckout.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblCheckout.getSelectedRow() != -1
                    && tblCheckout.getSelectedColumn() != -1) {
                int selectRow = tblCheckout.getSelectedRow();
                int colSize = tblCheckout.getColumnCount();
                // Retrieve the selected value from the table
                selectedRwTblCheckout = new Object[colSize];
                for (int col = 0; col < colSize; col++) {
                    selectedRwTblCheckout[col] = tblCheckout.getValueAt(selectRow, col);
                }
            }
        });

        // NOTE : NEEDS TO BE FIXED
        btnAdd.addActionListener(e -> {
            if (selectedRwTblMain != null && tblMain.getSelectedRow() != -1) {
                tblModelCheckout.addRow(selectedRwTblMain);
                tblModelMain.removeRow(tblMain.getSelectedRow());
                // // Update the total price when new rows are added
                // Double dTotalValue = Double.parseDouble(lblTotalValue.getText()) + Double.parseDouble((String)tblMain.getValueAt(tblMain.getSelectedRow(), 3));
                // System.out.println(dTotalValue);
                // String text = String.format("%.2f", dTotalValue);
                // lblTotalValue.setText(text);
                // selectedRwTblMain = null;
            }
        });

        // NOTE : NEEDS TO BE FIXED
        btnRemove.addActionListener(e -> {
            if (selectedRwTblCheckout != null) {
                tblModelMain.addRow(selectedRwTblCheckout);
                tblModelCheckout.removeRow(tblCheckout.getSelectedRow());
                // // Update the total price when new rows are added
                // Double dTotalValue = Double.parseDouble(lblTotalValue.getText())
                //         - Double.parseDouble(((String) selectedRwTblCheckout[3]).substring(1));
                // String text = String.format("%.2f", dTotalValue);
                // lblTotalValue.setText(text);
                // selectedRwTblCheckout = null;
            }
        });

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                tableFilter(cbxFilters.getSelectedIndex());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                tableFilter(cbxFilters.getSelectedIndex());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                tableFilter(cbxFilters.getSelectedIndex());
            }
        });
    }

    void tableFilter(int i) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tblModelMain);
        tblMain.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(txtSearch.getText(), i));
    }

}
