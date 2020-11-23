package com.revature.model;

public class EmployeeAndManager extends Employee
{
	private String managerName;
	
	private String employeeName;

	public EmployeeAndManager(Employee e, Employee man)
	{
		super(e.getLast_Name(), e.getFirst_Name(), e.getEmail(), e.getPassword(), e.isManager(), man.getAccount_Num(), e.getAccount_Num());
		this.managerName = man.getFirst_Name() + " " + man.getLast_Name();
		this.employeeName = e.getFirst_Name() + " " + e.getLast_Name();
	}

	public String getEmployeeName()
	{
		return employeeName;
	}

	public String getManagerName()
	{
		return managerName;
	}

}
