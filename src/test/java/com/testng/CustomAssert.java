package com.testng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
 
public class CustomAssert
{
	private static Logger log = LogManager.getLogger("Verify");
	private static Map<ITestResult, List<Throwable>> verificationFailuresMap = new HashMap<ITestResult, List<Throwable>>();

	public static void fail(String message) 
	{
		log.error(message);
	    Assert.fail(message+"</br>");
	}

	private static void assertTrue(boolean condition)
    {
    	Assert.assertTrue(condition);
    }

	private static void assertTrue(boolean condition, String message)
    {
    	Assert.assertTrue(condition, message);
    }

	private static void assertFalse(boolean condition)
    {
    	Assert.assertFalse(condition);
    }

	private static void assertFalse(boolean condition, String message)
    {
    	Assert.assertFalse(condition, message);
    }

	private static void assertEquals(boolean actual, boolean expected)
    {
    	Assert.assertEquals(actual, expected);
    }
    
    public static void assertEquals(boolean actual, boolean expected, String message) 
    {
    	Assert.assertEquals(actual, expected, message);
    }

	private static void assertEquals(Object actual, Object expected)
    {
    	Assert.assertEquals(actual, expected);
    }
    
    public static void assertEquals(Object actual, Object expected, String message) 
    {
    	Assert.assertEquals(actual, expected, message);
    }

	private static void assertEquals(Object[] actual, Object[] expected)
    {
    	Assert.assertEquals(actual, expected);
    }
    
    public static void assertEquals(Object[] actual, Object[] expected, String message) 
    {
    	Assert.assertEquals(actual, expected, message);
    }
    
    public static void verifyTrue(boolean condition) 
    {
    	try 
    	{
    		assertTrue(condition);
    	} 
    	catch(Throwable e) 
    	{
    		addVerificationFailure(e);
    	}
    }
    
    public static void verifyTrue(boolean condition, String message) 
    {
    	try 
    	{
    		assertTrue(condition, message);
    	} 
    	catch(Throwable e) 
    	{
    		addVerificationFailure(e);
    	}
    }
    
    public static void verifyFalse(boolean condition) 
    {
    	try 
    	{
    		assertFalse(condition);
		} 
    	catch(Throwable e) 
		{
    		addVerificationFailure(e);
		}
    }
    
    public static void verifyFalse(boolean condition, String message) 
    {
    	try 
    	{
    		assertFalse(condition, message);
    	} 
    	catch(Throwable e) 
    	{
    		addVerificationFailure(e);
    	}
    }
    
    public static void verifyEquals(boolean actual, boolean expected, String message) 
    {
    	try 
    	{
    		assertEquals(actual, expected);
		} 
    	catch(Throwable e) 
		{
			addVerificationFailure(e, message);
			log.error("Expected value is ["+expected+"] but found actual value as ["+actual+"]");
    		Reporter.log("Expected value is [<b>"+expected+"</b>] but found actual value as [<b>"+actual+"</b>]</br></br>");
		}
    }

    public static void verifyEquals(Object actual, Object expected, String message) 
    {
    	try 
    	{
    		assertEquals(actual, expected);
		} 
    	catch(Throwable e) 
		{
    		addVerificationFailure(e, message);
    		log.error("Expected value is ["+expected+"] but found actual value as ["+actual+"]");
    		Reporter.log("Expected value is [<b>"+expected+"</b>] but found actual value as [<b>"+actual+"</b>]</br></br>");
		}
    }
    
    public static void verifyEquals(Object[] actual, Object[] expected, String message) 
    {
    	try 
    	{
    		assertEquals(actual, expected);
		} 
    	catch(Throwable e) 
		{
			addVerificationFailure(e, message);
		}
    }



	private static List<Throwable> getVerificationFailures()
	{
		List<Throwable> verificationFailures = verificationFailuresMap.get(Reporter.getCurrentTestResult());
		return verificationFailures == null ? new ArrayList<Throwable>() : verificationFailures;
	}
	
	public static void addVerificationFailure(Throwable e)
	{
		List<Throwable> verificationFailures = getVerificationFailures();
		verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
		verificationFailures.add(e);
	}
	
	private static void addVerificationFailure(Throwable e, String message) 
	{
		log.error(message);
		Reporter.log(message+"</br>");
		List<Throwable> verificationFailures = getVerificationFailures();
		verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
		verificationFailures.add(e);
	}
}