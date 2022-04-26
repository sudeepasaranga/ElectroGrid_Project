package model;

import java.sql.*;

public class Bill {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");     

			
			// Provide the correct details: DBServer/DBName, username, password
			
			con= DriverManager.getConnection("jdbc:mysql://localhost:3306/electrogrid","root", "19990121"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	
	public String insertBill(String UserName, String UserAddress, String UnitCount, String BillAmount, String DueAmount, String Date) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			
			
			
			// create a prepared statement
			
			String query = " insert into bill( BillID, UserName, UserAddress, UnitCount, BillAmount, DueAmount, Date)"
					+ " values( ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, UserName);
			preparedStmt.setString(3, UserAddress);
			preparedStmt.setInt(4, Integer.parseInt(UnitCount));
			preparedStmt.setDouble(5, Double.parseDouble(BillAmount));
			preparedStmt.setDouble(6, Double.parseDouble(DueAmount));
			preparedStmt.setString(7, Date);
			
			
			
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while Inserting the bill.";
			System.err.println(e.getMessage());
		}
		return output;
		
	}
	
	
	//reading bills
	
		public String readBill() {
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for reading.";
				}
				
				
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>User Name</th>" + "<th>User Address</th>" + "<th>Unit Count</th>"
						+ "<th>Bill Amount</th>" + "<th>Due Amount</th>"+ "<th>Date</th>"+"<th>Update</th><th>Remove</th></tr>";

				String query = "select * from bill";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				
				// iterate through the rows in the result set
				while (rs.next()) {
					String BillID = Integer.toString(rs.getInt("BillID"));
					String UserName = rs.getString("UserName");
					String UserAddress = rs.getString("UserAddress");
					String UnitCount = rs.getString("UnitCount");
					String BillAmount = Double.toString(rs.getDouble("BillAmount"));
					String DueAmount = Double.toString(rs.getDouble("DueAmount"));
					String Date = rs.getString("Date");
					
					
					// Add into the html table
					output += "<tr><td>" + UserName + "</td>";
					output += "<td>" + UserAddress + "</td>";
					output += "<td>" + UnitCount + "</td>";
					output += "<td>" + BillAmount + "</td>";
					output += "<td>" + DueAmount + "</td>";
					output += "<td>" + Date + "</td>";
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
							+ "<td><form method='post' action='items.jsp'>"
							+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
							+ "<input name='BillID' type='hidden' value='" + BillID + "'>" + "</form></td></tr>";
				}
				con.close();
				// Complete the html table
				output += "</table>";
			} catch (Exception e) {
				output = "Error while reading the bill.";
				System.err.println(e.getMessage());
			}
			return output;	
		}
		
	
		//updating bills
	
		public String updateBill(String BillID, String UserName, String UserAddress, String UnitCount, String BillAmount, String DueAmount, String Date)

		{
			String output = "";
			
			
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for updating.";
				}
				// create a prepared statement
				String query = "UPDATE bill SET UserName=?, UserAddress=?, UnitCount=?, BillAmount=?, DueAmount=?, Date=? WHERE BillID=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setString(1, UserName);
				preparedStmt.setString(2, UserAddress);
				preparedStmt.setInt(3, Integer.parseInt(UnitCount));
				preparedStmt.setString(4, BillAmount);
				preparedStmt.setString(5, DueAmount);
				preparedStmt.setString(6, Date);
				preparedStmt.setInt(7, Integer.parseInt(BillID));
				// execute the statement
				 preparedStmt.execute();
				con.close();
				
				output = "Bill updated successfully";
				
				
			} catch (Exception e) {
				
				output = "Error while updating the bill.";
				System.err.println(e.getMessage());
				
			}
			return output;
		}
		
		
		public String deleteBill(String BillID) 
		 { 
		        String output = ""; 
		 try
		 { 
			       Connection con = connect(); 
			       
			       if (con == null) 
			      {
			    	   return "Error while connecting to the database for deleting."; 
			      } 
			       
			     // create a prepared statement
			     String query = "delete from bill where BillID=?"; 
			     
			     PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			     
			     // binding values
			     preparedStmt.setInt(1, Integer.parseInt(BillID)); 
			 
			     
			    // execute the statement
			    preparedStmt.execute(); 
			    con.close(); 
			    
			    output = "Bill Deleted successfully"; 
		  } 
		   catch (Exception e) 
		  { 
		       output = "Error while deleting the bill."; 
		       System.err.println(e.getMessage()); 
		  } 
		 
	    	 return output; 
		 }
	
	//read most relevant bill details
	
	        public String readOneBill()
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
		+ "<th>Bill ID</th>"
		+ "<th>User Name</th>"
		+ "<th>User Address</th>"
		+ "<th>Unit Count</th>"
		+ "<th>Bill Amount</th>"
		+ "<th>Due Amount</th>"
		+ "<th>Date</th>"
		+ "<th>Update</th>"
		+ "<th>Remove</th>"
		+ "</tr>";


               String query = "select * from bill where BillID= (Select max(BillID) from bill)";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		
              // iterate through the rows in the result set
	   
	      while (rs.next())
		{

		String BillID = rs.getString("BillID");
		String UserName = rs.getString("UserName");
		String UserAddress = rs.getString("UserAddress");
		String UnitCount = rs.getString("UnitCount");
		String BillAmount = rs.getString("BillAmount");
		String DueAmount = rs.getString("DueAmount");
		String Date = rs.getString("Date");
		      
	      // Add into the html table
		      
		output += "<tr><td>" + BillID + "</td>";
		output += "<td>" + UserName + "</td>";
		output += "<td>" + UserAddress + "</td>";
		output += "<td>" + UnitCount + "</td>";
		output += "<td>" + BillAmount + "</td>";
		output += "<td>" + DueAmount + "</td>";
		output += "<td>" + Date + "</td>";
		
		
		// buttons
		      
		output += "<td>" + "<input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'>" + "</td>"
		+ "<td>" + " <input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>" + "</td></tr>"

		+"<input name='BillID' type='hidden' value='" + BillID + "'>" + "</form></td></tr>";
		}
		con.close();
		
			
}
