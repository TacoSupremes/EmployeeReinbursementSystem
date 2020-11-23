package com.revature.model;

public class RequestWithEmployeeName extends Request
{
	private String employeeName;
	
	public RequestWithEmployeeName(Request r, Employee e)
	{
		super(r.getPending(), r.getEmployeeID(), r.getManagerID(), r.getReason(), r.getAmount(), r.getImageURI(), r.getReviewed(), r.getCreated(), r.getRequestID());
		this.employeeName = e.getFirst_Name() + " " + e.getLast_Name();
	}

	public String getEmployeeName()
	{
		return employeeName;
	}
}
