package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Employee;

public class EmployeeRepository implements Repository<Employee>
{
	private Connection connection;
	
	//private static UserRepositoryImpl instance = new UserRepositoryImpl(); 
	
	// DO NOT MANIPULATE DATA HERE THIS IS ONLY FOR ACCESS
	
	public EmployeeRepository(Connection connection)
	{
		this.connection = connection;
	}
	
	@Override
	public void insert(Employee user) 
	{
		String SQL = "insert into employees values(?, ?, ?, ?, ?, ?)";
		
		try 
		{
			PreparedStatement ps = connection.prepareStatement(SQL);
			ps.setString(1, user.getLast_Name());
			ps.setString(2, user.getFirst_Name());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setBoolean(5, user.isManager());
			ps.setInt(6, user.getManager_Num());
			ps.executeUpdate();
			ps.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Employee> getAll()
	{
		// create new list
		List<Employee> l = new ArrayList<>();
		
		// get all records from users table
		String SQL = "select * from employees;";
		
		try
		{
			PreparedStatement ps = connection.prepareStatement(SQL);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				l.add(new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getInt(6), rs.getInt(7)));
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
	public void update(Employee toUpdate)
	{
		String SQL = "update employees set last_name=?, first_name=?, email=?, password=?, manager=?, manager_num=? where account_num=?";
		
		try
		{
			PreparedStatement ps = connection.prepareStatement(SQL);
			ps.setString(1, toUpdate.getLast_Name());
			ps.setString(2, toUpdate.getFirst_Name());
			ps.setString(3, toUpdate.getEmail());
			ps.setString(4, toUpdate.getPassword());
			ps.setBoolean(5, toUpdate.isManager());
			ps.setInt(6, toUpdate.getManager_Num());
			ps.setInt(7, toUpdate.getAccount_Num());
			
			ps.executeUpdate();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Employee type)
	{
		String SQL = "delete from employees where account_num=" + type.getAccount_Num();
		
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

	@Deprecated
	public Employee getByUsername(String s) 
	{
		String SQL = "select * from employees where user_name=?";
		
		try
		{
			PreparedStatement ps = connection.prepareStatement(SQL);
			ps.setString(1, s);
			ResultSet rs  = ps.executeQuery();
			
			while(rs.next())
			{
				Employee e = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getInt(6));
				rs.close();
				ps.close();
				return e;
			}
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}

	@Deprecated
	public Employee getByUsernameAndPassword(String user, String password) 
	{
		String SQL = "select * from employees where user_name=? and password=?";
		
		try
		{
			PreparedStatement ps = connection.prepareStatement(SQL);
			ps.setString(1, user);
			ps.setString(2, password);
			ResultSet rs  = ps.executeQuery();
			
			while(rs.next())
			{
				Employee e = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getInt(6));
				rs.close();
				ps.close();
				return e;
			}
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
