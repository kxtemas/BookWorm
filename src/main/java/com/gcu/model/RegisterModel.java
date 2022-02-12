package com.gcu.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Date: 02/05/2022
 * Model to handle data for registering and possible User data. Each variable is private and getters and setters are used for each one.
 * Extends Login model so it can also use id, username, and password.
 * 
 * @author Michael Mohler
 * @version 1.
 *
 */
public class RegisterModel
{

	@NotNull(message="First name is a required field")
	@Size(min=2, max=12, message="A first name can only be between 2 and 15 characters")
	private String firstName;
	
	@NotNull(message="Last name is a required field")
	@Size(min=2, max=12, message="A last name can only be between 2 and 15 characters")
	private String lastName;
	
	@NotNull(message="Email is a required field")
	@Size(min=7, max=45, message="An email can only be between 7 and 50 characters")
	private String email;
	
	
	@Valid
	private LoginModel loginCred = new LoginModel();
	
	
	/**
	 * Parameterized Constructor for Registered users. Uses login model for other varaibles.
	 * 
	 * @param firstName User's First Name
	 * @param lastName User's Last Name
	 * @param email User's email account used on the site
	 * @param phone User's Phone Number
	 * @param loginCred Login Model which contains id, username and password
	 */
	public RegisterModel(
			@NotNull(message = "First name is a required field") @Size(min = 2, max = 12, message = "A first name can only be between 2 and 15 characters") String firstName,
			@NotNull(message = "Last name is a required field") @Size(min = 2, max = 12, message = "A last name can only be between 2 and 15 characters") String lastName,
			@NotNull(message = "Email is a required field") @Size(min = 7, max = 50, message = "An email can only be between 7 and 50 characters") String email,
			@Valid LoginModel loginCred) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		
		this.loginCred = loginCred;
	}
	
	/**
	 * Default Constructor
	 */
	public RegisterModel()
	{
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		
		//Login Credentials being changed
		loginCred.setId(0);
		
		
		loginCred.setUsername("");
		loginCred.setPassword("");
		
	}

	//Getters and Setters for all the private variables. 
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LoginModel getLoginCred() {
		return loginCred;
	}

	public void setLoginCred(LoginModel loginCred) {
		this.loginCred = loginCred;
	}

	
	//Getters and Setters for loginCred fields
	public int getId() {
		return loginCred.getId();
	}
	public void setId(int id) {
		loginCred.setId(id);
	}
	public String getUsername() {
		return loginCred.getUsername();
	}
	public void setUsername(String username) {
		loginCred.setUsername(username);
	}
	public String getPassword() {
		return loginCred.getPassword();
	}
	public void setPassword(String password) {
		
		loginCred.setPassword(password);
	}



	
	
}