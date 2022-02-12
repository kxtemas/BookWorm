package com.gcu.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 * Date:02/05/2022
 * A website with a single vendor that sells books to users.
 * 
 * @author Michael Mohler
 * @version 1.
 */
@ComponentScan({"com.gcu"})
@SpringBootApplication
public class BookShelf 
{

	public static void main(String[] args) 
	{
		SpringApplication.run(BookShelf.class, args);
	}

}
