package com;

import model.Bill;


import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Path("/Bills")
public class BillService {
	
	Bill itemObj = new Bill();
	
	
	
	//read bill API

	@GET
	@Path("/view")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return itemObj.readBill();
	}
	
	//Unit Calculator
	public double calculateBill(int unit) {
		
		if(unit >100) {
			return unit * 50;
		}
		else if(unit >75 && unit <= 100) {
			return unit * 40;
		}
		else if(unit >50 && unit <= 75) {
			return unit * 30;
		}
		else if(unit >25 && unit <= 50) {
			return unit * 20;
		}
		else if(unit >0 && unit <= 25) {
			return unit * 15;
		}
		else {
			return 0.0;
		}
		
	}
	
	
	// insert bill API
			@POST
			@Path("/add")
			@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
			@Produces(MediaType.TEXT_PLAIN)

			public String insertBill(@FormParam("UserName") String UserName,
					@FormParam("UserAddress") String UserAddress, @FormParam("UnitCount") String UnitCount, @FormParam("BillAmount") String BillAmount,
					@FormParam("DueAmount") String DueAmount, @FormParam("Date") String Date ) {
				
				int unitC = Integer.parseInt(UnitCount);
				double billSum = calculateBill(unitC);

				BillAmount = String.valueOf(billSum);
	
				String output = itemObj.insertBill(UserName, UserAddress, UnitCount, BillAmount, DueAmount, Date);
				return output;
			}
			
	
			
			// API for update bill
			@PUT
			@Path("/update")
			@Consumes(MediaType.APPLICATION_JSON)
			@Produces(MediaType.TEXT_PLAIN)

			public String updateBill(String itemData) {
				// Convert the input string to a JSON object
				JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
				// Read the values from the JSON object
				String BillID = itemObject.get("BillID").getAsString();
				String UserName = itemObject.get("UserName").getAsString();
				String UserAddress = itemObject.get("UserAddress").getAsString();
				String UnitCount = itemObject.get("UnitCount").getAsString();
				String BillAmount = itemObject.get("BillAmount").getAsString();
				String DueAmount = itemObject.get("DueAmount").getAsString();
				String Date = itemObject.get("Date").getAsString();
				
				int unitC = Integer.parseInt(UnitCount);
				double billSum = calculateBill(unitC);

				BillAmount = String.valueOf(billSum);

				String output = itemObj.updateBill(BillID, UserName, UserAddress, UnitCount, BillAmount, DueAmount, Date);
				
				
				return output;
			}
	
			
			// Delete specific user
			
			@DELETE
			@Path("/delete") 
			@Consumes(MediaType.APPLICATION_XML) 
			@Produces(MediaType.TEXT_PLAIN) 
			public String deleteBill(String itemData) 
			{ 
			//Convert the input string to an XML document
			 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
			 
			//Read the value from the element <userID>
			 String BillID = doc.select("BillID").text(); 
			 String output = itemObj.deleteBill(BillID); 
			return output; 
			}
	
	
	//API for read a latest bill
	
	                @GET
			@Path("/latest")
			@Produces(MediaType.TEXT_HTML)
			public String readOneBill()
			
}
