package com.gcu.data;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.gcu.model.ProductModel;

import com.gcu.utility.DatabaseException;


/**
 * Date: 02/08/2022
 * DAO for the Product Database.
 * Will be used to communicate between the Product Business Layer and the Database.
 * 
 * @author Michael Mohler
 * @version 1
 */
@Service
public class ProductDataService implements DataAccessInterface<ProductModel>, ProductDataAccessInterface<ProductModel> 
{
	//Logger for logging to console and file
	private static final Logger logger = LoggerFactory.getLogger(ProductDataService.class);

	//Initialize the Data Source and JDBC
	@SuppressWarnings("unused")
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	
	/**
	 * Constructor
	 * Set up the Data Source when service is called.
	 * 
	 * @param dataSource Used to setup the JDBC Template 
	 * 
	 */
	public ProductDataService(DataSource dataSource)
	{
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	
	/**
	 * Returns a list of all the product in a database that matches the user's id.
	 * 
	 * @param id that determines what products are pulled.
	 * 
	 * @return List<productModel> List of Products
	 */
	@Override
	public List<ProductModel> findUser(int id) {

		
		//SQL String that creates a view of every row that matches the user's id. 
		String sql = "SELECT * FROM product WHERE USERID = '" + id + "'";
		logger.info("SQL string is: " + sql);
		
		//Initialize the list for the Product Models
		List<ProductModel> products = new ArrayList<ProductModel>();
		

		//Try to make a list of products
		try
		{
			//For every row in the table make a product and add it to a list.
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);
			while(srs.next())
			{
				products.add(new ProductModel(srs.getInt("IDPRODUCT"),
														srs.getInt("USERID"),
														srs.getString("BOOK_NAME"),
														srs.getString("BOOK_GENRE"),
														srs.getString("BOOK_AUTHOR"),
														srs.getFloat("PRICE"),
														srs.getInt("QUANTITY"),
														srs.getString("BOOK_DES")));		
			}	
			
			logger.info("Product list created for find");
			return products;
		}
		catch (Exception e)
		{
			//Throw Database error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down.");
		}
	}


	
	/**
	 * Create a product for the database. Returns an int so a
	 * proper error message can be displayed.
	 * 
	 * @param productModel Used to add product to database 
	 * 
	 * @return int Used to determine what to do
	 */
	@Override
	public int create(ProductModel productModel) 
	{
		
		//SQL string that inserts the variables for the product class.
		String sql = "INSERT INTO `product` (`IDPRODUCT`, `USERID`, `BOOK_AUTHOR`, `BOOK_NAME`, `PRICE`, `QUANTITY`, `BOOK_GENRE`, `BOOK_DES`) VALUES (NULL, '"
												+ productModel.getUserId() + "', '" 
												+ productModel.getBookAuthor() + "', '" 
												+ productModel.getBookName() + "', '" 
												+ productModel.getPrice() + "', '" 
												+ productModel.getQuantity() + "', '"
												+ productModel.getBookGenre() + "', '" 
												+ productModel.getBookDescription() + "')";
		
		
		logger.info("SQL string is: " + sql);
		
		//Try to create the product in the database. 
		//Return 0 if update is successful, 1 if there was an error in the database.
		try
		{
			//Adds the product to the database
			logger.info("Product Created");
			jdbcTemplateObject.update(sql);
			return 0; 
		}
		catch(Exception e)
		{
			
			//Throw Database error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down. Adding product was unsuccessful");
		}
	}

	
	/**
	 * Change a product from the database. Returns an int so a
	 * proper error message can be displayed.
	 * 
	 * @param productModel Used to change product from database 
	 * 
	 * @return int Used to determine what to do
	 */
	@Override
	public int update(ProductModel productModel) 
	{
		//SQL string that inserts the variables for the product class.
		String sql = "UPDATE `product` SET "
				+ "`BOOK_AUTHOR`='" + productModel.getBookAuthor() + 
				"',`BOOK_NAME`='" + productModel.getBookName() +
				"',`BOOK_GENRE`='" + productModel.getBookGenre() +
				"',`PRICE`='" + productModel.getPrice() + 
				"', `QUANTITY`='" + productModel.getQuantity() + 
				"',`BOOK_DES`='" + productModel.getBookDescription()+
				"' WHERE IDPRODUCT = " + productModel.getProductId() +
				" AND USERID = " + productModel.getUserId() + ";";
		logger.info("SQL string is: " + sql);
		
		//Try to update the product in the database. 
		//Return 0 if update is successful, 1 if there was an error in the database.
		try
		{
			//Adds the product to the database
			logger.info("Product Updated");
			jdbcTemplateObject.update(sql);
			return 0; 
		}
		catch(Exception e)
		{
			
			//Throw Database error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down. Updating the product was unsuccessful");
		}
	}

	
	/**
	 * Delete the product from the database.
	 * 
	 * @param productModel Used to find out which product to delete from database
	 * 
	 * @return int Used to determine what happened
	 */
	@Override
	public int delete(ProductModel productModel) 
	{
		
		String sql = "DELETE FROM `product` WHERE IDPRODUCT = " + productModel.getProductId() + " AND USERID = " + productModel.getUserId() + ";";
		logger.info("SQL string is: " + sql);
		
		try
		{
			//If one row is effected then the product was deleted. If not, throw DatabaseException.
			if(jdbcTemplateObject.update(sql) == 1)
			{	
				logger.info("Product Deleted");
				return 0; 
			}
			else
			{
				logger.warn("Product not deleted");
				return 1;
				
			}
		}
		catch(Exception e)
		{
			//Throw Database error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down. Deleting product was unsuccessful");
		}
	}


