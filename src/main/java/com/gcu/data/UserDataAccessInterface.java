package com.gcu.data;



/**
 * Date: 02/05/2022
 * Interface to be used by User Data Service to specifically check for the user database table
 * @author Michael M.
 * @version 1
 */
public interface UserDataAccessInterface<T>
{
	public T findByUsername(String username);
}
