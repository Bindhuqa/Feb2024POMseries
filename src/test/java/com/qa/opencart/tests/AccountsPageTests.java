package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;

public class AccountsPageTests extends BaseTest {
	
	@BeforeClass
	public void accountSetUp() {
	accountpage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void accountPageTitleTest() {
	String actualaccpagetitle =	accountpage.getAccountPageTitle();
	Assert.assertEquals(actualaccpagetitle, AppConstants.ACCOUNT_PAGE_TITLE,AppError.TITLE_NOT_FOUND);;
		
	}
	
	@Test
	public void accountPageUrlTest() {
	String actualaccountpageurl = accountpage.getAccountPageUrl();
	Assert.assertTrue(actualaccountpageurl.contains(AppConstants.ACCOUNT_PAGE_FRACTION_URL), AppError.URL_NOT_FOUND);
	}
	
	@Test
	public void accPageHeadersList() {
	List<String> headerslist =	accountpage.getAccountsPageHeaders();
	Assert.assertEquals(headerslist, AppConstants.ACC_PAGE_HEADERS_LIST,AppError.LIST_IS_NOT_MATCHED);
	}
	
	@DataProvider
	public Object[][] getSearchData() {
		return new Object[][] {
			{"macbook",3},
			{"imac",1},
			{"samsung",2},
			{"Airtel",0}
		};
		
	}
	
	@Test(dataProvider="getSearchData")
	public void searchTest(String searchkey,int resultscount) {
		searchresultspage =	accountpage.doSearch(searchkey);
		Assert.assertEquals(searchresultspage.getSerachResultCount(),resultscount,AppError.RESULTS_COUNT_MISMATCHED);
		
	}
	

}
