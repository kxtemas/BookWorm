package com.gcu.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Date: 02/05/22
 * Model to handle data for each book. Every variable is private and getters and setters are used for each one.
 * Validation is on all variables but product and user id.
 * 
 * @author Michael Mohler
 * @version 1.
 *
 */
public class ProductModel 
{
	private int productId;
	
	private int userId;
	
	@NotNull(message="Name is a required field")
	@Size(min=3, max=50, message="Type must be between 1 and 50 characters")	
	private String bookName;
	
	@NotNull(message="Genre is a required field")
	@Size(min=3, max=45, message="Brand must be between 3 and 45 characters")
	private String bookGenre;
	
	@NotNull(message="Author is a required field")
	@Size(min=3, max=20, message="Color must be between 3 and 20 characters")
	private String bookAuthor;
	
	@NotNull(message="Price is a required field")
	private float price;
	
	@NotNull(message="Quantity is a required field")
	private int quantity;
	
	@NotNull(message="Description is a required field")
	@Size(min=4, max=100, message="Size must be between 4 and 100 characters")
	private String bookDescription;

	
	/**
	 * Parameterized Constructor
	 * 
	 * @param poductId Id of product in database
	 * @param userId Id of user in database
	 * @param bookName Name of book
	 * @param bookGenre Genre of book
	 * @param bookAuthor Author of book
	 * @param price Price of book
	 * @param quantity Quantity of the book
	 * @param bookDescription A description of the book
	 */
	public ProductModel(int productId, int userId,
			@NotNull(message = "Name is a required field") @Size(min = 3, max = 50, message = "Type must be between 1 and 32 characters") String bookName,
			@NotNull(message = "Genre is a required field") @Size(min = 3, max = 45, message = "Brand must be between 3 and 20 characters") String bookGenre,
			@NotNull(message = "Author is a required field") @Size(min = 3, max = 20, message = "Color must be between 3 and 20 characters") String bookAuthor,
			@NotNull(message = "Price is a required field") float price,
			@NotNull(message = "Quantity is a required field") int quantity,
			@NotNull(message = "Description is a required field") @Size(min = 4, max = 100, message = "Size must be between 4 and 20 characters") String bookDescription) 
	{
		super();
		this.productId = productId;
		this.userId = userId;
		this.bookName = bookName;
		this.bookGenre = bookGenre;
		this.bookAuthor = bookAuthor;
		this.price = price;
		this.quantity = quantity;
		this.bookDescription = bookDescription;
	}


	
	/**
	 * Default constructor
	 */
	public ProductModel() 
	{
		this.productId = 0;
		this.userId = 0;
		this.bookName = "Name";
		this.bookGenre = "Genre";
		this.bookAuthor = "Author";
		this.price = 1.00f;
		this.quantity = 1;
		this.bookDescription = "Description";
	}


	/**
	 * Returns a string of all the information from the product. Used for easier logging.
	 */
	public String toString()
	{
		String info = "Book Name: " + bookName
				+ " Book Genre: " + bookGenre
				+ " Book Author: " + bookAuthor
				+ " Price: " + price
				+ " Quantity: " + quantity
				+ " Book Description: " + bookDescription;
		
		return info;
	}
	
	//Getters and Setters
	

	public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getBookName() {
		return bookName;
	}


	public void setBookName(String bookName) {
		this.bookName = bookName;
	}


	public String getBookGenre() {
		return bookGenre;
	}


	public void setBookGenre(String bookGenre) {
		this.bookGenre = bookGenre;
	}


	public String getBookAuthor() {
		return bookAuthor;
	}


	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getBookDescription() {
		return bookDescription;
	}


	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}
	
	

	
	
	
}	