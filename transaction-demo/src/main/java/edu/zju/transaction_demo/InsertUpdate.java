package edu.zju.transaction_demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertUpdate {
	
	public static void main(String[] args) {
		// 1000： 480ms both
		// 10000: insert: 4169ms update: 4823
		trancateTable();
		insertData();
		updateData();
	}
	
	public static void trancateTable() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = DBUtil.getSinCon();
			String sql = "truncate t_book";
			pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBUtil.close(connection, pstmt, null);
		}
	}
	
	public static void insertData() {
		long t = 0;
		Connection connection = null;
		PreparedStatement pstmt = null;
		int id = 1;
		while (id < 10000) {
			try {
				connection = DBUtil.getSinCon();
				String sql = "INSERT INTO t_book VALUES(?, ?)";
				pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, id);
				pstmt.setString(2, "book name" + id);
				long t1 = System.currentTimeMillis();
				pstmt.executeUpdate();
				t += System.currentTimeMillis() - t1;
			} catch (SQLException e) {
				// TODO: handle exception
			} finally {
				DBUtil.close(connection, pstmt, null);
			}
			id++;
		}
		System.out.println("INSERT time: " + t);
	}

	public static void updateData() {
		long t = 0;
		Connection connection = null;
		PreparedStatement pstmt = null;
		int id = 1;
		while (id < 10000) {
			try {
				connection = DBUtil.getSinCon();
				String sql = "UPDATE t_book SET book_name = ? WHERE book_id = ?";
				pstmt = connection.prepareStatement(sql);
				pstmt.setInt(2, id);
				pstmt.setString(1, "book name " + id + 1);
				long t1 = System.currentTimeMillis();
				pstmt.executeUpdate();
				t += System.currentTimeMillis() - t1;
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
				return;
			} finally {
				DBUtil.close(connection, pstmt, null);
			}
			id++;
		}
		System.out.println("Update time: " + t);
	}
}
