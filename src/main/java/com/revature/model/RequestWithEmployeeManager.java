package com.revature.model;

public class RequestWithEmployeeManager extends RequestWithEmployeeName
{
	private String managerName;
	
	public RequestWithEmployeeManager(Request r, Employee e, Employee m)
	{
		super(r, e);
		this.managerName = m.getFirst_Name() + " " + m.getLast_Name();
	}

	public String getManagerName()
	{
		return managerName;
	}

}
