package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.errors.AppError;

public class ProductInfoPageTests extends BaseTest{
	
	@BeforeClass
	public void productInfoPageSetUp() {
	accountpage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"macbook","MacBook Pro"},
			{"imac","iMac"},
			{"samsung","Samsung SyncMaster 941BW"},
			{"samsung","Samsung Galaxy Tab 10.1"},
			{"canon","Canon EOS 5D"}
		};
		
	}
	
	@Test(dataProvider ="getProductData")
	public void productHeaderTest(String searchkey,String productname) {
	searchresultspage =	accountpage.doSearch(searchkey);
	productinfopage = searchresultspage.selectProduct(productname);
	Assert.assertEquals(productinfopage.getProductHeader(),productname,AppError.HEADER_NOT_FOUND);
	
	}
	
	@DataProvider
	public Object[][] getProductImageData() {
		return new Object[][] {
			{"macbook","MacBook Pro",4},
			{"imac","iMac",3},
			{"samsung","Samsung SyncMaster 941BW",1},
			{"samsung","Samsung Galaxy Tab 10.1",7},
			{"canon","Canon EOS 5D",3}
		};
	}
	
	@Test(dataProvider ="getProductImageData")
	public void productImagesCountTest(String searchkey,String productname,int imagescount) {
	searchresultspage =	accountpage.doSearch(searchkey);
	productinfopage = searchresultspage.selectProduct(productname);
	Assert.assertEquals(productinfopage.getProductImgCount(),imagescount,AppError.IMAGES_COUNT_MISMATCHED);
		
	}
	
	@Test
	public void productInfoTest() {
		searchresultspage =	accountpage.doSearch("macbook");
		productinfopage = searchresultspage.selectProduct("MacBook Pro");
		Map<String, String> productinfomap = productinfopage.getProductInfoMap();
		System.out.println("product Information=========");
		System.out.println(productinfomap);
		
		softassert.assertEquals(productinfomap.get("productname"),"MacBook Pro");
		softassert.assertEquals(productinfomap.get("Brand"),"Apple");
		softassert.assertEquals(productinfomap.get("Product Code"),"Product 18");
		softassert.assertEquals(productinfomap.get("Reward Points"),"800");
		softassert.assertEquals(productinfomap.get("Availability"),"In Stock");
		softassert.assertEquals(productinfomap.get("Productprice"),"$2,000.00");
		softassert.assertEquals(productinfomap.get("EXTaxprice"),"$2,000.00");
		
		softassert.assertAll();
		//for single assertion=>HardAssertion
		//for multiple assertions =>SoftAssertion(verify)
		
	}
	

}
