package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.listeners.AnnotationTransformer;
import com.qa.opencart.listeners.ExtentReportListener;
import com.qa.opencart.listeners.TestAllureListener;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
@Epic("Epic 100: Design Opencart Application with Shopping Workflow")
@Story("US 101: Design Loginpage for Opencart Application")
@Listeners({ExtentReportListener.class,TestAllureListener.class,AnnotationTransformer.class})
public class LoginPageTests extends BaseTest {
    @Description("checking the login page title----")
    @Severity(SeverityLevel.MINOR)
	@Test(priority = 1)
    @Owner("Naveen AutomationLabs")
    @Issue("Login123")
    @Feature("Loginpage title feature")
	public void loginPageTitleTest() {
		String actualtitle = loginpage.getLoginPageTitle();
		Assert.assertEquals(actualtitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND);

	}
    
    @Description("checking the login page url----")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Naveen AutomationLabs")
    @Feature("Loginpage url feature")
	@Test(priority = 2)
    public void loginPageUrlTest() {
		String actualurl = loginpage.getLoginPageUrl();
		Assert.assertTrue(actualurl.contains(AppConstants.LOGIN_PAGE_FRACTION_URL), AppError.URL_NOT_FOUND);

	}
    
    @Description("checking the forot password link existence----")
    @Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3)
	public void frgtPasswordLinkExistTest() {
		Assert.assertTrue(loginpage.checkFrgtPswdLinkExist(), AppError.ELEMENT_NOT_FOUND);

	}
    
    @Description("checking user is able to login successfully---")
    @Severity(SeverityLevel.BLOCKER)
	@Test(priority = 4)
	public void loginTest() {
	accountpage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	String actualaccountpagetitle =	accountpage.getAccountPageTitle();
	Assert.assertEquals( actualaccountpagetitle, AppConstants.ACCOUNT_PAGE_TITLE,AppError.TITLE_NOT_FOUND);;

	}

}
