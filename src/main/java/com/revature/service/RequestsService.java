package com.revature.service;

import java.util.List;
import java.util.stream.Collectors;

import com.revature.model.Employee;
import com.revature.model.Request;
import com.revature.repository.RequestRepository;
import com.revature.utility.ConnectionFactory;

public class RequestsService
{
	private RequestRepository requestRepo;

	public RequestsService()
	{
		requestRepo = new RequestRepository(ConnectionFactory.getConnection());
	}
	
	public List<Request> getAll()
	{
		return requestRepo.getAll();
	}
	
	public List<Request> getRequestsByEmployee(Employee e)
	{
		List<Request> all = getAll();
		
		List<Request> l = all.stream().filter(e2 -> e2.getEmployeeID() == e.getAccount_Num()).collect(Collectors.toList());
		
		return l;
	}
	
	public List<Request> getPendingRequestsByManager(Employee e)
	{
		List<Request> all = getAll();
		
		List<Request> l = all.stream().filter(e2 -> (e2.getManagerID() == e.getAccount_Num() && e2.getPending() != null && e2.getPending())).collect(Collectors.toList());
		
		return l;
	}

	public void insert(Request r)
	{
		requestRepo.insert(r);
	}
	
	public void approveRequest(Request r)
	{
		r.setPending(false);
		timeStampRequest(r);
	}
	
	public void denyRequest(Request r)
	{
		r.setPending(null);
		timeStampRequest(r);
	}
	
	public void timeStampRequest(Request r)
	{
		r.setReviewed();
		requestRepo.update(r);
	}

	public Request getRequestsById(int id)
	{
		List<Request> requests = getAll();
		
		for(Request r : requests)
			if(r.getRequestID() == id)
				return r;
		
		return null;
	}
}
