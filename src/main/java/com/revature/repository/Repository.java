package com.revature.repository;

import java.util.List;

/*called repository since it handles data access to the database*/

public interface Repository<T> 
{
	void insert(T type);
	
	List<T> getAll();
	
	void update(T type);
	
	void delete(T type);

}
