package com.dxc.controller;
import java.util.*;
import java.io.IOException;
import com.dxc.pojos.trans;
import com.dxc.pojos.bankusers;
import com.dxc.pojos.comparetoken;
import com.dxc.dao.userdaoimpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;
       userdaoimpl obj=new userdaoimpl();
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String action="";
		  String message="";
		  HttpSession session=request.getSession();
   	   String temp=request.getParameter("userbtn");
   	   if(temp!=null)
   		   action=temp;
   	   if(action.equals("transfer_amount"))
   	   {
   		   int status;
   		   int toacc=Integer.parseInt(request.getParameter("baccno"));
   		 int fromacc=(int)session.getAttribute("user"); 
   		 int amount=Integer.parseInt(request.getParameter("amount"));
   		 status=obj.moneytransfer(fromacc, toacc, amount);
   		 if(status==1)
   		 {
   			 session.setAttribute("message", "transaction success");
   		 }
   		 if(status==-1)
   		 {session.setAttribute("message", "user not found");}
   		 if(status==0)
   		 {session.setAttribute("message", "insufficient balance to transfer");}
   		 if(status==-2)
   		 {session.setAttribute("message", "problem in database");}
   		 if(status==3)
   		 {session.setAttribute("message", "beneficiary account not found");}
   		 if(status==4)
   		 {session.setAttribute("message", "problem in database");}
   		response.sendRedirect("view.jsp");
   	   }
   	   if(action.equals("print_statement"))
   	   {
   		int accno=(int)session.getAttribute("user");
   		   //accno=(int)session.getAttribute("accno");
   		   List<trans> list=new ArrayList<trans>();
   		   list=obj.ministmt(accno);
   		Collections.sort(list,new comparetoken());
   		List<trans> list2=new ArrayList<trans>();
   		for(int i=0;i<5 && i<list.size();i++)
   		{
   			list2.add(list.get(i));
   		}
   		session.setAttribute("list", list2);
   		response.sendRedirect("viewstmt.jsp");
   		   
   	   }
   	   if(action.equals("change_password"))
   	   {
   		   //int accno=Integer.parseInt(request.getParameter("accno"));
   		   int user=(int)session.getAttribute("user");
   		   String oldpassword=request.getParameter("pass");
   		   String newpassword=request.getParameter("cnf_newpass");
   		   int status=obj.updatepassword(user,oldpassword,newpassword);
   		   if(status==1)
   		   {
   			   session.setAttribute("message","password updated");
   		   }
   		   if(status==0)
   		   {
   			   session.setAttribute("message","wrong password");
   		   }
   		   if(status==-2)
   		   {
   			   session.setAttribute("message", "problem in database");
   		   }
   		   if(status==-1)
   		   {
   			   session.setAttribute("message","user not found");
   		   }
   		response.sendRedirect("view.jsp");
   	   }
   	if(action.equals("check_balance"))
	   {
		   //int accno=Integer.parseInt(request.getParameter("accno"));
   			int accno=(int)session.getAttribute("user");
		   int balance=obj.retrivebalance(accno);
		   System.out.println(balance);
		   if(balance==-1)
		   {
			   session.setAttribute("message", "user not found");
			
		   }
		   if(balance==-2)
		   {
			   session.setAttribute("message", "problem with the database");
			   
		   }
		   else
		   {
		   session.setAttribute("message", balance);
		   }
		   response.sendRedirect("view.jsp");
	   }
   	   if(action.equals("deposit"))
   	   {
   		int accno=Integer.parseInt(request.getParameter("accno"));
   		int amt=Integer.parseInt(request.getParameter("amount"));
   		int status=obj.addbalance(accno,amt);
   		if(status==1)
   		{
   			message="amount deposited";
   			
   		}
   		if(status==-1)
   		{
   			message="user not found";
   		}
   		if(status==0)
   		{
   			message="unable to update due to problem in db";
   		}
   		session.setAttribute("message", message);
		 response.sendRedirect("view.jsp");
   	   }
   	 if(action.equals("withdraw"))
 	   {
   		int accno=(int)session.getAttribute("user");
 		
 		int amt=Integer.parseInt(request.getParameter("amount"));
 		int status=obj.removebalance(accno,amt);
 		if(status==1)
 		{
 			message="amount deducted";
 			
 		}
 		if(status==-1)
 		{
 			message="user not found";
 		}
 		if(status==0)
 		{
 			message="insufficient balance";
 		}
 		if(status==-2)
 		{
 			message="problem in db";
 		}
 		session.setAttribute("message", message);
		 response.sendRedirect("view.jsp");
 	   }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int authenticated=0;
		String errorMessage;
		HttpSession session=request.getSession();
		int user=Integer.parseInt(request.getParameter("user"));
		String pass=request.getParameter("pass");
		authenticated=obj.authenticateuser(user,pass);
		session.setAttribute("user", user);
		if(authenticated==1)
			response.sendRedirect("usermenu.jsp");
		else if(authenticated==0)
		{
			errorMessage="wrong username/password";
			session.setAttribute("errorMessage", errorMessage);
			response.sendRedirect("error.jsp");
		}
		else if(authenticated==-1)
		{
			errorMessage="problem with database";
			session.setAttribute("errorMessage", errorMessage);
			response.sendRedirect("error.jsp");
		}
	}

}
