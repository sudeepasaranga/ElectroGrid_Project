package model;


import java.sql.*;

public class Payment {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");     

			
			// Provide the correct details: DBServer/DBName, user name, password
			
			con= DriverManager.getConnection("jdbc:mysql://localhost:3306/electrogrid","root", "root"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertPayment(String dateOfpay, String payMethod, String cardHolder, String cardNo, String cvv, String expDate, String totamount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting...";
			}
			
			
			// create a prepared statement
			String query = " insert into payment( paymentId, dateOfpay, payMethod, cardHolder, cardNo, cvv, expDate, totamount)"
					+ " values( ?, ?, ?, ?, ?,?, ?, ? )";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, dateOfpay);
			preparedStmt.setString(3, payMethod);
			preparedStmt.setString(4, cardHolder);
			preparedStmt.setString(5, cardNo);
			preparedStmt.setString(6, cvv);
			preparedStmt.setString(7, expDate);
		
		    preparedStmt.setDouble(8, Double.parseDouble(totamount));
			//
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Payment was inserted successfully!";
		} catch (Exception e) {
			output = "Error while inserting the payment...";
			System.err.println(e.getMessage());
		}
		return output;
		
	}
	
	
	//reading all payments
	public String readPayment() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading...";
			}
			
			
			// Prepare the html table to be displayed
			output = "<table border='1'>"
					+ "<tr>"
					+ "<th>Payment ID</th>"
					+ "<th>Date Of Pay</th>"
					+ "<th>Payment Method</th>" 
					+ "<th>CardHolder Name</th>"
					+ "<th>Card Number</th>" 
					+ "<th>CVV</th>" 
					+ "<th>Expiry Date</th>" 
					+ "<th>Total Amount</th>" 
					+ "<th>Action</th>"
					+ "</tr>";

			String query = "select * from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String paymentId = rs.getString("paymentId");
				String dateOfpay = rs.getString("dateOfPay");
				String payMethod = rs.getString("payMethod");
				String cardHolder = rs.getString("cardHolder");
				String cardNo = rs.getString("cardNo");
				String cvv = rs.getString("cvv");
				String expDate = rs.getString("expDate");
				String totamount = rs.getString("totamount");
				
				// Add into the html table
				output += "<tr><td>" + paymentId + "</td>";
				output += "<td>" + dateOfpay + "</td>";
				output += "<td>" + payMethod + "</td>";
				output += "<td>" + cardHolder + "</td>";
				output += "<td>" + cardNo + "</td>";
				output += "<td>" + cvv + "</td>";
				output += "<td>" + expDate + "</td>";
				output += "<td>" + totamount + "</td>";
				
				 //action buttons
			output += "<td>" + "<input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'>"
					+ " <input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>" + "</td></tr>"
				
				 +"<input name='paymentId' type='hidden' value='" + paymentId + "'>" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the payment...";
			System.err.println(e.getMessage());
		}
		return output;	
	}
		
	//updating payments
	public String updatePayment(String paymentId, String dateOfpay, String payMethod, String cardHolder, String cardNo, String cvv, String expDate, String totamount){
		String output = "";
	    try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating...";
			}
			
			// create a prepared statement
			String query = "UPDATE payment SET dateOfpay=?,payMethod=?,cardHolder=?,cardNo=?,cvv=?, expDate=?, totamount=? WHERE paymentId=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, dateOfpay);
			preparedStmt.setString(2, payMethod);
			preparedStmt.setString(3, cardHolder);
			preparedStmt.setString(4, cardNo);
			preparedStmt.setString(5, cvv);
			preparedStmt.setString(6, expDate);
			preparedStmt.setDouble(7, Double.parseDouble(totamount));
			preparedStmt.setInt(8, Integer.parseInt(paymentId));
			
			// execute the statement
			 preparedStmt.execute();
			 con.close();
			 output = "Payment was updated successfully!";
		} catch (Exception e) {
			 output = "Error while updating the payment...";
			 System.err.println(e.getMessage());
		}
		return output;
	}
	
	//delete payment
	public String deletePayment(String paymentId) { 
	    String output = ""; 
	    try { 
		    Connection con = connect(); 
		    if (con == null) {
		    	   return "Error while connecting to the database for deleting..."; 
		      } 
		       
		     // create a prepared statement
		     String query = "delete from payment where paymentId=?"; 
		     PreparedStatement preparedStmt = con.prepareStatement(query); 
		     
		     // binding values
		     preparedStmt.setInt(1, Integer.parseInt(paymentId)); 
		     
		    // execute the statement
		    preparedStmt.execute(); 
		    con.close(); 
		    output = "Payment was deleted successfully!"; 
		    } catch (Exception e) { 
	            output = "Error while deleting the payment..."; 
	            System.err.println(e.getMessage()); 
	        } 
	        return output; 
	 }
		//read most relevant payment details
		public String readOnePayment()
		 {
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {
			 return "Error while connecting to the database for reading..."; 
			 }
		 // Prepare the view table to be displayed
		 output = "<table border='1'>"
					
					+ "<tr>"
					+ "<th>Payment ID</th>"
					+ "<th>Date Of Pay</th>"
					+ "<th>Payment Method</th>" 
					+ "<th>CardHolder Name</th>"
					+ "<th>Card Number</th>" 
					+ "<th>CVV</th>" 
					+ "<th>Expiry Date</th>" 
					//+ "<th>Email Address</th>" 
					+ "<th>Total Amount</th>" 
					+ "<th>Action</th>"
					//+ "<th>Update</th><th>Remove</th>"
					+ "</tr>";

		 String query = "select * from payment where PaymentId= (Select max(PaymentId) from payment)";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 // iterate through the rows in the result set
		 while (rs.next())
		 {
			 
				String paymentId = rs.getString("paymentId");
				String dateOfpay = rs.getString("dateOfpay");
				String payMethod = rs.getString("payMethod");
				String cardHolder = rs.getString("cardHolder");
				String cardNo = rs.getString("cardNo");
				String cvv = rs.getString("cvv");
				//String totamount = rs.getString("totamount");
				//String email = rs.getString("email");
				String expDate = rs.getString("expDate");
				String totamount = rs.getString("totamount");
		 // Add into the html table
				output += "<tr><td>" + paymentId + "</td>";
				output += "<td>" + dateOfpay + "</td>";
				output += "<td>" + payMethod + "</td>";
				output += "<td>" + cardHolder + "</td>";
				output += "<td>" + cardNo + "</td>";
				output += "<td>" + cvv + "</td>";
				output += "<td>" + expDate + "</td>";
				//output += "<td>" + email + "</td>";
				output += "<td>" + totamount + "</td>";
		 // buttons
				output += "<td>" + "<input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'>"
						+ " <input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>" + "</td></tr>"
					
					 +"<input name='paymentId' type='hidden' value='" + paymentId + "'>" + "</form></td></tr>";
		 }
		 con.close();
		 // Complete the html table
		 output += "</table>";
		 }
		 catch (Exception e)
		 {
		 output = "Error while reading the items.";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 } 
		
		
		
}
