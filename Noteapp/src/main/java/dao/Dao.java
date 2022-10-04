package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.*;

public class Dao {
	private String url;
	private String user;
	private String pass;
	private Connection conn;
	
	public Dao(Connection conn) {
		this.conn = conn;
	}
	
	public String test() {
		return "oof";
	}
	
	public boolean getConnection() {
		try {
	        if (conn == null || conn.isClosed()) {
	           try {
	                Class.forName("com.mysql.jdbc.Driver").newInstance();
	            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
	                throw new SQLException(e);
	            }
	           
	            conn = DriverManager.getConnection(url, user, pass);
	            
	        }
	        return true;
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean checkUserLogin(String username, String password) {
		try {
			String sql = "SELECT userID FROM users WHERE username = ? AND password = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet result = pstmt.executeQuery();
			
			if(result.first()) {
				return true;
			} else {
				return false;
			}
		} catch(SQLException e) {
			return false;
		}
	}
}