	/**
	 * Search product in database
	 * 
	 * @param productModel Holds UserId properly, but also uses a type as a search term.
	 * 
	 * @return int Used to determine what happened
	 */
	@Override
	public List<ProductModel> findBySearchTerm(ProductModel productModel) 
	{
		//Pull the information from the productModel so it is easier to read.
		String searchTerm = productModel.getBookName();
		
		
		//SQL String that creates a view that searches every column
		String sql = "SELECT * FROM product WHERE USERID = (SELECT USERID FROM user WHERE USERNAME = 'Admin') AND (BOOK_NAME LIKE '%" + searchTerm + "%' OR BOOK_AUTHOR LIKE '%" + searchTerm + "%' "
				+ "OR BOOK_GENRE LIKE '%" + searchTerm + "%' OR BOOK_DES LIKE '%"+ searchTerm + "%');";
		
		logger.info("SQL string is: " + sql);
		
		//Initialize the list for the Product Models
		List<ProductModel> products = new ArrayList<ProductModel>();
		
		//Try to make a list of products
		try
		{
				
			//For every row in the table make a product and add it to a list.
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);
			while(srs.next())
			{
				products.add(new ProductModel(srs.getInt("IDPRODUCT"),
															srs.getInt("USERID"),
															srs.getString("BOOK_NAME"),
															srs.getString("BOOK_GENRE"),
															srs.getString("BOOK_AUTHOR"),
															srs.getFloat("PRICE"),
															srs.getInt("QUANTITY"),
															srs.getString("BOOK_DES")));		
			}	
			
			logger.info("Products list created for search");
			return products;
		}
		catch (Exception e)
		{
			
			//Throw Error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down. Products could not be searched");

		}
	}



	/**
	 * Pulls every product in the database from Admin
	 * 
	 * @return List of Product of every product found in database.
	 */
	@Override
	public List<ProductModel> findAllProducts() 
	{
		
		//SQL String that creates a view of every row that matches the user's id. 
		String sql = "SELECT * FROM product WHERE USERID = (SELECT USERID FROM user WHERE USERNAME = 'Admin')";
		
		logger.info("SQL string is: " + sql);
		
		//Initialize the list for the Product Models
		List<ProductModel> products = new ArrayList<ProductModel>();
		
		
		//Try to make a list of products
		try
		{
			//For every row in the table make a product and add it to a list.
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);
			while(srs.next())
			{
				products.add(new ProductModel(srs.getInt("IDPRODUCT"),
											srs.getInt("USERID"),
											srs.getString("BOOK_NAME"),
											srs.getString("BOOK_GENRE"),
											srs.getString("BOOK_AUTHOR"),
											srs.getFloat("PRICE"),
											srs.getInt("QUANTITY"),
											srs.getString("BOOK_DES")));		
			}	
			
			logger.info("Products list created for find all");
			return products;
		}
		catch (Exception e)
		{
			//Throw Database error
			logger.error("Database Error");
			throw new DatabaseException("The Database is currently down.");
		}
	}
}
