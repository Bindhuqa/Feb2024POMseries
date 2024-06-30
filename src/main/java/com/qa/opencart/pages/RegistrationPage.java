package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class RegistrationPage {

	private WebDriver driver;
	private ElementUtil eleutil;

	private By firstname = By.id("input-firstname");
	private By lastname = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmpassword = By.id("input-confirm");
	private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[1]/input[@type='radio']");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[2]/input[@type='radio']");
	private By agreecheckbox = By.name("agree");
	private By continuebutton = By.xpath("//input[@value='Continue']");
	private By successmsg = By.cssSelector("#content h1");
	private By logoutlink = By.linkText("Logout");
	private By registerlink = By.linkText("Register");

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}

	public boolean userRegister(String firstname, String lastname, String email, String telephone, String password,
			String subscribe) {
		eleutil.doSendkeys(this.firstname, firstname, TimeUtil.DEFAULT_MEDIUM_TIME);
		eleutil.doSendkeys(this.lastname, lastname);
		eleutil.doSendkeys(this.email, email);
		eleutil.doSendkeys(this.telephone, telephone);
		eleutil.doSendkeys(this.password, password);
		eleutil.doSendkeys(confirmpassword, password);
		if (subscribe.equalsIgnoreCase("Yes")) {
			eleutil.doClick(subscribeYes);
		} else {
			eleutil.doClick(subscribeNo);
		}
		eleutil.doClick(agreecheckbox);
		eleutil.doClick(continuebutton);

		String successmessage = eleutil.waitForElementVisible(successmsg, TimeUtil.DEFAULT_MEDIUM_TIME).getText();
		System.out.println(successmessage);
		if (successmessage.contains(AppConstants.USER_REGISTER_SUCCESS_MSG)) {
			eleutil.doClick(logoutlink);
			eleutil.doClick(registerlink);
			return true;
		} else {
			return false;
		}

	}

}
