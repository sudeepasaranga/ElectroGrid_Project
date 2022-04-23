package model;

import java.sql.* ;

public class UnitManagement {

	
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
	
	
	//Insert
	
	public String insertUnitManagement(String mnValue, String mxValue, String modifiedDate, String price) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			
			
			// create a prepared statement
			String query = " insert into unit( unitID, mnValue, mxValue, modifiedDate, price)"
					+ " values( ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setInt(2, Integer.parseInt(mnValue));
			preparedStmt.setInt(3, Integer.parseInt(mxValue));
			preparedStmt.setInt(4, Integer.parseInt(modifiedDate));
			preparedStmt.setInt(5, Integer.parseInt(price));
			
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while Inserting the unit.";
			System.err.println(e.getMessage());
		}
		return output;
		
	}
	
	
	//reading units
	public String readUnitManagement() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Min Value</th><th>Max Value</th><th>Modified Date</th>" + "<th>Price</th>"
					+"<th>Update</th><th>Remove</th></tr>";

			String query = "select * from unit";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String unitID = Integer.toString(rs.getInt("unitID"));
				String mnValue = rs.getString("mnValue");
				String mxValue = rs.getString("mxValue");
				String modifiedDate = rs.getString("modifiedDate");
				String price = Double.toString(rs.getDouble("price"));
				
				// Add into the html table
				output += "<tr><td>" + mnValue + "</td>";
				output += "<td>" + mxValue + "</td>";
				output += "<td>" + modifiedDate + "</td>";
				output += "<td>" + price + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						+ "<input name='unitID' type='hidden' value='" + unitID + "'>" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the units.";
			System.err.println(e.getMessage());
		}
		return output;	
	}
		
	//updating unit management
	public String updateUnitManagement(String unitID, String mnValue, String mxValue, String modifiedDate, String price)

	{
		String output = "";
		
		
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE unit SET mnValue=?,mxValue=?,modifiedDate=?,price=? WHERE unitID=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(mnValue));
			preparedStmt.setInt(2, Integer.parseInt(mxValue));
			preparedStmt.setInt(3, Integer.parseInt(modifiedDate));
			preparedStmt.setInt(4, Integer.parseInt(price));
			preparedStmt.setInt(5, Integer.parseInt(unitID));
			// execute the statement
			 preparedStmt.execute();
			con.close();
			
			output = "unit range updated successfully";
			
			
		} catch (Exception e) {
			
			output = "Error while updating the unit range.";
			System.err.println(e.getMessage());
			
		}
		return output;
	}
	
	//Delete
	
	public String deleteUnit(String unitID) 
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
		     String query = "delete from unit where unitID=?"; 
		     
		     PreparedStatement preparedStmt = con.prepareStatement(query); 
		     
		     // binding values
		     preparedStmt.setInt(1, Integer.parseInt(unitID)); 
		     
		    // execute the statement
		    preparedStmt.execute(); 
		    con.close(); 
		    
		    output = "Unit range Deleted successfully"; 
	  } 
	   catch (Exception e) 
	  { 
	       output = "Error while deleting the user."; 
	       System.err.println(e.getMessage()); 
	  } 
	 
   	 return output; 
	 } 
	
}
