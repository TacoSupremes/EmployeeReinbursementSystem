package com.revature.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Request 
{
	// use boolean object true is pending, false is approved, null is rejected. 
	private int requestID;
	private Boolean pending;
	private String reason;
	private BigDecimal amount;
	private String imageURI;
	private Timestamp created;
	private Timestamp reviewed;
	//maybe use EmployeeObject
	private int managerID;
	private int employeeID;
	
	// retrieving from the database
	public Request(Boolean pending, int employeeID, int managerID, String reason, BigDecimal amount, String imageURI, Timestamp reviewed, Timestamp created, int requestID) 
	{
		this.requestID = requestID;
		this.pending = pending;
		this.employeeID = employeeID;
		this.managerID = managerID;
		this.reason = reason;
		this.amount = amount;
		this.imageURI = imageURI;
		this.created = created;
		this.reviewed = reviewed;
	}

	// for inserting into the database
	public Request(Boolean pending, int employeeID, int managerID, String reason, BigDecimal amount, String imageURI)
	{
		this.pending = pending;
		this.employeeID = employeeID;
		this.managerID = managerID;
		this.reason = reason;
		this.amount = amount;
		this.imageURI = imageURI;
	}

	public int getRequestID()
	{
		return requestID;
	}

	public Boolean getPending()
	{
		return pending;
	}

	public String getReason()
	{
		return reason;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public String getImageURI()
	{
		return imageURI;
	}

	public Timestamp getCreated()
	{
		return created;
	}

	public Timestamp getReviewed()
	{
		return reviewed;
	}

	public int getManagerID()
	{
		return managerID;
	}

	public int getEmployeeID()
	{
		return employeeID;
	}

	public void setPending(Boolean pending)
	{
		this.pending = pending;
	}

	public void setReviewed()
	{
		this.reviewed = Timestamp.valueOf(LocalDateTime.now());
	}
}
