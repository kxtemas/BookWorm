package com.gcu.business;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import com.gcu.data.DataAccessInterface;
import com.gcu.data.ProductDataAccessInterface;
import com.gcu.model.ProductModel;

/**
 * Date: 02/07/2022
 * Business Layer Service for the Product portion of the website.
 * Used to communicate between the Product controller and the DAO.
 * 
 * @author Michael Mohler
 * @version 1
 */
public class ProductBusinessService implements ProductlBusinessInterface 
{

	@Autowired
	private DataAccessInterface<ProductModel> service;
	
	@Autowired
	private ProductDataAccessInterface<ProductModel> serviceProduct;
	

	/**
	 * Create a product by calling DAO. Returns an int so a
	 * proper error message can be displayed.
	 * 
	 * @param productModel Used to add product
	 * 
	 * @return int Used to determine what to do
	 */
	@Override
	public int insertProduct(ProductModel productModel) 
	{

		//Call Service and return a number based on the result
		return service.create(productModel);


	}

	
	/**
	 * Returns a list of all the product in the user's cart by calling the DAO service
	 * 
	 * @param id that determines what products are pulled.
	 * 
	 * @return List<ProductModel> List of Products
	 */
	@Override
	public List<ProductModel> displayUserProducts(int id) 
	{

		//Return service
		return service.findUser(id);

	}
	

	/**
	 * Change a product by calling the DAO. Returns an int so a
	 * proper error message can be displayed.
	 * 
	 * @param productModel Used to change product
	 * 
	 * @return int Used to determine what to do
	 */
	@Override
	public int changeProduct(ProductModel productModel) 
	{

		//Call Service and return a number based on the result
		return service.update(productModel);

	}
	
	
	/**
	 * Delete the product by calling DAO service
	 * 
	 * @param productModel Used to find out which product to delete
	 * 
	 * @return int Used to determine what happened
	 */
	@Override
	public int eraseProduct(ProductModel productModel) 
	{

		
		//Call Service and return a number based on the result
		return service.delete(productModel);
	}
	
	
	/**
	 * Search product by calling DAO service
	 * 
	 * @param productModel carries the search time and userid for the search
	 * 
	 * @return int Used to determine what happened
	 */
	@Override
	public List<ProductModel> displaySearchedProduct(ProductModel productModel)
	{
	
		return serviceProduct.findBySearchTerm(productModel);
		
	}
	
	
	/**
	 * Calls DAO to pull every product from database.
	 * 

	 * @return List of products. 
	 */
	@Override
	public List<ProductModel> getEveryProduct() 
	{
		return serviceProduct.findAllProducts();
	}

}
