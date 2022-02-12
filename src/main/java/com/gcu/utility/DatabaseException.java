package com.gcu.utility;

/**
 * Date: 02/05/2022
 * 
 * Custom Exception to handle database errors for user and product database. 
 * 
 * @author Michael Mohler
 *
 */
public class DatabaseException extends RuntimeException 
{

	//Initialize variables
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	
	
	/**
	 * Default Constructor 
	 */
	public DatabaseException() 
	{
		
	}


	/**
	 * Parameterized Constructor 
	 * 
	 * @param errorMessage The message that will be displayed to the user
	 */
	public DatabaseException(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	
	//Getters and setters
	public String getErrorMessage() 
	{
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) 
	{
		this.errorMessage = errorMessage;
	}


	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}



	
	
	
}
