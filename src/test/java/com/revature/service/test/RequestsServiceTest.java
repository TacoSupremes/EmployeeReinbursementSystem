package com.revature.service.test;

import java.math.BigDecimal;
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
import com.revature.repository.RequestRepository;
import com.revature.service.RequestsService;

public class RequestsServiceTest
{
	@InjectMocks
	private static RequestsService requestsService;
	
	@Mock
	private RequestRepository requestRepo;
	
	private List<Request> testRequests = Arrays.asList(
			new Request(true, 4, 2, "Reason3", BigDecimal.valueOf(18), "linktopic", null, null, 1),
			new Request(true, 2, 1, "Reason", BigDecimal.valueOf(15.23), "linktopic", null, null, 2),
			new Request(true, 2, 1, "Reason2", BigDecimal.valueOf(18), "linktopic"));
	
	
	@BeforeClass
	public static void setUp() 
	{
		requestsService = new RequestsService();
	}

	@Before
	public void before() 
	{
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetAllRequests()
	{
		Mockito.when(requestRepo.getAll()).thenReturn(testRequests);
		List<Request> l = requestsService.getAll();
		Assert.assertEquals(l.get(0).getReason(), "Reason3");
		Assert.assertEquals(l.get(1).getReason(), "Reason");
		Assert.assertEquals(l.get(2).getReason(), "Reason2");
	}
	
	
	@Test
	public void testGetAllRequestsByEmployee()
	{
		Mockito.when(requestRepo.getAll()).thenReturn(testRequests);
		List<Request> l = requestsService.getRequestsByEmployee(new Employee("last", "first", "email", "pass", false, 1, 2));
		Assert.assertEquals(l.get(0).getReason(), "Reason");
		Assert.assertEquals(l.get(1).getReason(), "Reason2");
	}
	
	
	@Test
	public void testGetAllRequestsByManager()
	{
		Mockito.when(requestRepo.getAll()).thenReturn(testRequests);
		List<Request> l = requestsService.getPendingRequestsByManager(new Employee("last", "first", "email", "pass", true, 4, 1));
		Assert.assertEquals(l.get(0).getReason(), "Reason");
		Assert.assertEquals(l.get(1).getReason(), "Reason2");
	}
	
	@Test
	public void testApproveRequest()
	{
		Mockito.when(requestRepo.getAll()).thenReturn(testRequests);
		Request r = testRequests.get(0);
		requestsService.approveRequest(r);
		Assert.assertEquals(r.getPending(), false);
	}
	
	@Test
	public void testDenyRequest()
	{
		Mockito.when(requestRepo.getAll()).thenReturn(testRequests);
		Request r = testRequests.get(0);
		requestsService.denyRequest(r);
		Assert.assertEquals(r.getPending(), null);
	}
	
	@Test
	public void testTimeStampRequest()
	{
		Mockito.when(requestRepo.getAll()).thenReturn(testRequests);
		Request r = testRequests.get(0);
		requestsService.timeStampRequest(r);
		Assert.assertNotEquals(r.getReviewed(), null);
	}
	
	@Test
	public void testgetRequestById()
	{
		Mockito.when(requestRepo.getAll()).thenReturn(testRequests);
		Request r = requestsService.getRequestsById(1);
		requestsService.timeStampRequest(r);
		Assert.assertEquals(r.getReason(), "Reason3");
	}
	
}
