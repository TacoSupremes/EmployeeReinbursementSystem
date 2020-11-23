package com.revature.model.test;

import org.junit.Assert;
import org.junit.Test;

import com.revature.model.Employee;

public class AuthBaseTest 
{
	@Test
	public void testUserAndPasswordEqual()
	{
		Employee u = new Employee("Chief", "Master", "chief", "123456", false, 0, 1);
		
		Employee u2 = new Employee("Chief", "Master", "chief", "123456", false, 0, 1);
		
		Assert.assertEquals(u.equalUserAndPassword(u2), true);
		
		Assert.assertEquals(u.equalUserAndPassword(u), true);		
	}
}
