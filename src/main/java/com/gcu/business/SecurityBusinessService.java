 package com.gcu.business;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gcu.data.UserDataAccessInterface;
import com.gcu.model.RegisterModel;


/**
 * Date: 02/05/2022
 * Business Layer Service for the Login portion of the website.
 * This is used communicate between the Login controller and the DAO.
 * 
 * @author Michael M.
 * @version 2
 */
public class SecurityBusinessService implements SecurityBusinessServiceInterface,  UserDetailsService 
{

	@Autowired
	private UserDataAccessInterface<RegisterModel> service;
	
	//Needed to set the session variables
	@Autowired 
	 private HttpSession session;
	
	
	
	/**
	 * Search the username in the Database by calling the DAO. If it exists grant authority and
	 * set the session for the user so they application can work like it did before the security.
	 * 
	 * @param username String that holds the username the security is trying to search
	 * @return User model with the username, password, and role of the user.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		RegisterModel user = service.findByUsername(username);
		
		//If user exists then send them back with granted authority
		if(user != null)
		{
			
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("USER"));
			
			//Add session for username and Id
			session.setAttribute("username", user.getUsername());
			session.setAttribute("id", user.getId());
			
			//Return UserDetails back to security
			return new User(user.getUsername(), user.getPassword(), authorities);
		}
		else
		{
			//Throw Error, currently does not work. 
			throw new UsernameNotFoundException("Username not found");
		}
	}

}