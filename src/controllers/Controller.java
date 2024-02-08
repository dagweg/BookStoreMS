package controllers;

import model.*;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.print.attribute.standard.MediaSize.NA;

public class Controller {

    private static String dbName = "BOOK_STORE_MANGEMENT", user = "root", password = "123";
    private static Database db = new Database(dbName, user, password);
    public static Connection conn = null ;
    public ResultSet resultSet;
    public Statement statement;
    

    public Controller() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");  //lib name
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_store_mangement",user, password); //dbname , mysqlaccname, mysqlpass
	}

    public void addBook(String title, String price, String authorID, String pubID, String publicationDate, String genre, String stockQuantity) throws SQLException{  

        String query = "INSERT INTO Book (Title, Price, AuthorID, publisherID, pubDate, Genre, StockQuantity) VALUES (?,?,?,?,?,?,?);";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1,title );
        pstmt.setString(2,price );
        pstmt.setString(3,authorID );
        pstmt.setString(4,pubID );
        pstmt.setString(5,publicationDate );
        pstmt.setString(6,genre );
        pstmt.setString(7,stockQuantity );

        int rowsAffected = pstmt.executeUpdate();
        System.out.println(rowsAffected + " row(s) affected.");
        
    };

    // NOT WORKING
    public static void editBook(String title, String price, String authorID, String pubID, String publicationDate, String genre, String stockQuantity,String bookID) throws SQLException{
        String query = "UPDATE Book SET Title=?, Price=?, AuthorID=?, publisherID=?, pubDate=?, Genre=?, StockQuantity=? WHERE BookID=?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, title);
        pstmt.setString(2, price);
        pstmt.setString(3, authorID);
        pstmt.setString(4, pubID);
        pstmt.setString(5, publicationDate);
        pstmt.setString(6, genre);
        pstmt.setString(7, stockQuantity);
        pstmt.setString(8, bookID);

        int rowsAffected = pstmt.executeUpdate();
        System.out.println(rowsAffected + " row(s) affected.");
    };


    public void removeBook(Integer bookID) throws SQLException{
        String query = "DELETE FROM Sales WHERE BookID=?";
        
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, bookID);
        
        int rowsAffected = pstmt.executeUpdate();
        System.out.println(rowsAffected + " row(s) affected.");

        query = "DELETE FROM Book WHERE BookID=?";
        pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, bookID);
        
        rowsAffected = pstmt.executeUpdate();
        System.out.println(rowsAffected + " row(s) affected.");
    }

    public void removeEmployee(Integer employeeID) throws SQLException{
        String query = "";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, employeeID);
        int rowsAffected = pstmt.executeUpdate();
        System.out.println(rowsAffected + " row(s) affected.");
    }

    public static ArrayList<Object[]> retrieveAllBooks(){
        return db.executeQuery("SELECT * FROM BOOK;");
    }

    public static Object[][] retrieveBookTableData(){
        return arrayListToObjectArr(
            db.executeQuery(
                "SELECT b.BookId, b.Title, a.Name, b.Price FROM Book b JOIN Author a ON b.PersonID = a.AuthorID;"
            )
        );
    }
    
    public static Object[][] retrieveEmployeeTableData(){
        return arrayListToObjectArr(
            db.executeQuery(
                "SELECT p.PersonID, p.Name, p.Gender, e.Job, e.Salary FROM Person p JOIN Employee e ON p.PersonID = e.EmployeeID;"
            )
        );
    }


    public static void sortById(Object[][] tableData) {
        // Assuming 1st column given is id
        for (int i = 0; i < tableData.length; i++) {
            for (int j = i + 1; j < tableData.length; j++) {
                if ((int) tableData[j][0] < (int) tableData[i][0]) {
                    Object[] tmp = tableData[i];
                    tableData[i] = tableData[j];
                    tableData[j] = tmp;
                }
            }
        }
    }

    public static Object[][] retrieveAllBookObjects(){
        return arrayListToObjectArr(
            db.executeQuery("Select BookID, title, p.Name , price FROM Book JOIN Author a ON a.AuthorID = Book.AuthorID JOIN Person p ON p.PersonID = a.PersonID ORDER BY BookID;")
        );
    }

    public static ArrayList<Object[]> retrieveBookWithId(Integer id){
        return db.executeQuery("SELECT * FROM Book WHERE BookID = " + Integer.toString(id) + ";");
    }

    public static Object[][] retrieveBookObjsWithId(Integer id){
        return arrayListToObjectArr(retrieveBookWithId(id));
    }

    // DONT EDIT
    public static Object[] retrieveEditBookInformation(Integer id){
        Object[][] ret = arrayListToObjectArr(
                db.executeQuery("SELECT b.Title, Person.Name, p.Name, b.PubDate, b.Genre, b.Price, b.StockQuantity\n" + //
                        "FROM Book b\n" + //
                        "JOIN Author a ON b.AuthorID = a.AuthorID\n" + //
                        "JOIN Publisher p ON b.PublisherID = p.PublisherID\n" + //
                        "RIGHT JOIN Person ON a.PersonID = Person.PersonID\n" + //
                        "WHERE BookID = " + id)  //
        );
        if(ret.length != 0) return ret[0];
        else return null;
    }

    public static Object[] retrieveEditEmployeeInformation(Integer id){
        Object[][] ret = arrayListToObjectArr(
                db.executeQuery("SELECT p.Name, p.DOB, p.Gener, p.Address, p.Phone, p.Email, e.Password, e.Salary, e.Job FROM Person p JOIN Employee e ON p.PersonID = e.PersonID;")  //
        );
        if(ret.length != 0) return ret[0];
        else return null;
    }

    public static ArrayList<Object[]> retrieveAllEmployees(){
        return db.executeQuery("SELECT person.PersonID, person.Name, person.Gender, employee.Job, employee.Salary FROM person JOIN employee ON person.PersonID = employee.PersonID;");
    }

    public static Object[][] retrieveAllEmployeesObject(){
        ArrayList<Object[]> objs = retrieveAllEmployees();
        Object[][] ret = new Object[objs.size()][];
        
        int i = 0;
        for(Object[] obj : objs){
            ret[i++] = obj;
        }

        return ret;
    }


    public static void discountBook(Integer id, Double basePrice) throws SQLException{
        String query = "UPDATE Book SET Price = ? WHERE BookID=?";
        PreparedStatement pstmt = conn.prepareStatement(query);

        pstmt.setString(1, Double.toString(basePrice));
        pstmt.setString(2, Integer.toString(id));
        int rowsAffected = pstmt.executeUpdate();

        System.out.println(rowsAffected + " row(s) affected."); 

    }
    
    public Object[][] getAuthorIdAndName(){
        String query = "SELECT p.PersonID, p.Name from Person p RIGHT JOIN Author a ON p.PersonID = a.AuthorID;";
        return arrayListToObjectArr(db.executeQuery(query));
    }

    public Object[] getAuthorNames(Object[][] authorIdAndName){
        Object[] names = new Object[authorIdAndName.length];
        for (int i = 0; i < authorIdAndName.length; i++) {
            names[i] = authorIdAndName[i][1];
        }
        return names;
    }

    public Object[][] getPublisherIdAndName(){
        String query = "SELECT PublisherId, Name FROM Publisher;";
        return arrayListToObjectArr(db.executeQuery(query));
    }

    public Object[] getPublisherNames(Object[][] publisherIdAndName){
        Object[] names = new Object[publisherIdAndName.length];
        for (int i = 0; i < publisherIdAndName.length; i++) {
            names[i] = publisherIdAndName[i][1];
        }
        return names;
    }

    public Object[][] getAuthorTable(){
        String query = "SELECT p.Name, p.SSN, p.Age, p.Gender, p.Address, p.Phone, p.Email, p.DOB FROM Person p RIGHt JOIN Author a ON p.PersonID = a.PersonID;";
        return arrayListToObjectArr(db.executeQuery(query));
    }

    public Object[][] getPublisherTable(){
        String query = "SELECT PublisherID, Name, Email FROM Publisher;";
        return arrayListToObjectArr(db.executeQuery(query));
    }
    public void addAuthor(String Name, String SSN, String Age, String Gender, String Address, String Phone, String Email, String DateOfBirth) throws Exception{
        String query = "INSERT INTO Person (Name, SSN, Age, Gender, Address, Phone, Email, DOB) VALUES (?,?,?,?,?,?,?,?);";
        PreparedStatement pstmt1 = conn.prepareStatement(query);
        pstmt1.setString(1,Name );
        pstmt1.setString(2,SSN );
        pstmt1.setString(3,Age );
        pstmt1.setString(4,Gender );
        pstmt1.setString(5,Address );
        pstmt1.setString(6,Phone );
        pstmt1.setString(7,Email );
        pstmt1.setString(8, DateOfBirth);
        //int rowsAffected1 = pstmt1.executeUpdate();
        //System.out.println(rowsAffected1 + " row(s) affected in Person table.");

        query = "INSERT INTO Author (PersonID) SELECT PersonID FROM Person WHERE SSN = ?;";
        PreparedStatement pstmt2 = conn.prepareStatement(query);
        pstmt2.setString(1, SSN);
        //int rowsAffected2 = pstmt2.executeUpdate();
        //System.out.println(rowsAffected2 + " row(s) affected in Author table.");
    }
    // some crud functionality for the employees 

    public  void addEmployee(String title, String price, String authorID, String pubID, String publicationDate, String genre, String stockQuantity) throws SQLException{  
        

        // BookID INTEGER PRIMARY KEY AUTO_INCREMENT,
        // Title VARCHAR(50),
        // PubDate DATE,
        // Price DOUBLE,
        // QuantitySold INTEGER,
        // StockQuantity INTEGER, 
        // Genre ENUM("Fiction","Essay","Biography","Drama","Poetry","Non-fiction"),
        // AuthorID INTEGER NOT NULL,
        // FOREIGN KEY (AuthorID) REFERENCES Author(AuthorID),
        // PublisherID INTEGER NOT NULL,
        // FOREIGN KEY (PublisherID) REFERENCES Publisher(PublisherID)

        String query = "INSERT INTO Book (Title, Price, AuthorID, publisherID, pubDate, Genre, StockQuantity) VALUES (?,?,?,?,?,?,?);";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1,title );
        pstmt.setString(2,price );
        pstmt.setString(3,authorID );
        pstmt.setString(4,pubID );
        pstmt.setString(5,publicationDate );
        pstmt.setString(6,genre );
        pstmt.setString(7,stockQuantity );

        int rowsAffected = pstmt.executeUpdate();
        System.out.println(rowsAffected + " row(s) affected.");
        
    };

    // NOT WORKING
    public static void editEmployee(String title, String price, String authorID, String pubID, String publicationDate, String genre, String stockQuantity,String bookID) throws SQLException{
         String query = "UPDATE Book SET Title=?, Price=?, AuthorID=?, pubID=?, pubDate=?, Genre=?, StockQuantity=? WHERE BookID=?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, title);
        pstmt.setString(2, price);
        pstmt.setString(3, authorID);
        pstmt.setString(4, pubID);
        pstmt.setString(5, publicationDate);
        pstmt.setString(6, genre);
        pstmt.setString(7, stockQuantity);
        pstmt.setString(8, bookID);
        int rowsAffected = pstmt.executeUpdate();
        System.out.println(rowsAffected + " row(s) affected.");
    };


    //some additional functionalities for the enum types


    public static Object[] getGenres(){
        return Genre.values();
    }

    public static Object[] getGender(){
        return Gender.values();
    }

    public static String getName(Genre g){
        return g.getName();
    }

    public static String getName(Gender g){
        return g.getName();
    }

    public static Object[] getGenders(){
        return Gender.values();
    }

    public static Object[] getJobTitle(){
        return JobPosition.values();
    }


    
    // tmp helper
    public static void printArray(Object[][] obj){
        for(Object[] oa : obj){
            for(Object o : oa){
                System.out.print(o + "\t");
            }
            System.out.println();
        }
    }

    public static Object[][] arrayListToObjectArr(ArrayList<Object[]> arrList){
        Object[][] ret = new Object[arrList.size()][];
        
        int i = 0;
        for(Object[] obj : arrList) {
            ret[i++] = obj;
        }

        return ret;
    }

    public boolean checkEmploye(String query, String username, String password) {
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                if (password.equals(resultSet.getString("Employee.Password")) && 
                    username.equals(resultSet.getString("Person.Email"))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public boolean checkAdmin(String query,String username , String password)
    {
        try{
     resultSet = statement.executeQuery(query);
         while(resultSet.next())
       {
     if (password.equals(resultSet.getString("Administrator.Password")) && username.equals(resultSet.getString("Person.Email"))) {
         return true;
     }
    }
    }
        catch(Exception e){
            e.printStackTrace();
        }
        return false ;
    }

    public void addPublisher(String Name, String Email){
        String query = "INSERT INTO Publisher (Name, Email) VALUES (?,?);";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1,Name );
        pstmt.setString(2,price );


        int rowsAffected = pstmt.executeUpdate();
        System.out.println(rowsAffected + " row(s) affected.");
    }

    /** More database manipulation functions HERE */
}
