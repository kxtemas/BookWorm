package com.gcu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.business.ProductlBusinessInterface;
import com.gcu.model.ProductModel;

import org.springframework.ui.Model;

/**
 * Date: 02/08/2022
 * Controller for searching products in the database
 * 
 * Users can use actions that allow for editing, deleting, getting details on each product.
 * 
 * @author Michael Mohler
 * @since 02/07/2022
 * @version 1.
 *
 */
@Controller
@RequestMapping("/search")
public class SearchController 
{
	
	//The list of products that will be displayed to the user.
	private List<ProductModel> products = new ArrayList<ProductModel>();
	@Autowired
	private ProductlBusinessInterface service;
	
	
	/**
	 * Displays the main search page to the user
	 * 
	 * @param model Used to add attributes to the model page.
	 * @param session Controls session data throughout the site
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/")
	public String display(Model model, HttpSession session)
	{
		//Clear list to be used for search function
		List<ProductModel> products = new ArrayList<ProductModel>();
		
		//Display Search Form View
		model.addAttribute("title", "Search Product");
		model.addAttribute("products", products);
		model.addAttribute("productModel", new ProductModel());
		
		return "search";
	}
	
	
	/**
	 * Post method for searching a users product. Takes users
	 * back to same page with table filed out. 
	 * 
	 * @param productModel from form. Used to send information from the form to the Product DAO. Type is the search term
	 * @param bindingResults for error page
	 * @param model for page attributes
	 * @param session data to use for users id.
	 * 
	 * @return String that is the name of the HTML page.
	 */
	@PostMapping("/doSearch")
	public String doSearch(ProductModel productModel, Model model, HttpSession session)
	{
		//New Product is made to add variables to more easily get from the 
		ProductModel newProduct = productModel;
		
		//Pull user ID from session and add to the product model
		int id = (int)session.getAttribute("id");
		newProduct.setUserId(id);
		
		//Pull the searchTerm from the Book Name variable
		String searchTerm = productModel.getBookName();	
		
		//If user enters nothing then prevent their entire product list from showing
		if(searchTerm == "")
		{
			//Make sure no product is shown
			products = new ArrayList<ProductModel>();
			
			//Add error message to tell user to enter something
			String errorMessage = "Please enter something in the search bar.";
			model.addAttribute("products", products);
			model.addAttribute("errorMessage", errorMessage);
			return "search";
		}
		
		//Call service and try to find searched item
		products = service.displaySearchedProduct(newProduct);
		
		//If nothing is found
		if (products.isEmpty())
		{
			//Make sure no product is shown
			products = new ArrayList<ProductModel>();
			
			//Add error message to tell user to enter something
			String errorMessage = "No Products were found. Please try narrowing your search.";
			model.addAttribute("products", products);
			model.addAttribute("errorMessage", errorMessage);
		}

				
		//Take the user back to the Product page with attributes.
		model.addAttribute("title", "Search Product");
		model.addAttribute("products", products);
		model.addAttribute("productModel", new ProductModel());
		return "search";


	}
	
}
