package com;

import model.UnitManagement; 
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Path("/UnitService")
public class UnitService{
	
	UnitManagement itemObj = new UnitManagement();

	@GET
	@Path("/read")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return itemObj.readUnitManagement();
	}
	
	// insert Unit Management API
		@POST
		@Path("/add")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)

		public String insertUnitManagement(@FormParam("mnValue") String mnValue, @FormParam("mxValue") String mxValue,
				@FormParam("modifiedDate") String modifiedDate, @FormParam("price") String price) {
			String output = itemObj.insertUnitManagement(mnValue, mxValue, modifiedDate, price);
			return output;
		}
		
		// API for update unit range
		@PUT
		@Path("/update")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)

		public String updateUnitManagement(String itemData) {
			// Convert the input string to a JSON object
			JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
			// Read the values from the JSON object
			String unitID = itemObject.get("unitID").getAsString();
			String mnValue = itemObject.get("mnValue").getAsString();
			String mxValue = itemObject.get("mxValue").getAsString();
			String modifiedDate = itemObject.get("modifiedDate").getAsString();
			String price = itemObject.get("price").getAsString();
		

			String output = itemObj.updateUnitManagement(unitID, mnValue, mxValue, modifiedDate, price);
			
			
			return output;
		}

		// Delete specific user
		
		@DELETE
		@Path("/delete") 
		@Consumes(MediaType.APPLICATION_XML) 
		@Produces(MediaType.TEXT_PLAIN) 
		public String deleteUnit(String unitData) 
		{ 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(unitData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <userID>
		 String unitID = doc.select("unitID").text(); 
		 String output = itemObj.deleteUnit(unitID); 
		return output; 
		}  
	   
}

