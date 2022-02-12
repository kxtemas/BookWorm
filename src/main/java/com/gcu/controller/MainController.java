package com.gcu.controller;





import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;


/**
 * Date: 02/05/2022
 * Controller for the main page. This is the first page a user should see when accessing the site. 
 * It is here to inform the user that they need to login or register and are given
 * buttons with actions to take them to those pages.
 * 
 * @author Michael Mohler
 * @version 1
 *
 */
@Controller
@RequestMapping("/")
public class MainController 
{
	/**
	 * Displays the main page to the user
	 * 
	 * @param model Used to add attributes to the model page.
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/")
	public String display(Model model)
	{

		//Display Main Form View
		model.addAttribute("title", " Welcome to our Book Shelf");
		return "main";

	}
	
	
	/**
	 * Takes user to the login page
	 * 
	 * @param model Unused
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/goLogin")
	public String goLogin(Model model)
	{
		//Try to go to the login page
		try
		{
			//Forwards to the display method in the login controller
			return "forward:/login/";
		}
		catch(Exception e)
		{
			
			//Take user to an error page
			return "error";
		}
	}
	
	/**
	 * Takes user to the register page
	 * 
	 * @param model Unused
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/goRegister")
	public String goRegister(Model model)
	{

		//Forwards to the display method in the register controller
		return "forward:/register/";

	}
	
	/**
	 * Logs the user out and destroys their session data
	 * 
	 * @param session Session data that will be removed
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/logout")
	public String logout(HttpSession session)
	{
		//Try to go to logout user and take them main page
		try
		{
			//Removes the username and id session
			session.removeAttribute("username");
			session.removeAttribute("id");
			//Forwards to the display method in the register controller
			return "redirect:/";
		}
		catch(Exception e)
		{
			//Take user to an error page
			return "redirect:/error";
		}
	}
	
}
