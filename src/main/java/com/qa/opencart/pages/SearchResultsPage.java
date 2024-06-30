package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class SearchResultsPage {
	
	private WebDriver driver;
	private ElementUtil eleutil;
	
	private By searchresult = By.cssSelector("div.product-thumb");

	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}
	
	public int getSerachResultCount() {
	List<WebElement> resultslist =	eleutil.waitForVisibilityOfElementsLocated(searchresult, TimeUtil.DEFAULT_MEDIUM_TIME);
	int resultcount = resultslist.size();
	System.out.println("product result count is " + resultcount);
	return resultcount;
	}
	
	public ProductInfoPage selectProduct(String Productname) {
	eleutil.doClick(By.linkText(Productname),TimeUtil.DEFAULT_TIME);
	return new ProductInfoPage(driver);
	}


}
