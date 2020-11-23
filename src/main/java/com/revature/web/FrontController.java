package com.revature.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class FrontController
 */
@MultipartConfig
public class FrontController extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     *
     *
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() 
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		if (request.getMethod().equals("GET"))
		{
			if(request.getRequestURI().contains("/recieptPhotos/"))
				RequestHelper.getReciept(request, response, request.getRequestURI().replace("/ExpenseReimbursementSystem/api/recieptPhotos/", ""));		
			else
				response.getWriter().write(new Gson().toJson(RequestHelper.processGet(request, response)));
		}
		else if(request.getMethod().equals("POST"))
		{
			RequestHelper.processPost(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
