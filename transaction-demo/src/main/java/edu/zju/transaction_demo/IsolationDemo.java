package edu.zju.transaction_demo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

/**
 * 测试mysql的四种隔离级别。
 * 
 * @author jfqiao
 * 
 */

public class IsolationDemo {

	public static void uncommittedRead() {
		// 未提交读: 产生了脏读的数据。
		Thread t1 = new Thread(new Runnable() {

			public void run() {
				Connection connection = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					// t2先执行完一个查询，然后t1执行。
					Thread.sleep(1000);
					connection = DBUtil.getSinCon();
					connection.setAutoCommit(false);
					connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
					String sql = "INSERT INTO t_book(book_name) VALUES(?)";
					pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					pstmt.setString(1, "book_name");
					int affectedRow = pstmt.executeUpdate();
					// rs = pstmt.getGeneratedKeys();
					// if (rs.next())
					// System.out.println("INSERT RETURN PRIMARY KEY: " + rs.getInt(1));
					// 不 commit，使得t2执行第二个查询。
					Thread.sleep(1000);
					if (affectedRow == 1) {
						connection.commit();
					} else {
						connection.rollback();
					}
				} catch (SQLException e) {
					// TODO: handle exception
					System.err.println(e);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					DBUtil.close(connection, pstmt, rs);
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				Connection connection = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;
				ResultSet rs2 = null;
				try {
					connection = DBUtil.getSinCon();
					connection.setAutoCommit(false);
					connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
					String sql = "SELECT count(*) FROM t_book";
					pstmt = connection.prepareStatement(sql);
					pstmt2 = connection.prepareStatement(sql);
					rs = pstmt.executeQuery();
					if (rs.next()) {
						System.out.println("Book Nums: " + rs.getInt(1));
					}
					// 等待t1完成插入
					Thread.sleep(1000);
					rs2 = pstmt2.executeQuery();
					if (rs2.next()) {
						System.out.println("NEXT Book Nums: " + rs2.getInt(1));
					}
					connection.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					DBUtil.close(null, pstmt2, rs2);
					DBUtil.close(connection, pstmt, rs);
				}
			}
		});
		t2.start();
		t1.start();
	}

	public static void committedRead() {
		// 提交读: 解决了脏读，产生重复读不一致。
		Thread t1 = new Thread(new Runnable() {

			public void run() {
				Connection connection = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					// t2先执行完一个查询，然后t1执行。
					Thread.sleep(1000);
					connection = DBUtil.getSinCon();
					connection.setAutoCommit(false);
					connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					String sql = "INSERT INTO t_book(book_name) VALUES(?)";
					pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					pstmt.setString(1, "book_name");
					int affectedRow = pstmt.executeUpdate();
					// rs = pstmt.getGeneratedKeys();
					// if (rs.next())
					// System.out.println("INSERT RETURN PRIMARY KEY: " + rs.getInt(1));
					// commit。 使得t2可以看到t1提交的数据。
					// Thread.sleep(1000);
					if (affectedRow == 1) {
						connection.commit();
					} else {
						connection.rollback();
					}
				} catch (SQLException e) {
					// TODO: handle exception
					System.err.println(e);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					DBUtil.close(connection, pstmt, rs);
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				Connection connection = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;
				ResultSet rs2 = null;
				try {
					connection = DBUtil.getSinCon();
					connection.setAutoCommit(false);
					connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					String sql = "SELECT count(*) FROM t_book";
					pstmt = connection.prepareStatement(sql);
					pstmt2 = connection.prepareStatement(sql);
					rs = pstmt.executeQuery();
					if (rs.next()) {
						System.out.println("Book Nums: " + rs.getInt(1));
					}
					// 等待t1完成插入。如果t1不commit, t2无法看到新插入的数据。
					// 如果t1commit完成，t2可以看到新插入的数据。
					Thread.sleep(1000);
					rs2 = pstmt2.executeQuery();
					if (rs2.next()) {
						System.out.println("NEXT Book Nums: " + rs2.getInt(1));
					}
					connection.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					DBUtil.close(null, pstmt2, rs2);
					DBUtil.close(connection, pstmt, rs);
				}
			}
		});
		t2.start();
		t1.start();
	}

