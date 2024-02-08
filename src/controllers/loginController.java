package controllers;

import model.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;

public class loginController {
   private static String dbName = "BOOK_STORE_MANGEMENT", user = "root", password = "123";
   private static Database db = new Database(dbName, user, password);
   private String email, passoword;

   public loginController(String email, String passoword) {
      this.passoword = passoword;
      this.email = email;
   }

   public boolean checkEmployee() {
      String query = "SELECT  Employee.Password , Person.Email FROM Employee inner join Person on Employee.PersonID =  Person.PersonID ;";

      return db.checkEmploye(query, email, passoword);

   }

   public boolean checkAdmin() {
      String query = "SELECT  Administrator.Password , Person.Email FROM Administrator inner join Person on Administrator.PersonID =  Person.PersonID ;";
      return db.checkAdmin(query, email, passoword);
   }

}
