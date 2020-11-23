package com.revature.model;

public class Employee extends AuthBase
{
	private boolean manager;
	private int manager_num;

	public Employee(String last_name, String first_name, String username, String password, boolean manager, int manager_num, int account_num)
	{
		super(last_name, first_name, username, password, account_num);
		this.manager = manager;
		this.manager_num = manager_num;
	}
	
	public Employee(String last_name, String first_name, String username, String password, boolean manager, int manager_num)
	{
		super(last_name, first_name, username, password);
		this.manager = manager;
		this.manager_num = manager_num;
	}

	public boolean isManager() 
	{
		return manager;
	}

	public int getManager_Num()
	{
		return manager_num;
	}
}
