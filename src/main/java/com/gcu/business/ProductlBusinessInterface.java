package com.gcu.business;

import java.util.List;

import com.gcu.model.ProductModel;

/**
 * Date: 02/07/22
 * Interface for the business layer of the Product Product
 * 
 * @author Michael Mohler
 * @version 1
 */
public interface ProductlBusinessInterface 
{
	public int insertProduct(ProductModel productModel);
	public List<ProductModel> displayUserProducts(int id);
	int changeProduct(ProductModel productModel);
	int eraseProduct(ProductModel productModel);
	public List<ProductModel> displaySearchedProduct(ProductModel productModel);
	public List<ProductModel> getEveryProduct();
}
