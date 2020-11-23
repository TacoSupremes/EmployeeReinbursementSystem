package com.revature.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory 
{
	private static Connection connection;
	
	/** Returns a Connection object pointing to the PostgresSQL server */
	public static Connection getConnection()
	{
		if(connection != null)
			return connection;
		
		String url = System.getenv("dburllocal");
		String user = System.getenv("dbuserlocal");
		String pass = System.getenv("dbuserpass");
		
		try 
		{
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, pass);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			
			e.printStackTrace();
		}
		
		return connection;
	}
	
	// close connection stream
	public static void close()
	{
		try
		{
			connection.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
