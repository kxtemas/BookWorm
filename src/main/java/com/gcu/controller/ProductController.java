package com.gcu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.business.ProductlBusinessInterface;
import com.gcu.model.ProductModel;
import com.gcu.utility.DatabaseException;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

/**
 * Date: 02/08/22
 * Controller for the shelf page, for displaying products. Will call a list of products from the database to display on the page.
 * Users can also add to the product database
 * 
 * In the future will have actions that allow for editing, deleting, getting details on each product.
 * 
 * @author Michael Mohler
 * @since 02/08/22
 * @version 1.
 *
 */
@Controller
@RequestMapping("/shelf")
public class ProductController 
{
	
	//The list of products that will be displayed to the user.
	private List<ProductModel> products = new ArrayList<ProductModel>();
	@Autowired
	private ProductlBusinessInterface service;
	
	
	/**
	 * Displays the main shelf page to the user
	 * 
	 * @param model Used to add attributes to the model page.
	 * @param session Controls session data throughout the site
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/")
	public String display(Model model, HttpSession session)
	{
	
		products = service.getEveryProduct();
		//Loads product list into the pages view. 
		model.addAttribute("title", "Book Shelf");
		model.addAttribute("products", products);
		model.addAttribute("productModel", new ProductModel());
		

		return "shelf";
	}
	
	
	/**
	 * Take user to the Add page for adding a product
	 * 
	 * @param model Used to add attributes to the model page.
	 * 
	 * @return String of the page name
	 */
	@GetMapping("/goAdd")
	public String goAdd(Model model)
	{
		
		//Display Add Form View
		model.addAttribute("title", "Add Product");
		model.addAttribute("productModel", new ProductModel());
		
		return "add";

	}
	
	
	/**
	 * Post method to go to update page with the same details as product that was clicked
	 * 
	 * @param productModel Object to forward details to update page
	 * @param model for page attributes
	 * @param binding Results for error page
	 * 
	 * @return Update page name string
	 */
	@PostMapping("/goUpdate")
	public String goUpdate(ProductModel productModel, Model model, BindingResult bindingResult)
	{
		
		//Display Update Form View
		model.addAttribute("title", "Update Product");
		model.addAttribute("productModel", productModel);
		

		return "update";

	}
	
	
	/**
	 * Take user to the Detailed page for showing all details of a product
	 * 
	 * @param model Used to add attributes to the model page.
	 * 
	 * @return String of the page name
	 */
	@PostMapping("/goDetail")
	public String goDetail(ProductModel productModel, Model model)
	{
		
		//Display Product
		model.addAttribute("title", "Detailed Product");
		model.addAttribute("productModel", productModel);

		return "detail";

	}
	
	
	/**
	 * Post method that checks for validation. If user is admin take user 
	 * to the shelf page with new product added. If not, then user to cart page with product added to their cart.
	 * 
	 * @param productModel from form
	 * @param bindingResults for error page
	 * @param model for page attributes
	 * @param session data to use for users id.
	 * 
	 * @return Update page name string
	 */
	@PostMapping("/doAdd")
	public String doAdd(@Valid ProductModel productModel, BindingResult bindingResult, Model model, HttpSession session)
	{
		ProductModel newProduct = productModel;
		int id = (int)session.getAttribute("id");
		newProduct.setUserId(id);
		
		//If no validation errors are found add to list and go to shelf page.
		//Check for Validation errors.
		if(bindingResult.hasErrors())
		{
			//If Errors were found take the user back to the add page.
			model.addAttribute("title", "Add Product");
			return "add";
		}
		else
		{
			//If there were no errors then try to add product to the product list
			
			String errorMessage; //Initialize error message
			//Call service once and save number to determine the action
			int productNumber = service.insertProduct(newProduct);
		
			//Based on number, either take user to shelf page or display error.
			if (productNumber == 0)
			{
				//If Admin go back to the Home Page
				if(session.getAttribute("username").equals("Admin"))
				{
					//Refresh the product list
					
					products = service.getEveryProduct();
					//Take the user back to the shelf page.
					model.addAttribute("title", "Book shelf");
					model.addAttribute("products", products);
					return "shelf";
				}
				else //If user go to the cart
				{
					//Refresh the product list
					products = service.displayUserProducts(id);
					//Take the user back to the shelf page.
					model.addAttribute("title", "My Cart");
					model.addAttribute("products", products);
					return "cart";
				}

			}
			else if (productNumber == 1)
			{
				errorMessage = "Cannot Access Database"; //Error Message

			}
			else if (productNumber == 2)
			{
				errorMessage = "Erorr in Server"; //Error Message

			}
			else
			{
				errorMessage = "Error in Application"; //Error Message
				
			}
			
			//If Errors were found take the user back to the add page.
			model.addAttribute("title", "Add Product");
			model.addAttribute("errorMessage", errorMessage);
			return "add";

		}

	}
	
