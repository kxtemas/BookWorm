package com.gcu.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.gcu.business.UserBusinessService;
import com.gcu.model.LoginModel;
import com.gcu.model.RegisterModel;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

/**
 * Date: 02/05/2022
 * Controller for the Register page. Let's user register to the site and takes them to login page.
 * 
 * @author Michael Mohler
 * @version 1
 *
 */
@Controller
@RequestMapping("/register")
public class RegisterController 
{
	
	//Initialize the Business service for the Register Module
	@Autowired
	private UserBusinessService service;
	

	/**
	 * Displays the main register page to the user
	 * 
	 * @param model Used to add attributes to the model page.
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/")
	public String display(Model model)
	{
		
		//Display Register Form View
		model.addAttribute("title", "");
		model.addAttribute("registerModel", new RegisterModel());
		return "register";
	}
	
	/**
	 * Post method that checks for validation. If data is valid add user to database
	 * and forwards to login page. If not valid go back to register page.
	 * 
	 * @param registerModel Object model of the user profile
	 * @param bindingResult Displays errors to the user
	 * @param model Used to add attributes to the model's page
	 * 
	 * @return String of the page name the use will be taken to
	 */
	@PostMapping("/doRegister")
	public String doRegister(@Valid RegisterModel registerModel, BindingResult bindingResult, Model model)
	{
	
		//Attempts to register to the website. If there are validation errors or email/username is already registered stay on register page.
		//Otherwise register and go to the login page.
		
		//Check for Validation
		if(bindingResult.hasErrors())
		{
			model.addAttribute("title", "Register Form");
			model.addAttribute("registerModel", registerModel);
			return "register";
		}
		else
		{
		
			String errorMessage; //For customizing error messages
			int registerNumber = service.processRegister(registerModel); //We only want to call the service once so save the int code here.
			
			//Checks if 
			if(registerNumber == 0)
			{
				
				//Take the user to the login page to use their new credentials 
				model.addAttribute("title", "Login Form");
				model.addAttribute("loginModel", new LoginModel());
				return "login";
			}
			else if (registerNumber == 1)
			{
				errorMessage = "Username or Email is already taken";
			}
			else if (registerNumber == 2)
			{
				errorMessage = "Cannot Access Database";
			}
			else if (registerNumber == 3)
			{
				errorMessage = "Error in Server";
			}
			else
			{
				errorMessage = "Error in Application";
			}
			
			//Keep the user on the page to try different credentials. 
			model.addAttribute("title", "Register Form");
			model.addAttribute("errorMessage", errorMessage);
			return "register";
			
		}
		

	}
	
}
