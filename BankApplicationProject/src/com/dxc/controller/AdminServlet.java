package com.dxc.controller;
import com.dxc.dao.admindaoimpl;
import java.util.*;



import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.dxc.pojos.bankusers;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       admindaoimpl obj=new admindaoimpl();
       
   
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action="";
 	   String temp=request.getParameter("adminbtn");
 	   if(temp!=null)
 		   action=temp;
 	   else
 		   action=request.getParameter("adminbtn2");
 	  HttpSession session=request.getSession();
 	  if(action.equals("update_customer"))
 	  {
 		 int accno=Integer.parseInt(request.getParameter("accno"));
 		 String password=request.getParameter("password");
 		 String result=obj.updateuser(accno, password);
 		 session.setAttribute("message", result);
 		 response.sendRedirect("view.jsp");
 	  }
 	  if(action.equals("show_all_customers"))
 	  {
 		  List<bankusers> list=obj.getallbankusers();
 		  session.setAttribute("list", list);
 		  response.sendRedirect("viewcust.jsp");
 	  }
 	   if(action.equals("add_customer"))
 	   {
 		  int accno=Integer.parseInt(request.getParameter("accno"));
			String password=request.getParameter("password");
			int balance=Integer.parseInt(request.getParameter("balance"));
			
			bankusers s1=new bankusers(accno, password, balance);
			obj.adduser(s1);
			String message="Student added successfully!";
			session.setAttribute("message", message);
			response.sendRedirect("view.jsp");
 	   }
 	   if(action.equals("remove_customer"))
 	   {
 		   int accno=Integer.parseInt(request.getParameter("accno"));
 		   int result=obj.removeuser(accno);
 		   String message;
 		   if(result==1)
 			   message="deleted successfully";
 		   else if(result==-1)
 			   message="usernot found";
 		   else 
 			   message="problem in db";
 		   session.setAttribute("message", message);
 		   response.sendRedirect("view.jsp");
 			   
 	   }
 	   if(action.equals("show_customer"))
 	   {
 		   int accno=Integer.parseInt(request.getParameter("accno"));
 		   List<bankusers> list=new ArrayList<>();
 		   list=obj.retrieveuser(accno);
 		  session.setAttribute("list", list);
 		  response.sendRedirect("viewcust.jsp");
 		   
 	   }
 	   if(action.equals("get_cust_bal"))
 	   {
 		   int accno=Integer.parseInt(request.getParameter("accno"));
 		   int balance=obj.retrivebalance(accno);
 		   session.setAttribute("message", balance);
 		   response.sendRedirect("view.jsp");
 	   }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean authenticated=false;
		String errorMessage;
		HttpSession session=request.getSession();
		int admin=Integer.parseInt(request.getParameter("id"));
		String pass=request.getParameter("pass");
		authenticated=obj.authenticateadmin(admin,pass);
		if(authenticated==true)
			response.sendRedirect("adminmenu.jsp");
		else
		{
			errorMessage="Invalid login!";
			session.setAttribute("errorMessage", errorMessage);
			response.sendRedirect("error.jsp");
		}
	}

}
