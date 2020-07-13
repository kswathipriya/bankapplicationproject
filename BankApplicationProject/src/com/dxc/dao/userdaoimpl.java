package com.dxc.dao;
import java.sql.*;
import com.dxc.pojos.bankusers;
import java.util.ArrayList;
import java.util.List;

import com.dxc.pojos.trans;
public class userdaoimpl {
	public List<trans> ministmt(int accno)
	{	List<trans> list=new ArrayList<>();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
  		  Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankapplication?autoReconnect=true&useSSL=false","root","@$123");
	         PreparedStatement pstmt3=conn.prepareStatement("select * from trans where accno=?");
	  			pstmt3.setInt(1, accno);
	  			ResultSet rset=pstmt3.executeQuery();
			while(rset.next())
			{
				list.add(new trans(rset.getInt(1),rset.getInt(4),rset.getInt(2),rset.getString(3)));
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
		
	
	public int moneytransfer(int fromacc,int toacc,int amount)
	{
		int s1,s2=0;
		s1=removebalance(fromacc,amount);
		if(s1==1)
	     s2=addbalance(toacc,amount);
		else if(s1==-1)
			return -1;
		else if(s1==0)
			return 0;
		else if(s1==-2)
			return -2;
			
		if(s1==1 && s2==1)
			return 1;
		if(s1==1 && s2==-1)
			return 3;
		if(s1==1 && s2==0)
			return 4;
		
		return 2;
	}
	public int authenticateuser(int accno,String password)
	{int found=0;
	int token;
	  Connection c = null;
	  try {
		  Class.forName("com.mysql.jdbc.Driver");
		  c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?autoReconnect=true&useSSL=false","root","@$123");
	        PreparedStatement pstmt=c.prepareStatement("select * from bankusers");
			
			ResultSet rset=pstmt.executeQuery();
			while(rset.next())
			{
				if(accno==rset.getInt(1)&&password.equals(rset.getString(2)))
				{	
					found=1;
					return 1;
				}
			
			}
			if(found==0)
			{
				return(0);
			}
			
			}
	catch (SQLException | ClassNotFoundException e)
		{	System.out.println("problem in db");
			e.printStackTrace();
			return -1;
		}
		
		return 2;

	}
	
	public int addbalance(int accno,int amount)  
    {
  	  int found=0,balance=0,token=0;
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
					System.out.println("found the user");
					balance=rset.getInt(3);
					break;
				}
			}
			if(found==1)
			{
				PreparedStatement pstmt2=c.prepareStatement("UPDATE bankusers SET balance=? WHERE accno=?");
	  			pstmt2.setInt(1, balance+amount);
	  			pstmt2.setInt(2,accno);
	 
	  			pstmt2.execute();
	  			PreparedStatement pstmt4=c.prepareStatement("select * from timestamp");
	  			ResultSet rset4=pstmt4.executeQuery();
	  			while(rset4.next())
	  			{
	  				token=rset4.getInt(1);
	  			}
	  			PreparedStatement pstmt3=c.prepareStatement("INSERT INTO trans(accno,amount,type,timestamp) VALUES(?,?,?,?)");
	  			pstmt3.setInt(1, accno);
	  			pstmt3.setInt(2,amount);
	  			pstmt3.setString(3, "deposit");
	  			pstmt3.setInt(4, token);
	  			pstmt3.execute();
	  			System.out.println("updated the user");
	  			
	  			PreparedStatement pstmt5=c.prepareStatement("update timestamp set token=token+1");
	  			pstmt5.execute();
	  			return(1);
			}
			else
			{
				return(-1);
			}
			}
  	catch (SQLException | ClassNotFoundException e)
		{	System.out.println("problem in db");
			e.printStackTrace();
			return 0;
		}
  	  
    }
	public int removebalance(int accno,int amount)  
    {
  	  int deductible=0,balance=0;int found=0,token=0;
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
					System.out.println("found the user");
					balance=rset.getInt(3);
					if(balance-amount>=0)
					{
						deductible=1;
					}
					else
					{
						
						return 0;
					}
					break;
				}
			}
			if(found==0)
			{
				return -1;
			}
			
			if(deductible==1)
			{
				PreparedStatement pstmt2=c.prepareStatement("UPDATE bankusers SET balance=? WHERE accno=?");
	  			pstmt2.setInt(1, balance-amount);
	  			pstmt2.setInt(2,accno);
	 
	  			pstmt2.execute();
	  			System.out.println("updated the student");
	  			PreparedStatement pstmt4=c.prepareStatement("select * from timestamp");
	  			ResultSet rset4=pstmt4.executeQuery();
	  			while(rset4.next())
	  			{
	  				token=rset4.getInt(1);
	  			}
	  			PreparedStatement pstmt3=c.prepareStatement("INSERT INTO trans(accno,amount,type,timestamp) VALUES(?,?,?,?)");
	  			pstmt3.setInt(1, accno);
	  			pstmt3.setInt(2,amount);
	  			pstmt3.setString(3, "withdrawl");
	  			pstmt3.setInt(4, token);
	  			pstmt3.execute();
	  			System.out.println("updated the user");
	  			
	  			PreparedStatement pstmt5=c.prepareStatement("update timestamp set token=token+1");
	  			pstmt5.execute();
	  			return(1);
	  			
			}
			
			}
  	catch (SQLException | ClassNotFoundException e)
		{	System.out.println("problem in db");
			e.printStackTrace();
			return -2;
		}
  	  return 2;
    }
	public int retrivebalance(int accno)  
    {
  	  int found=0,balance=0;
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
			return -2;
		}
  	  
  	  
    }
	 public int updatepassword(int accno,String oldpassword,String newpassword)  

     {
   	  int found=0,authenticated=0;
   	  Connection c = null;
   	  try {
   		Class.forName("com.mysql.jdbc.Driver");
		  c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?autoReconnect=true&useSSL=false","root","@$123");
	        PreparedStatement pstmt=c.prepareStatement("select * from bankusers");
			
			ResultSet rset=pstmt.executeQuery();
			while(rset.next())
			{
				if(accno==rset.getInt(1))
				{found=1;
				
					if(oldpassword.equals(rset.getString(2)))
					{
					authenticated=1;
					break;
					}
					else
					{
						return 0;
					}
				}
			}
 			if(authenticated==1)
 			{
 				PreparedStatement pstmt2=c.prepareStatement("UPDATE bankusers SET password=? WHERE accno=?");
	  			pstmt2.setString(1, newpassword);
	  			pstmt2.setInt(2,accno);
	  			
	  			pstmt2.execute();
	  			System.out.println("updated the student");
	  			return(1);
	  			
 			}
 			if(found==0)
 			{
 				return(-1);
 			}
 			
 			
 		} 
 		catch (SQLException | ClassNotFoundException e)
 		{	System.out.println("problem in db");
 			e.printStackTrace();
 			return -2;
 		}
   	  return 2;
   	  
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
}
