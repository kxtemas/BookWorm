package com.gcu.business;


import com.gcu.model.RegisterModel;

/**
 * Date: 02/05/2022
 * Interface for the Register service pages
 * 
 * @author Michael Mohler
 * @version 1
 */
public interface UserBusinessServiceInterface 
{
	public int processRegister(RegisterModel registerModel);

}
