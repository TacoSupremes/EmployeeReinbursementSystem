package com.revature.service.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revature.model.Employee;
import com.revature.model.Request;
import com.revature.model.RequestWithEmployeeManager;
import com.revature.model.RequestWithEmployeeName;
import com.revature.repository.EmployeeRepository;
import com.revature.service.EmployeeService;


public class EmployeeServiceTest 
{
	@InjectMocks
	private static EmployeeService employeeService;
	
	@Mock
	private EmployeeRepository employeeRepo;

	private List<Employee> testEmployees = Arrays.asList(
			new Employee("Savage", "21", "savagemode2@gmail.com", "123456", false, 1, 2),
			new Employee("Drake", "Aubrey", "drake2@gmail.com", "123456", false, 1, 4),
			new Employee("Bezos", "Jeff", "bigmoneyjeff@gmail.com", "123456", true, 2, 1),
			new Employee("Man", "Monopoly", "admin@gmail.com", "123456", true, 2, 3));
	
	@BeforeClass
	public static void setUp() 
	{
		employeeService = new EmployeeService();
	}

	@Before
	public void before() 
	{
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetAllEmployees()
	{
		Mockito.when(employeeRepo.getAll()).thenReturn(testEmployees);
		List<Employee> l = employeeService.getAllEmployees();
		Assert.assertEquals(l.get(0).getFirst_Name(), "21");
		Assert.assertEquals(l.get(1).getFirst_Name(), "Aubrey");
		Assert.assertEquals(l.get(2).getFirst_Name(), "Jeff");
		Assert.assertEquals(l.get(3).getFirst_Name(), "Monopoly");
	}
	
	@Test
	public void testGetAllEmployeesSortedByID()
	{
		Mockito.when(employeeRepo.getAll()).thenReturn(testEmployees);
		List<Employee> l = employeeService.getAllEmployeesSortedByID();
		
		Assert.assertEquals(l.get(0).getFirst_Name(), "Jeff");
		Assert.assertEquals(l.get(1).getFirst_Name(), "21");
		Assert.assertEquals(l.get(2).getFirst_Name(), "Monopoly");
	}
	
	@Test
	public void testGetEmployeeByID()
	{
		Mockito.when(employeeRepo.getAll()).thenReturn(testEmployees);
		Employee e = employeeService.getEmployeeById(2);
		Assert.assertEquals(e.getFirst_Name(), "21");
	} 
	
	
	@Test
	public void testGetEmployeeByEmail()
	{
		Mockito.when(employeeRepo.getAll()).thenReturn(testEmployees);
		Employee e = employeeService.getEmployeeByEmail("savagemode2@gmail.com");
		Assert.assertEquals(e.getFirst_Name(), "21");
	}
	
	@Test
	public void testGetEmployeeByEmailAndPassword()
	{
		Mockito.when(employeeRepo.getAll()).thenReturn(testEmployees);
		Employee e = employeeService.getEmployeeByEmailAndPassword("admin@gmail.com", "123456");
		Assert.assertEquals(e.getFirst_Name(), "Monopoly");
	}
	
	@Test
	public void testGetAllManagers()
	{
		Mockito.when(employeeRepo.getAll()).thenReturn(testEmployees);
		Employee e = employeeService.getAllManagers().get(0);
		Assert.assertEquals(e.getFirst_Name(), "Jeff");
	}
	
	@Test
	public void testGetEmployeeNameForRequests()
	{
		Mockito.when(employeeRepo.getAll()).thenReturn(testEmployees);
		
		List<Request> r = Arrays.asList(new Request(true, 2, 1, "Reason", BigDecimal.valueOf(15.23), "linktopic"));
		
		 RequestWithEmployeeName o = employeeService.getEmployeeNameForRequests(r).get(0);
		Assert.assertEquals(o.getEmployeeName(), "21 Savage");
	}
	
	@Test
	public void testgetEmployeeManagerNameForRequests()
	{
		Mockito.when(employeeRepo.getAll()).thenReturn(testEmployees);
		
		List<Request> r = Arrays.asList(new Request(true, 2, 1, "Reason", BigDecimal.valueOf(15.23), "linktopic"));
		
		RequestWithEmployeeManager o = employeeService.getEmployeeManagerNameForRequests(r).get(0);
		Assert.assertEquals(o.getManagerName(), "Jeff Bezos");
		Assert.assertEquals(o.getEmployeeName(), "21 Savage");
	}
	
	@Test
	public void testgetAssignedManager()
	{
		Mockito.when(employeeRepo.getAll()).thenReturn(testEmployees);
		
		int i = employeeService.getAssignedManager();
		
		//Returns 3 because the other admin has 2 people to manage while this manager only has 1
		
		Assert.assertTrue(i == 3);
	}
	
	@Test
	public void testgetAllManagers()
	{
		Mockito.when(employeeRepo.getAll()).thenReturn(new ArrayList<>(testEmployees));
		List<Employee> l = employeeService.getAllManagers();
	
		Assert.assertEquals(l.get(0).getFirst_Name(), "Jeff");
		Assert.assertEquals(l.get(1).getFirst_Name(), "Monopoly");
	}
	
	
	@Test
	public void testgetAllNonManagers()
	{
		Mockito.when(employeeRepo.getAll()).thenReturn(new ArrayList<>(testEmployees));
		List<Employee> l = employeeService.getAllNonManagers();
		
		Assert.assertEquals(l.get(0).getFirst_Name(), "21");
		Assert.assertEquals(l.get(1).getFirst_Name(), "Aubrey");
	}
	
	
}
