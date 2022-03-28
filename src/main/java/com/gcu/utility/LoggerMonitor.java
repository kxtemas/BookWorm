package com.gcu.utility;



import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;



/**
 * Advise class that will log a method when it is called and when it ends
 * 
 * @author Michael M
 *
 */
@Aspect
@Component
public class LoggerMonitor
{
	//Logger for logging to console and file
	private static final Logger logger = LoggerFactory.getLogger("Book Worm");
	
	@Pointcut("execution(* com.gcu..controller..*(..)) || execution(* com.gcu..business..*(..)) || execution(* com.gcu..data..*(..))")
	public void monitor()
	{
	}
	
	/**
	 * Calls methods from monitor before and after they are called so they can be logged.
	 * @param point
	 * @return object of point
	 * @throws Throwable
	 */
	@Around("monitor()")
	public Object applicationLogger(ProceedingJoinPoint point) throws Throwable 
	{
		
		//Titles of the class and methods
		String classTitle = point.getTarget().getClass().toString();
		String methodTitle = point.getSignature().getName();
		
		//Time related variables
		long start = System.currentTimeMillis();
		long end;
		long time;
		
		//Log when the class and method are called
		logger.info("Called " + classTitle + " : " + methodTitle );
		Object object = point.proceed();
		
		//Find how long it took finish method
		end = System.currentTimeMillis();
		time = end - start;
		
		//Log when the class and method have stopped
		logger.info("Ended " + classTitle + " : " + methodTitle + " in " + time + " ms");
		return object;
	}
}