package db;

import java.sql.*;

public class DBConnection {

	public static void main(String[] args) {
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			// String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://localhost:3306/dummy";
			// Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, "root", "root");
			// Class.forName("sun.jdbc.odbc.jdbcodbcDriver");
			// Connection conne =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/Booxchange", "User",
			// "Booxchange!2345678");

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SHOW TABLES;");
			while (rs.next()) {
				System.out.println("id = " + rs.getString(1));
			}
			conn.close();
		} catch (Exception e) {
			System.err.println("Got an exception!");
			// printStackTrace method
			// prints line numbers + call stack
			e.printStackTrace();
			// Prints what exception has been thrown
			System.out.println(e);
		}
	}
}

// import java.sql.*;

// public class DBConnection {
// private Connection con;

// public void connect() throws SQLException {
// String url = "jdbc:mysql://localhost:3306/mydb";
// String user = "username";
// String password = "password";

// try {
// Class.forName("com.mysql.cj.jdbc.Driver");
// } catch (ClassNotFoundException e) {
// throw new SQLException("MySQL JDBC driver not found", e);
// }

// con = DriverManager.getConnection(url, user, password);
// }

// public ResultSet getData() throws SQLException {
// String sql = "SELECT * FROM mytable";
// Statement stmt = con.createStatement();
// ResultSet rs = stmt.executeQuery(sql);
// return rs;
// }

// public void addData(String name, String email) throws SQLException {
// String sql = "INSERT INTO mytable (name, email) VALUES (?, ?)";
// PreparedStatement pstmt = con.prepareStatement(sql);
// pstmt.setString(1, name);
// pstmt.setString(2, email);
// pstmt.executeUpdate();
// }

// public void deleteData(int id) throws SQLException {
// String sql = "DELETE FROM mytable WHERE id=?";
// PreparedStatement pstmt = con.prepareStatement(sql);
// pstmt.setInt(1, id);
// pstmt.executeUpdate();
// }

// public void close() throws SQLException {
// if (con != null) con.close();
// }
// }