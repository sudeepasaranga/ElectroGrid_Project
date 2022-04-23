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

		public String insertUnitManagement(@FormParam("minValue") String minValue, @FormParam("maxValue") String maxValue,
				@FormParam("modifiedDate") String modifiedDate, @FormParam("price") String price) {
			String output = itemObj.insertUnitManagement(minValue, maxValue, modifiedDate, price);
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
			String maxValue = itemObject.get("maxValue").getAsString();
			String minValue = itemObject.get("minValue").getAsString();
			String modifiedDate = itemObject.get("modifiedDate").getAsString();
			String price = itemObject.get("price").getAsString();
		

			String output = itemObj.updateUnitManagement(unitID, minValue, maxValue, modifiedDate, price);
			
			
			return output;
		}

	   
	   
}

