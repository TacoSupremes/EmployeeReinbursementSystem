package com.revature.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.revature.model.AuthBase;
import com.revature.model.Employee;
import com.revature.model.EmployeeAndManager;
import com.revature.model.Request;
import com.revature.model.RequestWithEmployeeManager;
import com.revature.model.RequestWithEmployeeName;
import com.revature.service.EmployeeService;
import com.revature.service.RequestsService;

public class RequestHelper
{
	private static EmployeeService employeeService = new EmployeeService();
	private static RequestsService requestsService = new RequestsService();
	
	private static File folder = new File("C:\\Users\\zach\\uploads");
	
	public static Object processGet(HttpServletRequest request, HttpServletResponse response) 
	{		
		final String URL = request.getRequestURI();
		System.out.println(URL);
			
		final String RESOURCE = URL.replace("/ExpenseReimbursementSystem/api", "");
		System.out.println(RESOURCE);
			
		switch(RESOURCE) 
		{
			case "/userInfo":
				return getUserInfo(request, response);			
			case "/userRequests":
				return getUserRequests(request, response);
			case "/managerRequests":
				return getManagerRequests(request, response);
			case "/allRequests":
				return getAllRequests(request, response);
			case "/allUsers":
				return getAllEmployees(request, response);
			default:
				return "No such endpoint or resource";
		}	
	}
	
	private static List<EmployeeAndManager> getAllEmployees(HttpServletRequest request, HttpServletResponse response)
	{
		return employeeService.getAllEmployeesAndManager();
	}

	private static List<RequestWithEmployeeManager> getAllRequests(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		
		if(session != null)
		{
			Employee e = employeeService.getEmployeeByEmail((String)session.getAttribute("user email"));
			
			if(e != null)
			{
				List<Request> lr = requestsService.getAll();
				List<RequestWithEmployeeManager> l = employeeService.getEmployeeManagerNameForRequests(lr);
				return l;
			}
		}
		
		return null;
	}

	private static List<RequestWithEmployeeName> getManagerRequests(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		
		if(session != null)
		{
			Employee e = employeeService.getEmployeeByEmail((String)session.getAttribute("user email"));
			
			if(e != null)
			{
				List<Request> lr = requestsService.getPendingRequestsByManager(e);
				List<RequestWithEmployeeName> lw = employeeService.getEmployeeNameForRequests(lr);	
				return lw;
			}
		}
		
		return null;
	}

	public static void getReciept(HttpServletRequest request, HttpServletResponse response, String filename)
	{
        File file = new File(folder, filename);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        
        try
		{
			Files.copy(file.toPath(), response.getOutputStream());
		}
        catch (IOException e)
		{
			e.printStackTrace();
		}	
	}

	private static Employee getUserInfo(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		
		if(session != null)
			return employeeService.getEmployeeByEmail((String)session.getAttribute("user email"));
		
		return null;
	}
	
	private static List<Request> getUserRequests(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		
		if(session != null)
		{
			Employee e = employeeService.getEmployeeByEmail((String)session.getAttribute("user email"));
			
			if(e != null)
				return requestsService.getRequestsByEmployee(e);
		}
		
		return null;
	}

	public static void processPost(HttpServletRequest request, HttpServletResponse response) 
	{	
		final String URL = request.getRequestURI();
		System.out.println(URL);
		
		final String RESOURCE = URL.replace("/ExpenseReimbursementSystem/api", "");
		System.out.println(RESOURCE);
		
		switch(RESOURCE) 
		{
			case "/login":
				handleLogin(request, response);
				break;
				
			case "/logout":
				handleLogout(request, response);
				break;
				
			case "/updateUserInfo":
				handleUpdate(request, response);
				break;
				
			case "/submitRequest":
				handleSubmitRequest(request, response);
				break;
				
			case "/approveRequest":
				handleApproveDenyRequest(request, response, true);
				break;
				
			case "/denyRequest":
				handleApproveDenyRequest(request, response, false);
				break;
				
			case "/newUser":
				handleNewUser(request, response);
				break;
				
		}
	}
	

	private static void handleNewUser(HttpServletRequest request, HttpServletResponse response)
	{	
		String lastName = request.getParameter("lastname");
		String firstName = request.getParameter("firstname");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		String man = request.getParameter("manager");
		
		//System.out.println(man);
		
		boolean manager = man != null;
		
		Employee e = new Employee(lastName, firstName, email, password, manager, employeeService.getAssignedManager());
		
		employeeService.insert(e);
		
		try
		{
			response.sendRedirect("../views/manager.html");
		} 
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private static void handleApproveDenyRequest(HttpServletRequest request, HttpServletResponse response, boolean approve)
	{
		Scanner scan = null;
		try
		{
			scan = new Scanner(request.getReader());
			int id = scan.nextInt();
			
			Request r = requestsService.getRequestsById(id);
			
			if(approve)
				requestsService.approveRequest(r);
			else
				requestsService.denyRequest(r);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(scan != null)
				scan.close();
		}
	}

	private static void handleSubmitRequest(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		
		if(session != null)
		{
			Employee e = employeeService.getEmployeeByEmail((String)session.getAttribute("user email"));
			
			if(e != null)
			{
				String reason = request.getParameter("reason"); 
						
			    BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(request.getParameter("amount")));
				
			    System.out.println(reason);
			    System.out.println(amount);
			    
			    Part filePart;
				
			    try
				{
					filePart = request.getPart("file");
					String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
					InputStream fileContent = filePart.getInputStream();
				
					System.out.print(fileName);
 
					File f = new File(folder, fileName);
					
					Files.copy(fileContent, f.toPath());
					
					Request r = new Request(true, e.getAccount_Num(), e.getManager_Num(), reason, amount, fileName);
					
					requestsService.insert(r);	
					
					response.sendRedirect("../views/requests.html");
				} 
				catch (IOException | ServletException ex)
				{
					ex.printStackTrace();
				} 
			}
		}
	}

	private static void handleUpdate(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		
		if(session != null)
		{
			Employee e = employeeService.getEmployeeByEmail((String)session.getAttribute("user email"));
			
			if(e != null)
			{
				try
				{
					String JSON = request.getReader().lines().collect(Collectors.joining());
					AuthBase ab = new Gson().fromJson(JSON, AuthBase.class);
					
					e.setEmail(ab.getEmail());
					if(ab.getPassword() != null && !ab.getPassword().equals("undefined"))
						e.setPass(ab.getPassword());
					e.setFirst_name(ab.getFirst_Name());
					e.setLast_name(ab.getLast_Name());
					
					employeeService.update(e);
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}

	static void handleLogin(HttpServletRequest request, HttpServletResponse response)
	{
		final String email = request.getParameter("email");
		final String pass = request.getParameter("password");

		Employee e = employeeService.getEmployeeByEmailAndPassword(email, pass);
		
		if(e != null)
		{	
			System.out.println("VALID USER");
			
			HttpSession session = request.getSession();
			
			//You can create session attributes for convenience.
			session.setAttribute("user email", email);
			session.setAttribute("user id", e.getAccount_Num());
			session.setAttribute("user name", e.getFirst_Name());
			session.setAttribute("user isManager", e.isManager());
			try
			{
				response.sendRedirect("../views/home.html");
			} 
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		else
		{
			try
			{
				response.sendRedirect("../index.html");
			} 
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}
	
	private static void handleLogout(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);

		if (session != null) 
			session.invalidate();
		
			try
			{
				response.sendRedirect("../index.html");
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}

}
