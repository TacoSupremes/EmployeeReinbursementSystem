package com.revature.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.revature.model.Employee;
import com.revature.model.EmployeeAndManager;
import com.revature.model.Request;
import com.revature.model.RequestWithEmployeeManager;
import com.revature.model.RequestWithEmployeeName;
import com.revature.repository.EmployeeRepository;
import com.revature.repository.Repository;
import com.revature.utility.ConnectionFactory;

public class EmployeeService 
{
	private Repository<Employee> employeeRepo;
	
	public EmployeeService()
	{
		employeeRepo = new EmployeeRepository(ConnectionFactory.getConnection());
	}
	
	public void insert(Employee e)
	{
		employeeRepo.insert(e);
	}
	
	public Employee getEmployeeByEmail(String s)
	{
		if(s == null)
			return null;
		
		for(Employee e : getAllEmployees())
		{
			if(e.getEmail().equals(s))
				return e;
		}
	
		return null;
	}
	
	public Employee getEmployeeByEmailAndPassword(String user, String password)
	{
		if(user == null || password == null)
			return null;
		
		for(Employee e : getAllEmployees())
		{
			if(e.getEmail().equals(user) && e.getPassword().equals(password))
				return e;
		}
	
		return null;
	}

	public List<Employee> getAllEmployees()
	{
		return employeeRepo.getAll();
	}
	
	public Employee getEmployeeById(int num)
	{
		for(Employee e : getAllEmployees())
			if(e.getAccount_Num() == num)
				return e;
		
		return null;
	}
	
	public List<Employee> getAllEmployeesSortedByID() 
	{
		List<Employee> l = getAllEmployees();
		
		l.sort((a, b) -> a.getAccount_Num() - b.getAccount_Num());
		
		return l;
	}

	public void delete(Employee e) 
	{
		employeeRepo.delete(e);	
	}

	public void update(Employee e)
	{
		employeeRepo.update(e);
	}

	public List<Employee> getAllManagers()
	{
		return getAllEmployees().stream().filter(e -> e.isManager()).collect(Collectors.toList());
	}
	
	public List<Employee> getAllNonManagers()
	{
		List<Employee> all = getAllEmployees();
		all.removeAll(getAllManagers());
		
		return all;
	}

	public List<RequestWithEmployeeName> getEmployeeNameForRequests(List<Request> lr)
	{
		List<RequestWithEmployeeName> l = new ArrayList<>();
		
		for(Request r : lr)
		{
			l.add(new RequestWithEmployeeName(r, getEmployeeById(r.getEmployeeID())));
		}
		
		return l;
	}

	public List<RequestWithEmployeeManager> getEmployeeManagerNameForRequests(List<Request> lr)
	{
		List<RequestWithEmployeeManager> l = new ArrayList<RequestWithEmployeeManager>();
		
		for(Request r : lr)
		{
			l.add(new RequestWithEmployeeManager(r, getEmployeeById(r.getEmployeeID()), getEmployeeById(r.getManagerID())));
		}
		
		return l;
	}

	// returns the manager with the least amount of employees assigned to them
	public int getAssignedManager()
	{
		int manager_id = -1;
		int best = Integer.MAX_VALUE;
		
		for(Employee man : getAllManagers())
		{
			int count = 0;
			
			for(Employee e : getAllEmployees())
			{
				if(e.getManager_Num() == man.getAccount_Num())
					count++;
			}
			
			if(count < best)
			{
				best = count;
				manager_id = man.getAccount_Num();
			}
		}
		
		return manager_id;
	}


	public List<EmployeeAndManager> getAllEmployeesAndManager()
	{
		List<EmployeeAndManager> eam = new ArrayList<>();
		for(Employee e : getAllEmployees())
		{
			eam.add(new EmployeeAndManager(e, getEmployeeById(e.getManager_Num())));
		}
		return eam;
	}
	
	
}
