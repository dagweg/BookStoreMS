package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class BookList extends JPanel {
      private String JScrollPane;

    public BookList(){
        Object[][] data = {
                {"John", "Doe", 30},
                {"Jane", "Doe", 25},
                {"Bob", "Smith", 45}
        };
        Object[] columnNames = {"First Name", "Last Name", "Age"};
        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        
        JTable BooksTable = new JTable(model);

        JScrollPane BookScroll  = new JScrollPane(BooksTable);

        add(BookScroll, BorderLayout.CENTER);
        this.add(BooksTable);

     
      }
}
