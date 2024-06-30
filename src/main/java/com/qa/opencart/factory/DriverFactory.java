package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameWorkException;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	
	OptionsManager optionsmanager;
	
	public static String highlight;
	
	public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();

	/**
	 * This is used to initialize the driver on the basis of given browser name.
	 * 
	 * @param browsername
	 */
	public WebDriver initDriver(Properties prop) {
		// cross browser logic

		String browsername = prop.getProperty("browser");
		System.out.println("browser name is " + browsername);
		
		highlight = prop.getProperty("highlight");
		optionsmanager = new OptionsManager(prop);
		switch (browsername.toLowerCase().trim()) {
		case "chrome":
			//driver = new ChromeDriver();
			tldriver.set(new ChromeDriver(optionsmanager.getChromeOptions()));
			break;
		case "firefox":
			//driver = new FirefoxDriver();
			tldriver.set(new FirefoxDriver(optionsmanager.getFirefoxOptions()));
			break;
		case "edge":
			//driver = new EdgeDriver();
			tldriver.set(new EdgeDriver(optionsmanager.getEdgeOptions()));
			break;
		case "safari":
			//driver = new SafariDriver();
			tldriver.set(new SafariDriver());
			break;

		default:
			System.out.println("plz pass the right browser : " + browsername);
			throw new BrowserException(AppError.BROWSER_NOT_FOUND);

		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}
	/**
	 * get the local thread copy of the driver
	 * @return
	 */
	public static WebDriver getDriver() {
		return tldriver.get();
	}

	/**
	 * This method is used to initialize properties from properties file. This
	 * returns properties(prop).
	 * 
	 * @return
	 */
	public Properties initProperty() {
		// cross property logic

		// mvn clean install -Denv="QA"
		prop = new Properties();
		FileInputStream ip = null;
		
		String envName = System.getProperty("env");
		System.out.println("Running the test suite on env---->" + envName);

		if (envName == null) {
			System.out.println("Env name is null,hence running it on QA env");
			try {
				ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

		} else {

			try {
				switch (envName.trim().toLowerCase()) {
				case "QA":
					ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
					break;
				case "Stage":
					ip = new FileInputStream(AppConstants.CONFIG_STAGE_FILE_PATH);
					break;

				case "Dev":
					ip = new FileInputStream(AppConstants.CONFIG_DEV_FILE_PATH);
					break;

				case "UAT":
					ip = new FileInputStream(AppConstants.CONFIG_UAT_FILE_PATH);
					break;
				case "Prod":
					ip = new FileInputStream(AppConstants.CONFIG_FILE_PATH);
					break;
				default:
					System.out.println("please pass the right environment --->" + envName);
					throw new FrameWorkException("WRONG ENV PASSED");

				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();

			}
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return prop;
	}
	/**
	 * get screenshot
	 */
	public static String getScreenshot(String methodname) {
	File srcfile =	((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
	String path = System.getProperty("user.dir")+"/Screenshot/"+methodname+"_"+System.currentTimeMillis()+".png";
	File destinationfile = new File(path);
	try {
		FileHandler.copy(srcfile,destinationfile);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return path;
	}

}
