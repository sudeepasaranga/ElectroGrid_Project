package com;
import model.User;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/Users")
public class UserService {
	
	User userObj = new User();
	
	//Insert new user
	
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)

	public String insertUser(@FormParam("username") String username, @FormParam("address") String address,
			@FormParam("phonenum") String phonenum, @FormParam("email") String email, @FormParam("password") String password) {
		String output = userObj.insertUser(username, address, phonenum, email, password);
		return output;
	}
	
	//Read all users
	
	@GET
	@Path("/all") 
	@Produces(MediaType.TEXT_HTML) 
	public String readUsers() 
	 { 
	 return userObj.readUser(); 
	}
	
	
	
	//Update specific user
	
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateUser(String userData) {
		
		// Convert the input string to a JSON object
		JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject();
		
		// Read the values from the JSON object
		String userID = userObject.get("userID").getAsString();
		String username = userObject.get("username").getAsString();
		String address = userObject.get("address").getAsString();
		String phonenum = userObject.get("phonenum").getAsString();
		String email = userObject.get("email").getAsString();
		String password = userObject.get("password").getAsString();

		String output = userObj.updateUser(userID, username, address, phonenum, email, password);
		
		
		return output;
	}
	
	// Delete specific user
	
		@DELETE
		@Path("/delete") 
		@Consumes(MediaType.APPLICATION_XML) 
		@Produces(MediaType.TEXT_PLAIN) 
		public String deleteUser(String userData) 
		{ 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(userData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <userID>
		 String userID = doc.select("userID").text(); 
		 String output = userObj.deleteUser(userID); 
		return output; 
		}
		
		@GET
		@Path("/latest")
		@Produces(MediaType.TEXT_HTML)
		public String readAUser(@PathParam("userID") int userID)
		 {
			return userObj.readAUser();
		 } 
	
}
