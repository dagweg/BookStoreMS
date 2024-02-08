package model;

class Customer extends Person{
    // This customer report will be displayed on the JTable
    static private Object customerReports[][] = {
        {"COL1" , "COL2" , "COL3"},
        {"ROW1" , "ROW1" , "ROW1"},
        {"ROW2" , "ROW2" , "ROW2"}
    };
    
    static Object[][] generateCustomerReports(){
        // TODO
        return customerReports;
    }
}