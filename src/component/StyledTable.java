package component;


import javax.swing.*;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StyledTable extends JTable {

    public StyledTable(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
        setUI(new CustomTableUI());
    }

    private class CustomTableUI extends BasicTableUI {

        @Override
        public void installUI(JComponent c) {
            super.installUI(c);

            JTable table = (JTable) c;
            table.setRowHeight(30);
            table.setIntercellSpacing(new Dimension(0, 0));
            table.setSelectionBackground(Color.LIGHT_GRAY);
            table.setSelectionForeground(Color.BLACK);
            table.setFont(new Font("Arial", Font.PLAIN, 14));

            // Set cell renderer to center text horizontally and vertically
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            centerRenderer.setVerticalAlignment(JLabel.CENTER);
            table.setDefaultRenderer(Object.class, centerRenderer);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columnNames = {"Name", "Age", "Gender"};
        Object[][] rowData = {
                {"John", 25, "Male"},
                {"Sarah", 30, "Female"},
                {"David", 40, "Male"},
                {"Linda", 35, "Female"}
        };
        StyledTable table = new StyledTable(rowData, columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}