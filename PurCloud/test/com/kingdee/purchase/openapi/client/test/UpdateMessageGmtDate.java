package com.kingdee.purchase.openapi.client.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import junit.framework.TestCase;


public class UpdateMessageGmtDate extends TestCase {
	
	public void testTimeStamp() {
		System.out.print(new Timestamp(Long.parseLong("1400120036573")));
	}
	
	public void testUpdateMessageGmtdate() {
		Connection conn = null;
		Statement stat = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://192.168.204.132:3306/purchasedb";
			conn = DriverManager.getConnection(url, "root", "123");
			stat = conn.createStatement();
			ps = conn.prepareStatement("update t_data_msg set gmtDate=? where id=?");
			ResultSet rs = stat.executeQuery("select id, gmtBorn from t_data_msg");
			while(rs.next()) {
				ps.setTimestamp(1, new Timestamp(rs.getLong("gmtBorn")));
				ps.setLong(2, rs.getLong("id"));
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				stat.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
