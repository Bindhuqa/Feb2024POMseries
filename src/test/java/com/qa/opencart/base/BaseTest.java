package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.beust.jcommander.Parameter;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.SearchResultsPage;

public class BaseTest {
	DriverFactory df;
	WebDriver driver;
	protected Properties prop;
	protected LoginPage loginpage;
	protected AccountPage accountpage;
	protected SearchResultsPage searchresultspage;
	protected ProductInfoPage productinfopage;
	protected SoftAssert softassert;
	protected RegistrationPage registrationpage;
	
    @Parameters({"browser"})
	@BeforeTest
	public void setUp(@Optional("firefox") String browsername) {
    	df = new DriverFactory();
		prop = df.initProperty();
		
		if(browsername!=null) {
    		prop.setProperty("browser",browsername);
    	}
		driver = df.initDriver(prop);
		loginpage = new LoginPage(driver);
		softassert = new SoftAssert();
		

	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
