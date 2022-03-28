package com.gcu.controller;




import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.business.ProductlBusinessInterface;
import com.gcu.model.ProductModel;

import org.springframework.ui.Model;


/**
 * Date: 02/08/2022
 * Controller for the Home page. Will be the page users are taken to after logging in.
 * From here the user will be able to see the most recent products added to their database
 * and can also go to most of the pages seen on the navbar. Such as the product page, search, and random. 
 * 
 * @author Michael Mohler
 * @version 1
 *
 */
@Controller
@RequestMapping("/cart")
public class CartController 
{
	//For the logger
	private static final Logger logger = LoggerFactory.getLogger(CartController.class);
	
	//The list of products that will be displayed to the user.
	private List<ProductModel> product = new ArrayList<ProductModel>();
	
	
	@Autowired
	private ProductlBusinessInterface service;
	
	
	/**
	 * Displays the main cart page to the user
	 * 
	 * @param model Used to add attributes to the model page.
	 * @param session Used to get the ID of the user to display utensil names
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/")
	public String display(Model model, HttpSession session)
	{

		int id = (int)session.getAttribute("id");
		
		logger.info("User id is " + id);
		
		product = service.displayUserProducts(id);
		
		//Display cart Page View
		model.addAttribute("title", "My Cart");
		model.addAttribute("products", product);
		
		return "cart";
	}
	
	
	/**
	 * Takes user to the case page
	 * 
	 * @param model Unused
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/doShelf")
	public String doShelf(Model model)
	{
			
		//Display Title
		model.addAttribute("title", "Book Shelf");
		
		//Go to shelf page. Uses forward so the products list on the shelf page is used.
		return "forward:/shelf/";	

	}
}
