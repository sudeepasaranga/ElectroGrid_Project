package model;


import java.sql.*;

public class Payment {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");     

			
			// Provide the correct details: DBServer/DBName, username, password
			
			con= DriverManager.getConnection("jdbc:mysql://localhost:3306/electrogrid","root", "root"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertPayment(String paymentCode, String payMethod, String cardHolder, String cardNo, String cvv, String amount, String email, String expDate) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting...";
			}
			
			
			// create a prepared statement
			String query = " insert into payment( paymentId, paymentCode, payMethod, cardHolder, cardNo, cvv, amount, email, expDate)"
					+ " values( ?, ?, ?, ?, ?,?, ?, ?, ? )";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, paymentCode);
			preparedStmt.setString(3, payMethod);
			preparedStmt.setString(4, cardHolder);
			preparedStmt.setString(5, cardNo);
			preparedStmt.setString(6, cvv);
			preparedStmt.setDouble(7, Double.parseDouble(amount));
			preparedStmt.setString(8, email);
			preparedStmt.setString(9, expDate);
			
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Payment was inserted successfully!";
		} catch (Exception e) {
			output = "Error while Inserting the payment...";
			System.err.println(e.getMessage());
		}
		return output;
		
	}
	
	
	//reading payments
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
					+ "<th>Payment Code</th>"
					+ "<th>Payment ID</th>"
					+ "<th>Payment Method</th>" 
					+ "<th>CardHolder Name</th>"
					+ "<th>Card Number</th>" 
					+ "<th>CVV</th>" 
					+ "<th>Total Amount</th>" 
					+ "<th>Email Address</th>" 
					+ "<th>Expiry Date</th>" 
					+ "<th>Action</th>"
					//+ "<th>Update</th><th>Remove</th>"
					+ "</tr>";

			String query = "select * from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String paymentCode = rs.getString("paymentCode");
				String paymentId = rs.getString("paymentId");
				String payMethod = rs.getString("payMethod");
				String cardHolder = rs.getString("cardHolder");
				String cardNo = rs.getString("cardNo");
				String cvv = rs.getString("cvv");
				String amount = rs.getString("amount");
				String email = rs.getString("email");
				String expDate = rs.getString("expDate");
				
				// Add into the html table
				output += "<tr><td>" + paymentId + "</td>";
				output += "<td>" + paymentCode + "</td>";
				output += "<td>" + payMethod + "</td>";
				output += "<td>" + cardHolder + "</td>";
				output += "<td>" + cardNo + "</td>";
				output += "<td>" + cvv + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + expDate + "</td>";
				
				 //buttons
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
	public String updatePayment(String paymentId, String paymentCode, String payMethod, String cardHolder, String cardNo, String cvv, String amount, String email, String expDate)

	{
		String output = "";
		
		
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating...";
			}
			// create a prepared statement
			String query = "UPDATE payment SET paymentCode=?,payMethod=?,cardHolder=?,cardNo=?,cvv=?, amount=?, email=?, expDate=? WHERE paymentId=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, paymentCode);
			preparedStmt.setString(2, payMethod);
			preparedStmt.setString(3, cardHolder);
			preparedStmt.setString(4, cardNo);
			preparedStmt.setString(5, cvv);
			preparedStmt.setDouble(6, Double.parseDouble(amount));
			preparedStmt.setString(7, email);
			preparedStmt.setString(8, expDate);
			preparedStmt.setInt(9, Integer.parseInt(paymentId));
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
	public String deletePayment(String paymentId) 
	 { 
	        String output = ""; 
	 try
	 { 
		       Connection con = connect(); 
		       
		       if (con == null) 
		      {
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
	  } 
	   catch (Exception e) 
	  { 
	       output = "Error while deleting the user..."; 
	       System.err.println(e.getMessage()); 
	  } 
	 
   	 return output; 
	 }
		
		
}
