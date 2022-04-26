package com;

import model.Payment; 
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



@Path("/Payments")
public class PaymentService {


	Payment itemObj = new Payment();

	@GET
	@Path("/all")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return itemObj.readPayment();
	
		
	}
	
	// insert payment API
		@POST
		@Path("/insert")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)

		public String insertPayment(@FormParam("paymentCode") String paymentCode, @FormParam("payMethod") String payMethod, @FormParam("cardHolder") String cardHolder,
				@FormParam("cardNo") String cardNo, @FormParam("cvv") String cvv, @FormParam("amount") String amount, @FormParam("email") String email, @FormParam("expDate") String expDate) {
			String output = itemObj.insertPayment(paymentCode, payMethod, cardHolder, cardNo, cvv, amount, email, expDate);
			return output;
		}
		
		// API for update payment
		@PUT
		@Path("/update")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)

		public String updatePayment(String itemData) {
			// Convert the input string to a JSON object
			JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
			// Read the values from the JSON object
			String paymentId = itemObject.get("paymentId").getAsString();
			String paymentCode = itemObject.get("paymentCode").getAsString();
			String payMethod = itemObject.get("payMethod").getAsString();
			String cardHolder = itemObject.get("cardHolder").getAsString();
			String cardNo = itemObject.get("cardNo").getAsString();
			String cvv = itemObject.get("cvv").getAsString();
			String amount = itemObject.get("amount").getAsString();
			String email = itemObject.get("email").getAsString();
			String expDate = itemObject.get("expDate").getAsString();
			
			String output = itemObj.updatePayment(paymentId, paymentCode, payMethod, cardHolder, cardNo, cvv, amount, email, expDate);
			
			
			return output;
		}
		//API for delete payment

		@DELETE
		@Path("/delete") 
		@Consumes(MediaType.APPLICATION_XML) 
		@Produces(MediaType.TEXT_PLAIN) 
		public String deletePayment(String itemData) 
		{ 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <userID>
		 String paymentId = doc.select("paymentId").text(); 
		 String output = itemObj.deletePayment(paymentId); 
		return output; 
		}
	//API for read a latest payment
		@GET
		@Path("/latest")
		@Produces(MediaType.TEXT_HTML)
		public String readOnePayment()
		 {
			return itemObj.readOnePayment();
		 } 
		
		

		
}

