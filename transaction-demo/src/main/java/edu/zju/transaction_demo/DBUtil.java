package edu.zju.transaction_demo;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;

public class DBUtil {
	
	private static Connection connection;
	
	private static final String USERNAME = "root";
	private static final String PASSWORD = "1234";
	private static final String URL = "jdbc:mysql://localhost:3306/db_demo?charset=utf-8";
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void init() {
		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Connection getConnection() {
		if (connection == null) {
			init();
		}
		return connection;
	}
	
	public static Connection getSinCon() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
	
	public PreparedStatement getPreparedStatement(String sql) throws SQLException {
		return getConnection().prepareStatement(sql);
	}
	
	public static void close(Connection connection, PreparedStatement pstmt, ResultSet rs) {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
}