	public static void repeatableRead() {
		// 重复读：解决了脏读，重复读问题。
		Thread t1 = new Thread(new Runnable() {

			public void run() {
				Connection connection = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					// t2先执行完一个查询，然后t1执行。
					Thread.sleep(1000);
					connection = DBUtil.getSinCon();
					connection.setAutoCommit(false);
					// 不管是插入还是更新，repeatable read都不会导致其他事物感知到不管事物是否commit。
					connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
					String sql = "INSERT INTO t_book(book_name) VALUES(?)";
					pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					pstmt.setString(1, "book_name");
					int affectedRow = pstmt.executeUpdate();
					// rs = pstmt.getGeneratedKeys();
					// if (rs.next())
					// System.out.println("INSERT RETURN PRIMARY KEY: " + rs.getInt(1));
					// commit。 使得t2可以看到t1提交的数据。
					// Thread.sleep(1000);
					if (affectedRow == 1) {
						connection.commit();
					} else {
						connection.rollback();
					}
				} catch (SQLException e) {
					// TODO: handle exception
					System.err.println(e);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					DBUtil.close(connection, pstmt, rs);
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				Connection connection = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;
				ResultSet rs2 = null;
				try {
					connection = DBUtil.getSinCon();
					connection.setAutoCommit(false);
					connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
					String sql = "SELECT count(*) FROM t_book";
					pstmt = connection.prepareStatement(sql);
					pstmt2 = connection.prepareStatement(sql);
					rs = pstmt.executeQuery();
					if (rs.next()) {
						System.out.println("Book Nums: " + rs.getInt(1));
					}
					// 无论t1是否commit， t2都看不到他的数据。
					Thread.sleep(10000);
					rs2 = pstmt2.executeQuery();
					if (rs2.next()) {
						System.out.println("NEXT Book Nums: " + rs2.getInt(1));
					}
					connection.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					DBUtil.close(null, pstmt2, rs2);
					DBUtil.close(connection, pstmt, rs);
				}
			}
		});
		t2.start();
		t1.start();
	}

	public static void phantomRead() {
		phantomRead(Connection.TRANSACTION_REPEATABLE_READ);
	}

	public static void phantomRead(final int level) {
		// 幻读问题。其他事物插入数据并commit后，并事物可以修改该插入的数据，并且修改后可以看到了。（修改前无法看到）
		// 如果两个事物都想修改某个记录（或删除），则有一个会完成，另外一个必须等待锁释放（即commit).
		// 如果事物A删除了记录R，并commit，在事物B中看到的R不会改变。无论是否在事物B中修改该记录。
		int countNum = 0;
		final String countSql = "SELECT COUNT(*) FROM t_book";
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = DBUtil.getSinCon();
			pstmt = connection.prepareStatement(countSql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				countNum = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBUtil.close(connection, pstmt, rs);
		}
		final int bookId = countNum + 1;
		Thread t1 = new Thread(new Runnable() {
			// 插入一条数据
			public void run() {
				// TODO Auto-generated method stub
				Connection connection = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					// wait t2 execute the first query.
					Thread.sleep(1000);
					connection = DBUtil.getSinCon();
					connection.setAutoCommit(false);
					connection.setTransactionIsolation(level);
					String sql = "INSERT INTO t_book VALUE(?, ?)";
					pstmt = connection.prepareStatement(sql);
					pstmt.setInt(1, bookId);
					pstmt.setString(2, "book_name");
					int rowAffected = pstmt.executeUpdate();
					if (rowAffected == 1) {
						connection.commit();
					} else {
						connection.rollback();
						System.err.println("Connection rollback.");
					}
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					DBUtil.close(connection, pstmt, rs);
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			// 事物t1插入后，t2可以修改插入的数据。
			// 两次读出的数据不一致。
			public void run() {
				// TODO Auto-generated method stub
				Connection connection = null;
				PreparedStatement pstmtFirst = null;
				PreparedStatement pstmtSec = null;
				PreparedStatement pstmtThr = null;
				ResultSet rsFirst = null;
				ResultSet rsThr = null;
				try {
					connection = DBUtil.getSinCon();
					connection.setAutoCommit(false);
					connection.setTransactionIsolation(level);
					String updateSql = "UPDATE t_book SET book_name = ? where book_id = ?";
					pstmtFirst = connection.prepareStatement(countSql);
					pstmtSec = connection.prepareStatement(updateSql);
					pstmtThr = connection.prepareStatement(countSql);
					pstmtSec.setString(1, "new book name");
					pstmtSec.setInt(2, bookId);
					rsFirst = pstmtFirst.executeQuery();
					if (rsFirst.next()) {
						System.out.println("Book nums: " + rsFirst.getInt(1));
					}
					// wait for t1.
					Thread.sleep(1500);
					pstmtSec.executeUpdate();
					rsThr = pstmtThr.executeQuery();
					if (rsThr.next()) {
						System.out.println("Book nums: " + rsThr.getInt(1));
					}
					connection.commit();
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					DBUtil.close(null, pstmtFirst, rsFirst);
					DBUtil.close(null, pstmtSec, null);
					DBUtil.close(connection, pstmtThr, rsThr);
				}
			}
		});
		t2.start();
		t1.start();
	}

	public static void serializable() {
		// 串行化
		// 读出的数据一致。
		phantomRead(Connection.TRANSACTION_SERIALIZABLE);
	}

}
