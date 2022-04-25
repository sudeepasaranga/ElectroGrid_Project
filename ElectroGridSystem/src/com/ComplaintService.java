package com;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Complaint; 

@Path("/ComplaintService")
public class ComplaintService {

	Complaint ComplaintObj = new Complaint();

	@GET
	@Path("/ComplaintRead")
	@Produces(MediaType.TEXT_HTML)
	public String readComplaint() {
		return ComplaintObj.readComplaint();
	}
	
	
	@POST
	@Path("/ComplaintAdd")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertComplaint(@FormParam("perName") String perName, 			
			@FormParam("cAccNo") String cAccNo,
			@FormParam("cArea") String cArea,
			@FormParam("cPhone") String cPhone,
			@FormParam("comp") String comp) {
		String output = ComplaintObj.insertComplaint(perName, cAccNo, cArea, cPhone,comp);
		return output;

	}
	

	// API for update unit 
	@PUT
	@Path("/ComplaintUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateComplaint(String itemData) {
		// Convert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		// Read the values from the JSON object
		String cID = itemObject.get("cID").getAsString();
		String perName = itemObject.get("perName").getAsString();
		String cAccNo = itemObject.get("cAccNo").getAsString();
		String cArea = itemObject.get("cArea").getAsString();
		String cPhone = itemObject.get("cPhone").getAsString();
		String comp = itemObject.get("comp").getAsString();

		String output = ComplaintObj.updateComplaint(cID, perName, cAccNo,cArea,cPhone,comp);
		
		
		return output;
	}

		//API for delete specific unit
		
		
		@DELETE
		@Path("/ComplaintDelete") 
		@Consumes(MediaType.APPLICATION_XML) 
		@Produces(MediaType.TEXT_PLAIN) 
		public String deleteComplaint(String complaintData) 
		{ 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(complaintData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <userID>
		 String cID = doc.select("cID").text(); 
		 String output = ComplaintObj.deleteComplaint(cID); 
		return output; 
		} 
		
		
		
		//API for read specific unit range
		
		@GET
		@Path("/getoneComplaint")
		@Produces(MediaType.TEXT_HTML)
		public String readAPayment()
		 {
			return ComplaintObj.readOneComplaint();
		 } 
}
