package com.datamanager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.testng.Assert;
import com.utilities.UtilityMethods;

/**
 * This class provides methods to read values of keys from either app.properties
 * or sys.properties from "ConfigFiles" folder
 */
public class ConfigManager {
	private Properties properties = new Properties();
	private String configFileName;
	private Logger log = LogManager.getLogger("ConfigManager");
	private String fileSeperator = System.getProperty("file.separator");

	// The default constructor initializes and reads the key values from
	// sys.properties file
	public ConfigManager() {
		configFileName = "Sys";
	}

	// The parameterized constructor when supplied with "app" as configFile name
	// initializes and
	// reads the key values from app.properties
	public ConfigManager(String configname) {
		configFileName = configname;
	}

	/**
	 * Returns the value of given property from either sys.properties or
	 * app.properties file
	 * 
	 * @param key
	 *            - ConfigParamvalue that requires to be returned from
	 *            Config.properties file
	 * @return - return ConfigValue
	 */
	public String getProperty(String key) {
		String value = "";
		if (key != "") {
			loadProperties();
			try {
				if (!properties.getProperty(key).trim().isEmpty())
					value = properties.getProperty(key).trim();
			} catch (NullPointerException e) {
				Assert.fail("Key - '" + key + "' does not exist or not given a value in " + configFileName
						+ ".properties file \n" + UtilityMethods.getStackTrace());
			}
		} else {
			log.error("key cannot be null.. ");
			Assert.fail("key cannot be null.. ");
		}
		return value;
	}

	/**
	 * 
	 * This method is sued to load properties file that has to be accessed
	 *
	 */
	private void loadProperties() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(getConfigFilePath(configFileName));
			properties.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			log.error("Cannot find configuration file - " + configFileName + ".properties" + " at "
					+ getConfigFilePath(configFileName));
			Assert.fail("Cannot find configuration file - " + configFileName + ".properties" + " at "
					+ getConfigFilePath(configFileName));
		} catch (IOException e) {
			log.error("Cannot read configuration file - " + " at " + getConfigFilePath(configFileName));
			Assert.fail("Cannot read configuration file - " + " at " + getConfigFilePath(configFileName));
		}
	}

	/**
	 * This method helps to write any new key value pairs to app.properties
	 * file.
	 * 
	 * @param sKey  key to write app.properties
	 * @param sData  data to write app.properties
	 */
	public void writeProperty(String sKey, String sData) {
		if (sKey.trim().length() > 0) {
			try {
				PropertiesConfiguration config = new PropertiesConfiguration(getConfigFilePath(configFileName));

				config.setProperty(sKey, sData);
				config.getLayout();
				config.save();
			} catch (ConfigurationException e) {
				Assert.fail("cannot write to properties file..." + e.getMessage() + e.getStackTrace());
			}
		} else {
			Assert.fail("cannot write to properties file...Please check if key value is empty");

		}

	}

	/* *
	 * This method helps to write the failure test data to
	 * Failure_list.properties file.
	 * 
	 * @param sKey Key to write into Failure_list.properties file
	 * @param sData Data to write into Failure_list.properties file
	 */
	public void writePropertyForFailures(String sKey, String sData, String c) {
		try {
			SimpleDateFormat format= new SimpleDateFormat("HH_mm_ss");
			Date date= new Date();
			PropertiesConfiguration config = new PropertiesConfiguration(getConfigFilePath(configFileName));
			config.setProperty(sData+"$$"+format.format(date.getTime()), sKey);
			config.getLayout();
			config.save();

		} catch (ConfigurationException e) {
			Assert.fail("cannot write to properties file..." + e.getMessage() + e.getStackTrace());
		}

	}

	/**
	 * This method helps to clean the failure test data in
	 * "FailedTestCases.properties" file
	 */
	public void cleanFailureTestCases() {
		try {

			PropertiesConfiguration config = new PropertiesConfiguration(getConfigFilePath(configFileName));
			config.clear();
			config.save();

		} catch (ConfigurationException e) {
			Assert.fail("cannot write to properties file..." + e.getStackTrace());
		}
	}
	
	/**
	 * This method helps to write the execution time in "FailedTestCases.properties" file
	 */
	public void setExecutionTime(String executionTime) {
		try {

			PropertiesConfiguration config = new PropertiesConfiguration(getConfigFilePath(configFileName));
			config.setProperty("ExecutionTime", executionTime);
			config.save();

		} catch (ConfigurationException e) {
			Assert.fail("cannot write to properties file..." + e.getMessage() + e.getStackTrace());
		}

	}

	/**
	 * 
	 * This method is used to get the path of specified properties file name
	 *
	 * @param File
	 *            , Need to pass the name of properties file
	 * @return , returns the file path of the specified properties file
	 */
	private String getConfigFilePath(String File) {
		String configFilePath;
		// configFilePath =
		// getConfigFolderPath()+fileSeperator+File.toLowerCase()+".properties";
		configFilePath = getConfigFolderPath() + fileSeperator + File + ".properties";
		return configFilePath;
	}

	/**
	 * 
	 * This method is used to get the location of 'ConfigFiles' folder
	 */
	private String getConfigFolderPath() {
		return System.getProperty("user.dir") + fileSeperator + "ConfigFiles";
	}

	/**
	 * This method helps to write any new key value pairs to properties file.
	 * 
	 * @param strKey  key to write
	 * @param strValue  Value to write
	 */
	public void writeToDashboard(String strKey, String strValue) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(configFileName + ".properties");
			config.setProperty(strKey, strValue);
			config.getLayout();
			config.save();
		} catch (ConfigurationException e) {
			log.error("cannot write to properties file..." + e.getMessage() + UtilityMethods.getStackTrace());
			Assert.fail("cannot write to properties file..." + e.getMessage());
		}

	}

}
