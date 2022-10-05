package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.*;
import conn.Connections;

public class Dao {
	
	public static boolean checkUserLogin(String username, String password) {
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		try {
			String sql = "SELECT userID FROM users WHERE username = ? AND password = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet result = pstmt.executeQuery();

			if (result.next()) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean addNewUser(String username, String password) {
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		try {
			String sql = "INSERT INTO users (username, password) VALUES (?, ?);";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			int resultCount = pstmt.executeUpdate();
			
			if (resultCount > 0) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ArrayList<Note> getUserNotes(int userID) {
		ArrayList<Note> user_notes = new ArrayList<>();
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return user_notes;
		}
		
		try {
			String sql = "SELECT * FROM notes WHERE userID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);
			ResultSet result = pstmt.executeQuery();
			
			while(result.next()) {
				Note current_note = new Note();
				current_note.setNoteID(result.getInt(1));
				current_note.setTitle(result.getString(2));
				current_note.setContent(result.getString(3));
				current_note.setDate(result.getString(4));
				current_note.setNotePublic(result.getBoolean(5));
				current_note.setUserID(result.getInt(6));
				user_notes.add(current_note);
			}
			
			return user_notes;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return user_notes;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ArrayList<Note> getNotesSharedToUser(int userID) {
		ArrayList<Note> notes_shared_to_user = new ArrayList<>();
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return notes_shared_to_user;
		}
		
		try {
			String sql = "SELECT notes.* FROM shares JOIN sharedTo ON shares.shareID = sharedTo.shareID JOIN notes ON shares.NoteID = notes.NoteID WHERE sharedTo.userID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);
			ResultSet result = pstmt.executeQuery();
			
			while(result.next()) {
				Note current_note = new Note();
				current_note.setNoteID(result.getInt(1));
				current_note.setTitle(result.getString(2));
				current_note.setContent(result.getString(3));
				current_note.setDate(result.getString(4));
				current_note.setNotePublic(result.getBoolean(5));
				current_note.setUserID(result.getInt(6));
				notes_shared_to_user.add(current_note);
			}
			
			return notes_shared_to_user;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return notes_shared_to_user;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ArrayList<Note> getPublicNotes(int userID) {
		ArrayList<Note> public_notes = new ArrayList<>();
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return public_notes;
		}
		
		try {
			String sql = "SELECT * FROM notes WHERE NOT userID = ? AND public = 1;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);
			ResultSet result = pstmt.executeQuery();
			
			while(result.next()) {
				Note current_note = new Note();
				current_note.setNoteID(result.getInt(1));
				current_note.setTitle(result.getString(2));
				current_note.setContent(result.getString(3));
				current_note.setDate(result.getString(4));
				current_note.setNotePublic(result.getBoolean(5));
				current_note.setUserID(result.getInt(6));
				public_notes.add(current_note);
			}
			
			return public_notes;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return public_notes;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ArrayList<User> getUsers(int userID) {
		ArrayList<User> users = new ArrayList<>();
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return users;
		}
		
		try {
			String sql = "SELECT userID, username FROM users WHERE NOT userID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);
			ResultSet result = pstmt.executeQuery();
			
			while(result.next()) {
				User current_user = new User();
				current_user.setUserID(result.getInt(1));
				current_user.setUsername(result.getString(2));
				users.add(current_user);
			}
			
			return users;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return users;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Note getNote(int noteID) {
		Connection conn = null;
		Note note = new Note();
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return note;
		}
		
		try {
			String sql = "SELECT * FROM notes WHERE noteID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noteID);
			ResultSet result = pstmt.executeQuery();

			if (result.next()) {
				note.setNoteID(result.getInt(1));
				note.setTitle(result.getString(2));
				note.setContent(result.getString(3));
				note.setDate(result.getString(4));
				note.setNotePublic(result.getBoolean(5));
				note.setUserID(result.getInt(6));
				return note;
			} else {
				return note;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return note;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean addNewNote(String title, String content, String date, boolean notePublic, int userID) {
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		try {
			String sql = "INSERT INTO notes (title, content, date, public, userID) VALUES (?, ?, ?, ?, ?);";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, date);
			pstmt.setBoolean(4, notePublic);
			pstmt.setInt(5, userID);
			int resultCount = pstmt.executeUpdate();
			
			if (resultCount > 0) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}