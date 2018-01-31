package BookData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;

public class sqlEngine {// start class
	private PreparedStatement pstmt;
	private java.sql.Connection conn;
	int delete;
	/**
	 * This method is used for the connection
	 * to the database calling on the driver and calling an 
	 * instance of it. 
	 */
	public void connect() {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/itb?useSSL=false", "root", "rooney10");

		} catch (Exception ex) {
			System.err.println("Cannot connect to database server");
		}

	}
	
	/**
	 * This method is used to close and kill the connection
	 * to the database.
	 */
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());

		}

	}// end close connection

	/**
	 * This method is used to execute the insertQuery.
	 * It takes two parameters from the class used from 
	 * the user.
	 * @param user String value taken from user
	 * @param pass user String value taken from user
	 * @throws SQLException in case of an error in sql input
	 */
	public void insertQuery(String user, String pass) throws SQLException {

		String sql = "insert into students(Username,Pass)" + "values(?,?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user);
			pstmt.setString(2, pass);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw e;
		}

	}// end executeQuery
	
	
	/**
	 * This method is used to execute the searchQuery
	 * for the users in the database.
	 * It takes two parameters from the class used from 
	 * the user.
	 * @param id String value taken from user
	 * @param pass user String value taken from user
	 * @throws SQLException in case of an error in sql input
	 */
	public ResultSet searchUserQuery(String id, String pass) throws SQLException {
		ResultSet rs = null;
		String sql = "select * from students where StudentID =? and Pass=?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			//pstmt.close();
			if (pstmt.execute())
				rs = pstmt.getResultSet();

		} catch (SQLException e) {
			throw e;
		}
		return rs;

	}// end executeQuery
	
	/**
	 * This method is used to execute the searchQuery
	 * for the books in the database.
	 * It takes a parameter from the class used from 
	 * the user.
	 * @param isbn int value taken from user
	 * @throws SQLException in case of an error in sql input
	 */
	public ResultSet searchBookQuery(int isbn) throws SQLException {
		ResultSet rs = null;
		String sql = "select * from books where ISBN =?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, isbn);
			//pstmt.close();
			if (pstmt.execute())
				rs = pstmt.getResultSet();

		} catch (SQLException e) {
			throw e;
		}
		return rs;

	}// end executeQuery
	
	/**
	 * This method is used to execute the searchQuery
	 * for the certain StudentID in the database.
	 * It takes a parameter from the class used from 
	 * the user.
	 * @param id int value taken from user
	 * @throws SQLException in case of an error in sql input
	 */
	public ResultSet searchStudentID(int id) throws SQLException {
		ResultSet rs = null;
		String sql = "select StudentID from students";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//pstmt.setInt(1, id);
			//pstmt.close();
			if (pstmt.execute())
				rs = pstmt.getResultSet();

		} catch (SQLException e) {
			throw e;
		}
		return rs;

	}// end executeQuery
	
	/**
	 * This method is used to execute the insertQuery
	 * for the books in the database.
	 * It takes a parameter from the class used from 
	 * the user.
	 * @param title string value taken from user
	 * @param genre string value taken from user
	 * @throws SQLException in case of an error in sql input
	 */
	public void insertBookQuery(String title, String genre) throws SQLException {

		String sql = "insert into books(Title,Genre)" + "values(?,?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, genre);
			//pstmt.setString(3, genre);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw e;
		}

	}// end executeQuery
	
	/**
	 * This method is used to execute the searchQuery
	 * for the certain ISBN in the database.
	 * It takes a parameter from the class used from 
	 * the user.
	 * @throws SQLException in case of an error in sql input
	 */
	public ResultSet searchISBN() throws SQLException {
		ResultSet rs = null;
		String sql = "select ISBN from books";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//pstmt.setInt(1, isbn);
			//pstmt.close();
			if (pstmt.execute())
				rs = pstmt.getResultSet();

		} catch (SQLException e) {
			throw e;
		}
		return rs;

	}// end executeQuery
	
	public ResultSet getISBN(int isbn) throws SQLException {
		ResultSet rs = null;
		String sql = "select * from books where ISBN =?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, isbn);
			//pstmt.close();
			if (pstmt.execute())
				rs = pstmt.getResultSet();

		} catch (SQLException e) {
			throw e;
		}
		return rs;

	}// end executeQuery
	
	
	/**
	 * This method is used to execute the deleteQuery
	 * for the certain book in the database.
	 * It takes a parameter from the class used from 
	 * the user.
	 * @param isbn int value taken from user
	 * @throws SQLException in case of an error in sql input
	 */
	public void deleteBookQuery(int isbn) throws SQLException {
		
		String sql = "delete from books where ISBN =?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, isbn);
			delete = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}

	}// end executeQuery
	
	public ResultSet searchBook(){
		ResultSet rs = null;
		String sql = "Select * from books";
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			if (pstmt.execute())
				rs = pstmt.getResultSet();
		}catch(SQLException e){
		}
		return rs;
	}
	
	public int getDelete(){
		return delete;
	}
	

}// end class
