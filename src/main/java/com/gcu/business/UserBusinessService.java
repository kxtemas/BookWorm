package com.gcu.business;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.gcu.data.DataAccessInterface;

import com.gcu.model.RegisterModel;


/**
 * Date: 02/05/2022
 * Business Layer Service for the Register portion of the website.
 * Will be used to communicate between the register controller and the database.
 * 
 * @author Michael Mohler
 * @version 1
 */
public class UserBusinessService implements UserBusinessServiceInterface 
{

	//For the logger
	private static final Logger logger = LoggerFactory.getLogger(UserBusinessService.class);
	
	@Autowired
	private DataAccessInterface<RegisterModel> service;
	
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	/**
	 * Creates a user by calling the DAO. Encrypts the password first
	 * 
	 * @param registerModel Used to add details of user to database 
	 * 
	 * @return int Used to determine what to do
	 */
	@Override
	public int processRegister(RegisterModel registerModel) 
	{
		RegisterModel newRegister = registerModel;
		newRegister.setPassword(passwordEncoder.encode(registerModel.getPassword()));
		
		int infoNumber = service.create(newRegister);	
		
		logger.info("Info Number is " + infoNumber);
		return infoNumber;
	}
	

}