	/**
	 * Post method that checks for validation when updating. If data is valid take user 
	 * to the shelf page. If not, take the user back to update page.
	 * 
	 * @param productModel from form
	 * @param bindingResults for error page
	 * @param model for page attributes
	 * @param session data to use for users id.
	 * 
	 * @return Update page name string
	 */
	@PostMapping("/doUpdate")
	public String doUpdate(@Valid ProductModel productModel, BindingResult bindingResult, Model model, HttpSession session)
	{
		ProductModel newProduct = productModel;
		int id = (int)session.getAttribute("id");
		newProduct.setUserId(id);

		//If no validation errors are found add to list and go to shelf page.

		//Check for Validation errors.
		if(bindingResult.hasErrors())
		{
			//If Errors were found take the user back to the add page.
			model.addAttribute("title", "Update Product");
			return "update";
		}
		else
		{
			//If there were no errors then try to add product to the product list
			
			String errorMessage; //Initialize error message
			//Call service once and save number to determine the action
			int productNumber = service.changeProduct(newProduct);
		
			//Based on number, either take user to shelf page or display error.
			if (productNumber == 0)
			{
				//Refresh the product list
				products = service.getEveryProduct();
				
				//Take the user back to the shelf page.
				model.addAttribute("title", "Book Shelf");
				model.addAttribute("products", products);
				return "shelf";
			}
			else if (productNumber == 1)
			{
				errorMessage = "Cannot Access Database"; //Error Message

			}
			else if (productNumber == 2)
			{
				errorMessage = "Erorr in Server"; //Error Message

			}
			else
			{
				errorMessage = "Error in Application"; //Error Message
				
			}
			
			//If Errors were found take the user back to the add page.
			model.addAttribute("title", "Update Product");
			model.addAttribute("errorMessage", errorMessage);
			return "update";

		}



	}
	
	/**
	 * Post method that checks for validation then deletes product. If user is admin take user 
	 * to the shelf page. If not, then take them to cart page.
	 * 
	 * @param productModel from form
	 * @param bindingResults for error page
	 * @param model for page attributes
	 * @param session data to use for users id.
	 * 
	 * @return Update page name string
	 */
	@PostMapping("/doDelete")
	public String doDelete(ProductModel productModel, Model model, HttpSession session)
	{
		ProductModel newProduct = productModel;
		int id = (int)session.getAttribute("id");
		newProduct.setUserId(id);
		

		//Call service once and save number to determine the action
		int productNumber = service.eraseProduct(newProduct);
	
		//Based on number, refresh shelf page
		if (productNumber == 0)
		{
			//If Admin go back to the Home Page
			if(session.getAttribute("username").equals("Admin"))
			{
				//Refresh the product list
				
				products = service.getEveryProduct();
				//Take the user back to the shelf page.
				model.addAttribute("title", "Book shelf");
				model.addAttribute("products", products);
				return "shelf";
			}
			else //If user go to the cart
			{
				//Refresh the product list
				products = service.displayUserProducts(id);
				//Take the user back to the shelf page.
				model.addAttribute("title", "My Cart");
				model.addAttribute("products", products);
				return "cart";
			}
			
		}
		else
		{
			throw new DatabaseException("Database is currently down. Could not delete product.");
		}
		
	}
}
