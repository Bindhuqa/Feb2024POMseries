package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class AccountPage {

	private WebDriver driver;
	private ElementUtil eleutil;

	public AccountPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}

	private By logoutlink = By.linkText("Logout");
	private By headers = By.xpath("//div[@id='content']/h2");
	private By searchfield = By.name("search");
	private By searchicon = By.cssSelector("div#search button");

	public String getAccountPageTitle() {
		
		String title = eleutil.waitForTitleToBe(AppConstants.ACCOUNT_PAGE_TITLE,TimeUtil.DEFAULT_TIME);
		System.out.println("Account page title :" + title);
		return title;
	}

	public String getAccountPageUrl() {
		
		String url = eleutil.waitForUrlContains(AppConstants.ACCOUNT_PAGE_FRACTION_URL,TimeUtil.DEFAULT_TIME);
		System.out.println("Account page url :" + url);
		return url;
	}

	public boolean isLogoutLinkExist() {
		return	eleutil.doIsDisplayed(logoutlink);
	}

	public List<String> getAccountsPageHeaders() {
		List<WebElement> Headers = eleutil.waitForVisibilityOfElementsLocated(headers, TimeUtil.DEFAULT_TIME);
		List<String> Headersvaluelist = new ArrayList<String>();
		
		for (WebElement e : Headers) {
			String text = e.getText();
			Headersvaluelist.add(text);
		}
		return Headersvaluelist;
	}
	
	public boolean isSearchFieldExist() {
		return eleutil.doIsDisplayed(searchfield);
	}
	
	public SearchResultsPage doSearch(String searchkey) {
		System.out.println("searching : " + searchkey);
		if(isSearchFieldExist()) {
			eleutil.doSendkeys(searchfield, searchkey);
			eleutil.doClick(searchicon);
			return new SearchResultsPage(driver);
		}
		else {
			System.out.println("searchfield is not present on the page");
			return null;
		}
	}

}
