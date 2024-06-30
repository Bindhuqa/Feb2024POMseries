package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleutil;

	private By emailid = By.id("input-email");
	private By passwrd = By.id("input-password");
	private By loginbutton = By.xpath("//input[@type='submit']");
	private By frgtpassword = By.linkText("Forgotten Password");
	private By registerlink = By.linkText("Register");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}
    @Step("Getting Login page title")
	public String getLoginPageTitle() {
		
		String title = eleutil.waitForTitleToBe(AppConstants.LOGIN_PAGE_TITLE,TimeUtil.DEFAULT_TIME);
		System.out.println("login page title :" + title);
		return title;
	}
    @Step("Getting Login page url...")
	public String getLoginPageUrl() {
		
		String url = eleutil.waitForUrlContains(AppConstants.LOGIN_PAGE_FRACTION_URL,TimeUtil.DEFAULT_TIME);
		System.out.println("login page url :" + url);
		return url;
	}
    @Step("Getting the state of frgt password link")
	public boolean checkFrgtPswdLinkExist() {
		
	return	eleutil.doIsDisplayed(frgtpassword);
    }
    @Step("Login to Application with username {0} and password {1}")
	public AccountPage doLogin(String username, String password) {
		
		eleutil.doSendkeys(emailid, username,TimeUtil.DEFAULT_MEDIUM_TIME);
		eleutil.doSendkeys(passwrd, password);
		eleutil.doClick(loginbutton);
        return new AccountPage(driver);

	}
	@Step("navigating to registration page")
	public RegistrationPage navigateToRegisterPage() {
		eleutil.doClick(registerlink, TimeUtil.DEFAULT_TIME);
		return new RegistrationPage(driver);
	}

}
