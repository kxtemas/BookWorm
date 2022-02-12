package com.gcu.data;

import java.util.List;


/**
 * Date: 02/07/2022
 * Interface to be used by Product Data Service to specifically check for the user database table
 * 
 * @author Michael Mohler
 * @version 1
 */
public interface ProductDataAccessInterface<T>
{
	public List<T> findBySearchTerm(T t);
	public List<T> findAllProducts();
}
