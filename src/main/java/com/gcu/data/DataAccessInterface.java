package com.gcu.data;

import java.util.List;



/**
 * Date: 02/05/2022
 * Interface to be used by the Product Data Service and User Data Service
 * @author Michael Mohler
 * @version 1
 */
public interface DataAccessInterface<T>
{
	public List<T> findUser(int id);
	public int create(T t);
	public int update(T t);
	public int delete(T t);
}
