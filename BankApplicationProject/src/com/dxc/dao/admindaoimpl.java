package com.dxc.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import com.dxc.pojos.bankusers;

public class admindaoimpl {
	public boolean authenticateadmin(int userid,String password)
	{
		
		if(userid==123 && password.equals("admin"))
		{
			return true;
		}
		return false;
	}
	public void adduser(bankusers s){
		Connection c = null;
	      try {
	    	  System.out.println(s.getAccno());
	    	  System.out.println(s.getPassword());
	    	  
	    	  System.out.println(s.getBalance());
	    	 
		         
		         Class.forName("com.mysql.jdbc.Driver");
 c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?autoReconnect=true&useSSL=false","root","@$123");
	        
		           
		        
	         String sql = "INSERT INTO bankusers (accno,password,balance) VALUES (?, ?, ? )";
	         
	         PreparedStatement statement = c.prepareStatement(sql);
	         statement.setInt(1, s.getAccno());
	         statement.setString(2, s.getPassword());
	         statement.setInt(3,(int)s.getBalance());
	        
	         int rowsInserted = statement.executeUpdate();
	         if (rowsInserted > 0) {
	             System.out.println("A new user was inserted successfully!");
	         }
	         c.close();
		}
		catch(Exception e)
		{
			System.out.println("unable to open db");
			e.printStackTrace();
		}
		}
	   public int removeuser(int accno)  
	      {
	    	  int found=0;
	    	  Connection c = null;
	    	  
	    	  try {
	    		  Class.forName("com.mysql.jdbc.Driver");
	    		  c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?autoReconnect=true&useSSL=false","root","@$123");
	    		 	        
	 	        PreparedStatement pstmt=c.prepareStatement("select * from bankusers");
				
				ResultSet rset=pstmt.executeQuery();
				while(rset.next())
				{
					if(accno==rset.getInt(1))
					{
						found=1;
						break;
					}
				}
	  			if(found==1)
	  			{
	  				PreparedStatement pstmt2=c.prepareStatement("DELETE FROM bankusers where accno=?");
		  			pstmt2.setInt(1, accno);
		  			pstmt2.execute();
		  			return(1);
	  			}
	  			else
	  			{
	  				return(-1);
	  			}
	  			
	  			
	  		} 
	  		catch (SQLException | ClassNotFoundException e)
	  		{
	  			e.printStackTrace();
	  			return 0;
	  		}
	    	  
	      }
	   public List<bankusers> retrieveuser(int accno)  
	      {
	    	  int found=0;
	    	  List<bankusers> list=new ArrayList<>();
	    	  Connection c = null;
	    	  try {
	    		  Class.forName("com.mysql.jdbc.Driver");
	    		  c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?autoReconnect=true&useSSL=false","root","@$123");
	 	        PreparedStatement pstmt=c.prepareStatement("select * from bankusers");
				
				ResultSet rset=pstmt.executeQuery();
				while(rset.next())
				{
					if(accno==rset.getInt(1))
					{
						found=1;
						break;
					}
				}
	  			if(found==1)
	  			{
	  				PreparedStatement pstmt2=c.prepareStatement("select * from bankusers where accno=?");
		  			pstmt2.setInt(1, accno);
		  			ResultSet rset2=pstmt2.executeQuery();
		  			while(rset2.next())
		  			{
		  				list.add(new bankusers(rset2.getInt(1),rset2.getString(2),rset2.getInt(3)));
		  			}
		  			return(list);
	  			}
	  			else
	  			{
	  				list.add(new bankusers(-1,"null",0));
	  				return list;
	  			}
	  			
	  			
	  		} 
	  		catch (SQLException | ClassNotFoundException e)
	  		{
	  			e.printStackTrace();
	  			list.add(new bankusers(-1,"null",0));
  				return list;
	  		}
	    	  
	      }
	   public int retrivebalance(int accno)  
	      {
	    	  int found=0,balance=-1;
	    	  Connection c = null;
	    	  try {
	    		  Class.forName("com.mysql.jdbc.Driver");
	    		  c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?autoReconnect=true&useSSL=false","root","@$123");
	 	        PreparedStatement pstmt=c.prepareStatement("select * from bankusers");
				
				ResultSet rset=pstmt.executeQuery();
				while(rset.next())
				{
					if(accno==rset.getInt(1))
					{
						found=1;
						break;
					}
				}
	  			if(found==1)
	  			{
	  				PreparedStatement pstmt2=c.prepareStatement("select * from bankusers where accno=?");
		  			pstmt2.setInt(1, accno);
		  			ResultSet rset2=pstmt2.executeQuery();
		  			while(rset2.next())
		  			{
		  			balance=rset2.getInt(3);
		  			}
		  			return balance;
	  			}
	  			else
	  			{
	  				return(-1);
	  			}
	  			
	  			
	  		} 
	  		catch (SQLException | ClassNotFoundException e)
	  		{
	  			e.printStackTrace();
	  			return 0;
	  		}
	    	  
	      }
	   public String updateuser(int accno,String password)  
	      {
	    	  int found=0;
	    	  Connection c = null;
	    	  try {
	    		  Class.forName("com.mysql.jdbc.Driver");
	    		  c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?autoReconnect=true&useSSL=false","root","@$123");
	 	        PreparedStatement pstmt=c.prepareStatement("select * from bankusers");
				
				ResultSet rset=pstmt.executeQuery();
				while(rset.next())
				{
					if(accno==rset.getInt(1))
					{
						found=1;
						System.out.println("found the customer");
						break;
					}
				}
	  			if(found==1)
	  			{
	  				PreparedStatement pstmt2=c.prepareStatement("UPDATE bankusers SET password=? WHERE accno=?");
		  			pstmt2.setString(1, password);
		  			pstmt2.setInt(2,accno);
		  			pstmt2.execute();
		  			System.out.println("updated the customer");
		  			return("updated the user");
		  			
	  			}
	  			else
	  			{
	  				return("unable to find the user");
	  			}
	  			
	  			
	  		} 
	  		catch (SQLException | ClassNotFoundException e)
	  		{	System.out.println("problem in db");
	  			e.printStackTrace();
	  			return "problem in db";
	  		}
	    	  
	      }
	   public List<bankusers> getallbankusers() {
	 		List<bankusers> list=new ArrayList<>();
	 		Connection conn = null;
	 		try {
	 			Class.forName("com.mysql.jdbc.Driver");
	    		  Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankapplication?autoReconnect=true&useSSL=false","root","@$123");
	 			Statement stmt=conn.createStatement();
	 			ResultSet rs=stmt.executeQuery("select * from bankusers");
	 			while(rs.next())
	 			{
	 				list.add(new bankusers(rs.getInt(1),rs.getString(2),rs.getInt(3)));
	 			}
	 			
	 		}catch(Exception e)
	 		{
	 			e.printStackTrace();
	 		}
	 		return list;
	 	}
	   
}
