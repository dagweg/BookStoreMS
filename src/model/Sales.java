package model;

import java.time.LocalDate;

class Sales{
    // This sales report will be displayed on the JTable
    static private Object salesReport[][] = {
        {"COL1" , "COL2" , "COL3"},
        {"ROW1" , "ROW1" , "ROW1"},
        {"ROW2" , "ROW2" , "ROW2"}
    };
    
    static Object[][] generateSalesReport(){
        // TODO
        return salesReport;
    }
}
