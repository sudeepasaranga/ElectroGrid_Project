package model;


import java.sql.* ;

public class Complaint {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");     

			
			// provide DBServer/DBName, username, password
			
			con= DriverManager.getConnection("jdbc:mysql://localhost:3306/electrogrid", "root", "saranga"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	
	public String insertComplaint(String perName, String cAccNo, String cArea, String cPhone, String comp) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into complaint(`cID`, `perName`, `cAccNo`, `cArea`, `cPhone`, `comp`)" + " values ( ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, perName);
			preparedStmt.setString(3, cAccNo);
			preparedStmt.setString(4, cArea);
			preparedStmt.setString(5, cPhone);
			preparedStmt.setString(6, comp);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the complaint.";
			System.err.println(e.getMessage());
		}
		return output;
	}


	
	public String readComplaint() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Complaint ID</th><th>Person Name</th><th>Account No</th><th>Area</th><th>PhoneNum</th><th>Complaint</th></tr>";
			String query = "select * from complaint";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String cID = Integer.toString(rs.getInt("cID"));
				String perName = rs.getString("perName");
				String cAccNo = rs.getString("cAccNo");
				String cArea = rs.getString("cArea");
				String cPhone = rs.getString("cPhone");
				String comp = rs.getString("comp");

				output += "<tr><td>" + cID + "</td>";
				output += "<td>" + perName + "</td>";
				output += "<td>" + cAccNo + "</td>";
				output += "<td>" + cArea + "</td>";
				output += "<td>" + cPhone + "</td>";
				output += "<td>" + comp + "</td>";

			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the complaint.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	/*
	//updating complaint management
		public String updateComplaint(String cID, String perName, String cAccNo, String cArea, String cPhone, String comp )

		{
			String output = "";
			
			
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for updating.";
				}
				// create a prepared statement
				String query = "UPDATE complaint SET perName=?,cAccNo=?,cArea=?,cPhone=?,comp? WHERE cID=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setString(1, perName);
				preparedStmt.setString(2, cAccNo);
				preparedStmt.setString(3, cArea);
				preparedStmt.setString(4, cPhone);
				preparedStmt.setString(5, comp);		
				preparedStmt.setInt(6, Integer.parseInt(cID));
				// execute the statement
				 preparedStmt.execute();
				con.close();
				
				output = "updated successfully";
				
				
			} catch (Exception e) {
				
				output = "Error while updating";
				System.err.println(e.getMessage());
				
			}
			return output;
		}
		
		*/
	
		//Delete
		
		public String deleteComplaint(String cID) 
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
			     String query = "delete from complaint where cID=?"; 
			     
			     PreparedStatement preparedStmt = con.prepareStatement(query); 
			     
			     // binding values
			     preparedStmt.setInt(1, Integer.parseInt(cID)); 
			     
			    // execute the statement
			    preparedStmt.execute(); 
			    con.close(); 
			    
			    output = "Complaint Deleted successfully"; 
		  } 
		   catch (Exception e) 
		  { 
		       output = "Error while deleting the user."; 
		       System.err.println(e.getMessage()); 
		  } 
		 
	   	 return output; 
		 } 
		
		
		///read specific record
		
				public String readOneComplaint()
				 {
				 String output = "";
				 try
				 {
				 Connection con = connect();
				 if (con == null)
				 {return "Error while connecting to the database for reading."; }
				 // Prepare the html table to be displayed
				 output = "<table border='1'><tr><th>Person Name</th><th>Account No:</th>" +
				 "<th>Area</th><th>Phone Num</th><th>Complaint</th>" +
				 "<th>Update</th><th>Remove</th></tr>";
			
				 String query = "select * from complaint where cID= (Select max(cID) from complaint)";
				 Statement stmt = con.createStatement();
				 ResultSet rs = stmt.executeQuery(query);
				 // iterate through the rows in the result set
				 while (rs.next())
				 {
				 String cID = Integer.toString(rs.getInt("cID"));
				 String perName = rs.getString("perName");
				 String cAccNo = rs.getString("cAccNo");
				 String cArea= rs.getString("cArea");
				 String cPhone = rs.getString("cPhone");
				 String com = rs.getString("com");
			
				 // Add into the html table
				 output += "<tr><td>" + perName + "</td>";
				 output += "<td>" + cAccNo + "</td>";
				 output += "<td>" + cArea + "</td>";
				 output += "<td>" + cPhone + "</td>";
				 output += "<td>" + com + "</td>";
				 
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
				 + "<td><form method='post' action='items.jsp'>"
				 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
				 + "<input name='cID' type='hidden' value='" + cID
				 + "'>" + "</form></td></tr>";
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
