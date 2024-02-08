package model;

import java.sql.*;

import java.util.ArrayList;

public class Database {
     Connection conn;
     Statement statement;
     PreparedStatement pstmt;
     ResultSet resultSet;
    

    public Database(String dbName, String user, String password){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName, user, password);
            
            statement = conn.createStatement();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void pstmt(String query){
        try{
            pstmt = conn.prepareStatement(query);
        }catch(Exception e){ e.printStackTrace();}
    }


    public ArrayList<Object[]> executeQuery(String query){
        ArrayList<Object[]> table = new ArrayList<Object[]>();
        try
        {            
            if(statement.execute(query)){
                resultSet = statement.getResultSet();
            }else{
                return null;
            }

            ResultSetMetaData metaData = resultSet.getMetaData();

            int numCols = metaData.getColumnCount();
        
            while(resultSet.next()){
                Object[] row = new Object[numCols];

                for(int i = 0; i < numCols; i++){
                    row[i] = resultSet.getObject(i+1);
                }
                
                table.add(row);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return table;
    }

    public void commit() {
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
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
}
