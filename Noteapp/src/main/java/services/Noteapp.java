package services;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import data.*;
import dao.Dao;

@Path("noteappservice")
public class Noteapp {
	
	@POST
	@Path("/checkuserlogin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User checkUserLogin(User u) {
		return Dao.checkUserLogin(u.getUsername(), u.getPassword());
	}
	
	@POST
	@Path("/registeruser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean registerUser(User u) {
		return Dao.addNewUser(u.getUsername(), u.getPassword());
	}
	
	@POST
	@Path("/getusernotes")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Note> getUserNotes(User u) {
		return Dao.getUserNotes(u.getUserID());
	}
	
	@POST
	@Path("/getusernotesharedusers")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<User> getUserNoteSharedUsers(Share s) {
		return Dao.getUserNoteSharedUsers(s.getDistributorID(), s.getNoteID());
	}
	
	@POST
	@Path("/getnotessharedtouser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Note> getNotesSharedToUser(User u) {
		return Dao.getNotesSharedToUser(u.getUserID());
	}
	
	@POST
	@Path("/getpublicnotes")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Note> getPublicNotes(User u) {
		return Dao.getPublicNotes(u.getUserID());
	}
	
	@POST
	@Path("/getusers")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<User> getUsers(User u) {
		return Dao.getUsers(u.getUserID());
	}
	
	@POST
	@Path("/getusersbysearch")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<User> getUsersBySearch(User u) {
		return Dao.getUsersBySearch(u.getUsername());
	}
	
	@POST
	@Path("/getnote")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Note getNote(Note n) {
		return Dao.getNote(n.getNoteID());
	}
	
	@POST
	@Path("/addnote")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addNote(Note n) {
		return Dao.addNewNote(n.getTitle(), n.getContent(), n.getDate(), n.isNotePublic(), n.getUserID());
	}
	
	@POST
	@Path("/sharenote")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Share shareNote(Share s) {
		return Dao.shareUserNote(s.getDistributorID(), s.getNoteID());
	}
	
	@POST
	@Path("/sharetouser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean shareToUser(ShareTo st) {
		return Dao.shareToUser(st.getShareID(), st.getUserID());
	}
	
	@PUT
	@Path("/editnote")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Note editNote(Note n) {
		return Dao.updateNote(n.getNoteID(), n.getTitle(), n.getContent(), n.getDate(), n.isNotePublic(), n.getUserID());
	}
	
	@DELETE
	@Path("/removeshareto")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean removeShareTo(ShareTo st) {
		return Dao.deleteShareTo(st.getShareID(), st.getUserID());
	}
	
	@DELETE
	@Path("/removeshare")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean removeShare(Share s) {
		return Dao.deleteShare(s.getDistributorID(), s.getNoteID());
	}
	
	@DELETE
	@Path("/removenote")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean removeNote(Note n) {
		return Dao.deleteNote(n.getNoteID(), n.getUserID());
	}
	
	@POST
	@Path("/getnotedetails")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DetailedNote getNoteDetails(Note n) {
		return Dao.getNoteDetails(n.getNoteID(), n.getUserID());
	}
	
	@POST
    @Path("/getshare")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Share getShare(Share s) {
        return Dao.getShare(s.getDistributorID(), s.getNoteID());
    }
}
