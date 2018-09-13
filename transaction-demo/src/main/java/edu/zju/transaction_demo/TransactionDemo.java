package edu.zju.transaction_demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionDemo {
	
	public static void insertDemo() {
		
		Connection connection = null;
		
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		try {
			connection = DBUtil.getConnection();
			if (connection == null) {
				System.err.println("Get connection error.");
			}
			connection.setAutoCommit(false);
			String insertSql = "INSERT INTO t_user(user_name, user_age, user_balance) VALUES(?, ?, ?)";
			pstmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			String userName = "user1";
			int userAge = 10;
			int userBalance = 100;
			pstmt.setString(1, userName);
			pstmt.setInt(2, userAge);
			pstmt.setInt(3, userBalance);
			int rowAffected = pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rowAffected == 1) {
				System.out.println("Insert success.");
				connection.commit();
			} else {
				connection.rollback();
			}
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println(e);
		} finally {
			try {
			if (rs != null) 
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (connection != null)
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
				System.err.println("Close connection error.");
			}
		}
	}

}
