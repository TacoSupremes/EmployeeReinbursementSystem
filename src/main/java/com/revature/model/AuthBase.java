package com.revature.model;

/** Base class that stores last_name, first_name, username, password, account_num*/
public class AuthBase 
{
	private String last_name;
	private String first_name;
	private String email;
	private String pass;
	protected final int account_num;
	
	public AuthBase(String last_name, String first_name, String email, String pass, int account_num)
	{
		this.account_num = account_num;
		this.last_name = last_name;
		this.first_name = first_name;
		this.email = email;
		this.pass = pass;
	}
	
	public AuthBase(String last_name, String first_name, String email, String pass)
	{
		this.account_num = -1;
		this.last_name = last_name;
		this.first_name = first_name;
		this.email = email;
		this.pass = pass;
	}
	
	/**Check if this other AuthBase is equal to this AuthBase*/
	public boolean equalUserAndPassword(AuthBase u)
	{
		return getEmail().equals(u.getEmail()) && getPassword().equals(u.getPassword());
	}

	public String getLast_Name() 
	{
		return last_name;
	}

	public String getFirst_Name() 
	{
		return first_name;
	}

	public String getEmail() 
	{
		return email;
	}

	public String getPassword()
	{
		return pass;
	}

	public int getAccount_Num()
	{
		return account_num;
	}

	public void setLast_name(String last_name)
	{
		this.last_name = last_name;
	}

	public void setFirst_name(String first_name)
	{
		this.first_name = first_name;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setPass(String pass)
	{
		this.pass = pass;
	}
}
