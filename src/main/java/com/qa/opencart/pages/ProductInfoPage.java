package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleutil;

	private By productheader = By.cssSelector("div#content h1");
	private By productimgcount = By.cssSelector("div#content a.thumbnail");
	private By productmetadata = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productpricedata = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");

	private Map<String, String> productmap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}

	public String getProductHeader() {
		String Header = eleutil.doGetText(productheader);
		System.out.println("product header = " + Header);
		return Header;
	}

	public int getProductImgCount() {

		int imagescount = eleutil.waitForVisibilityOfElementsLocated(productimgcount, TimeUtil.DEFAULT_MEDIUM_TIME)
				.size();
		System.out.println("Total number of images = " + imagescount);
		return imagescount;

	}
	
	public Map<String, String> getProductInfoMap() {
		productmap = new LinkedHashMap<String, String>();
		productmap.put("productname", getProductHeader());
		productmap.put("productimagecount",String.valueOf(getProductImgCount()));
		getProductMetaData();
		getProductPriceData();
		return productmap;
		
	}

//	//Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: In Stock

	private void getProductMetaData() {

		
		List<WebElement> metalist = eleutil.waitForVisibilityOfElementsLocated(productmetadata,
				TimeUtil.DEFAULT_MEDIUM_TIME);
		for (WebElement e : metalist) {
			String MetaData = e.getText();
			String Meta[] = MetaData.split(":");
			String metakey = Meta[0].trim();
			String metavalue = Meta[1].trim();
			productmap.put(metakey, metavalue);
		}
	}

//	$2,000.00
//	Ex Tax: $2,000.00
	private void getProductPriceData() {
		List<WebElement> pricelist = eleutil.waitForVisibilityOfElementsLocated(productpricedata,
				TimeUtil.DEFAULT_MEDIUM_TIME);
		String productprice = pricelist.get(0).getText();
		String exTaxprice = pricelist.get(1).getText().split(":")[1].trim();
		productmap.put("Productprice",  productprice);
		productmap.put("EXTaxprice", exTaxprice);
		

	}

}
