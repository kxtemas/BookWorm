package com.gcu.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;




import org.springframework.ui.Model;


/**
 * Date: 02/05/2022
 * Controller which handles the login page, security, and setting up the user's sessions.
 * 
 * 
 * @author Michael M.
 * @version 1.
 *
 */
@Controller
public class LoginController 
{

	
	
	/**
	 * Displays the main login page to the user
	 * 
	 * @param model Used to add attributes to the model page.
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/login")
	public String display(Model model)
	{

		//Display Login View
		model.addAttribute("title", "");
		return "login";

	}
	
	
	
}
