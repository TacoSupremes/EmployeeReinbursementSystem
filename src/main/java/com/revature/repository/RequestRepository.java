package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Request;

public class RequestRepository implements Repository<Request>
{
	private Connection connection;
	
	// DO NOT MANIPULATE DATA HERE THIS IS ONLY FOR ACCESS
	
	public RequestRepository(Connection connection)
	{
		this.connection = connection;
	}
	
	@Override
	public void insert(Request type)
	{
		String SQL = "insert into requests values(?, ?, ?, ?, ?, ?)";
		
		try
		{
			PreparedStatement ps = connection.prepareStatement(SQL);
			ps.setObject(1, type.getPending());
			ps.setInt(2, type.getEmployeeID());
			ps.setInt(3, type.getManagerID());
			ps.setString(4, type.getReason());
			ps.setBigDecimal(5, type.getAmount());
			ps.setString(6, type.getImageURI());

			ps.executeUpdate();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<Request> getAll()
	{
		// create new list
		List<Request> l = new ArrayList<>();
				
		// get all records from users table
		String SQL = "select * from requests;";
		
		try
		{
			PreparedStatement ps = connection.prepareStatement(SQL);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				l.add(new Request(
								 rs.getObject(1, Boolean.class), rs.getInt(2),rs.getInt(3), rs.getString(4), 
								 rs.getBigDecimal(5), rs.getString(6), rs.getTimestamp(7), rs.getTimestamp(8), rs.getInt(9))
								 );
			}
			
			rs.close();
			ps.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return l;
	}

	@Override
	public void update(Request type)
	{
		String SQL = "update requests set pending=?, employee_id=?, manager_id=?, reason=?, amount=?, image_uri=?, reviewed=?, created=? where request_id=?";
		
		try
		{
			PreparedStatement ps = connection.prepareStatement(SQL);
			ps.setObject(1, type.getPending());
			ps.setInt(2, type.getEmployeeID());
			ps.setInt(3, type.getManagerID());
			ps.setString(4, type.getReason());
			ps.setBigDecimal(5, type.getAmount());
			ps.setString(6, type.getImageURI());
			ps.setTimestamp(7, type.getReviewed());
			ps.setTimestamp(8, type.getCreated());
			
			ps.setInt(9, type.getRequestID());
			
			ps.executeUpdate();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Request type)
	{
		final String SQL = "delete from requests where request_id=" + type.getRequestID();
		
		try
		{
			PreparedStatement ps = connection.prepareStatement(SQL);
			
			ps.executeUpdate();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
