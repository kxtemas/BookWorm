package com.gcu.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.gcu.utility.DatabaseException;

/**
 * Date: 02/05/2022
 * Global Exception handler that will handle the exceptions and take the user to an error page.
 * 
 * 
 * @author Michael Mohler.
 * @since 02/05/2022
 * @version 1.
 *
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler 
{
	//For the logger
	private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
	
	
	/**
	 * Handler for DatabaseExceptions. Will take user to error page.
	 * 
	 * @param ex Custom exception class that stores details from thrown
	 * exceptions. 
	 * 
	 * @return ModelAndView determines model attributes of the next page
	 */
	@ExceptionHandler(DatabaseException.class)
	public ModelAndView handleDatabaseException(DatabaseException ex)
	{
		
		//Create ModelAndView
		ModelAndView model = new ModelAndView();
		
		//Add the Title, error message, and determine what page to go to. 
		model.addObject("title", "Database Error");
		model.addObject("errorMessage", "Error: " + ex.getErrorMessage());
		model.setViewName("error");
		
		logger.error("Error: + " + ex.getErrorMessage());
		
		return model;
	}
	
	/**
	 * Handler for any exception. Will take user to error page with default message.
	 * 
	 * @return ModelAndView determines model attributes of the next page
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException()
	{
		//Create ModelAndView
		ModelAndView model = new ModelAndView();
		
		
		//Add the Title, error message, and determine what page to go to. 
		model.addObject("title", "Unknown Error");
		model.addObject("errorMessage", "Error: An unidentified error has occurred.");
		model.setViewName("error");
		
		logger.error("Error: An unidentified error has occurred.");
		
		return model;
	}
}
