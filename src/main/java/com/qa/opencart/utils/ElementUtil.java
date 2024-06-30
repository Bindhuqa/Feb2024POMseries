package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.ElementException;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsutil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsutil = new JavaScriptUtil(driver);
	}
	
	public void highlightingTheElement(WebElement element) {
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsutil.flash(element);
		}
	}

	public WebElement getElement(By Locator) {
		try {
			WebElement element = driver.findElement(Locator);
			highlightingTheElement(element);
			return element;
		} catch (NoSuchElementException e) {
			System.out.println("Element is not present on the page " + Locator);
			e.printStackTrace();
			return null;
		}
	}

	public void nullCheck(String value) {
		if (value == null) {
			throw new ElementException("value is null... " + value);
		}
	}

	public void doSendkeys(By Locator, String value, int timeout) {
		nullCheck(value);
		waitForElementPresence(Locator, timeout).clear();
		waitForElementPresence(Locator, timeout).sendKeys(value);
	}

	public void doSendkeys(By Locator, String value) {
		nullCheck(value);
		getElement(Locator).clear();
		getElement(Locator).sendKeys(value);
	}

	public void doClick(By Locator) {
		getElement(Locator).click();
	}

	public void doClick(By Locator, int timeout) {
		waitForElementPresence(Locator, timeout).click();
	}

	public String doGetText(By Locator) {
		return getElement(Locator).getText();
	}

	public String doGetAttribute(By Locator, String attname) {
		return getElement(Locator).getAttribute(attname);
	}
    @Step("checking the state of Locator {0} is displayed or not")
	public boolean doIsDisplayed(By Locator) {
		try {
			boolean flag = getElement(Locator).isDisplayed();
			System.out.println("Element is displayed " + Locator);
			return flag;
		} catch (NoSuchElementException e) {
			System.out.println("Element with locator " + Locator + "is not displayed");
			return false;

		}

	}

	public List<WebElement> getElements(By Locator) {
		return driver.findElements(Locator);
	}

	public int getElementsCount(By Locator) {
		return getElements(Locator).size();
	}

	public List<String> getElementTextList(By Locator) {
		List<WebElement> eleTextList = getElements(Locator);
		List<String> TextList = new ArrayList<String>();
		for (WebElement e : eleTextList) {
			String text = e.getText();
			if (text.length() != 0) {
				TextList.add(text);
			}
		}
		return TextList;
	}

	public List<String> getAttributeList(By Locator, String Attributename) {
		List<WebElement> imgslist = getElements(Locator);
		List<String> attrlist = new ArrayList<String>();
		for (WebElement e : imgslist) {
			String AttributeValue = e.getAttribute(Attributename);
			if (AttributeValue != null && AttributeValue.length() != 0) {
				attrlist.add(AttributeValue);
				System.out.println(AttributeValue);
			}

		}
		return attrlist;
	}
	// *****************select dropdown util****************

	public void doSelectByIndex(By Locator, int index) {
		Select select = new Select(getElement(Locator));
		select.selectByIndex(index);
	}

	public void doSelectByValue(By Locator, String value) {
		Select select = new Select(getElement(Locator));
		select.selectByValue(value);
		;
	}

	public void doSelectByVisibleText(By Locator, String Visibletext) {
		Select select = new Select(getElement(Locator));
		select.selectByVisibleText(Visibletext);
	}

	public int getDropdownOptionsCount(By Locator) {
		Select select = new Select(getElement(Locator));
		return select.getOptions().size();

	}

	public List<String> getDropdownOptionsTextList(By Locator) {
		Select select = new Select(getElement(Locator));
		List<WebElement> optionslist = select.getOptions();
		List<String> optionstextlist = new ArrayList<String>();
		System.out.println(optionslist.size());
		for (WebElement e : optionslist) {
			String text = e.getText();
			optionstextlist.add(text);

		}
		return optionstextlist;
	}

	public void selectValueFromDropdown(By Locator, String value) {
		Select select = new Select(driver.findElement(Locator));
		List<WebElement> options = select.getOptions();
		for (WebElement e : options) {
			String text = e.getText();
			if (text.equals(value)) {
				e.click();
				break;
			}
		}

	}

	public void selectValueFromDropdownWithoutSelectClass(By Locator, String value) {
		List<WebElement> optionslist = getElements(Locator);
		System.out.println(optionslist.size());
		for (WebElement e : optionslist) {
			String text = e.getText();
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	public void doDeselectAll(By Locator) {
		Select select = new Select(getElement(Locator));
		select.deselectAll();
	}

	public void doDeselectByVisibleText(By Locator, String text) {
		Select select = new Select(getElement(Locator));
		select.deselectByVisibleText(text);
	}

	public void doDeselectByValue(By Locator, String value) {
		Select select = new Select(getElement(Locator));
		select.deselectByValue(value);
	}

	public void doDeselectByIndex(By Locator, int index) {
		Select select = new Select(getElement(Locator));
		select.deselectByIndex(index);
	}

	public void doSearch(By searchfield, String searchkey, By suggestions, String value) throws InterruptedException {
		doSendkeys(searchfield, searchkey);
		Thread.sleep(3000);
		List<WebElement> suggestionsList = getElements(suggestions);
		System.out.println(suggestionsList.size());
		for (WebElement e : suggestionsList) {
			String suggestionslist = e.getText();
			System.out.println(suggestionslist);
			if (suggestionslist.contains(value)) {
				e.click();
				break;
			}
		}
	}

	// ******************ActionsClass***************************

	public void handleParentSubMenu(By parentlocator, By childlocator) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentlocator)).perform();
		Thread.sleep(2000);
		doClick(childlocator);

	}

	public void dragAndDrop(By sourceLocator, By targetLocator) {

		Actions act = new Actions(driver);
		act.dragAndDrop(getElement(sourceLocator), getElement(targetLocator)).build().perform();

	}

	public void doRightClick(By Locator) {
		Actions act = new Actions(driver);
		act.contextClick(getElement(Locator)).perform();
	}

	public void doActionsSendkeys(By Locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(driver.findElement(Locator), value).perform();

	}

	public void doActionsClick(By Locator) {
		Actions act = new Actions(driver);
		act.click(driver.findElement(Locator)).perform();

	}

	/**
	 * this method is used to enter the value in a text field with pause
	 * 
	 * @param Locator
	 * @param value
	 * @param pausetime
	 */
	public void doActionSendkeysWithPause(By Locator, String value, long pausetime) {
		Actions act = new Actions(driver);
		char ch[] = value.toCharArray();
		for (char e : ch) {
			act.sendKeys(getElement(Locator), String.valueOf(e)).pause(pausetime).perform();

		}
	}

	/**
	 * this method is used to enter the value in a text field with pause of 500ms(by
	 * default pause)
	 * 
	 * @param Locator
	 * @param value
	 */
	public void doActionSendkeysWithPause(By Locator, String value) {
		Actions act = new Actions(driver);
		char ch[] = value.toCharArray();
		for (char e : ch) {
			act.sendKeys(getElement(Locator), String.valueOf(e)).pause(500).perform();

		}

	}

	public void level4MenuSubMenuHandlingUsingClick(By Level1, String Level2, String Level3, String Level4)
			throws InterruptedException {
		doClick(Level1);
		Thread.sleep(1000);

		Actions act = new Actions(driver);
		act.moveToElement(getElement(By.linkText(Level2))).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(By.linkText(Level3))).perform();
		Thread.sleep(1000);
		getElement(By.linkText(Level4)).click();

	}

	public void level4MenuSubMenuHandlingUsingClick(By Level1, By Level2, By Level3, By Level4)
			throws InterruptedException {
		doClick(Level1);
		Thread.sleep(1000);

		Actions act = new Actions(driver);
		act.moveToElement(getElement(Level2)).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(Level3)).perform();
		Thread.sleep(1000);
		doClick(Level4);

	}

	public void level4MenuSubMenuHandlingUsingMouseHover(By Level1, By Level2, By Level3, By Level4)
			throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(Level1)).perform();

		Thread.sleep(1000);
		act.moveToElement(getElement(Level2)).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(Level3)).perform();
		Thread.sleep(1000);
		doClick(Level4);

	}

	// *******************wait utils****************************

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param Locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementPresence(By Locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(Locator));
		highlightingTheElement(element);
		return element;

	}

	public WebElement waitForElementPresence(By Locator, int timeout, int intervaltime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(intervaltime)).ignoring(NoSuchElementException.class)
				.withMessage("====Element is not found====");

		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(Locator));
		highlightingTheElement(element);
		return element;

	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param Locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementVisible(By Locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
		highlightingTheElement(element);
		return element;

	}

	public WebElement waitForElementVisible(By Locator, int timeout, int intervaltime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(intervaltime)).ignoring(NoSuchElementException.class)
				.withMessage("====Element is not found====");

		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
		highlightingTheElement(element);
		return element;

	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @param Locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitForPresenceOfElementsLocated(By Locator, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Locator));

	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locatorare visible. Visibility means that the elements are not only
	 * displayed but also have a heightand width that is greater than 0.
	 * 
	 * @param Locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitForVisibilityOfElementsLocated(By Locator, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Locator));
		}
		catch(Exception e){
		return List.of();//returning empty arraylist	
		}

	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param Locator
	 * @param timeout
	 */
	public void clickWhenReady(By Locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(Locator)).click();

	}

	public String waitForTitleContains(String titlefraction, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.titleContains(titlefraction))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("title not found");

		}
		return driver.getTitle();

	}
    @Step("Waiting for the title and capturing it")
	public String waitForTitleToBe(String title, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.titleIs(title))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("title not found");

		}
		return driver.getTitle();

	}

	public String waitForUrlContains(String urlfraction, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.urlContains(urlfraction))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("url not found");

		}
		return driver.getCurrentUrl();

	}

	public String waitForUrlToBe(String url, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.urlToBe(url))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("url not found");

		}
		return driver.getCurrentUrl();

	}

	public Alert waitForJSAlert(int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public Alert waitForJSAlert(int timeout, int intervaltime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(intervaltime)).ignoring(NoAlertPresentException.class)
				.withMessage("====Alert is not found====");

		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public String getJSAlertText(int timeout) {
		Alert alert = waitForJSAlert(timeout);
		String text = alert.getText();
		alert.accept();
		return text;

	}

	public void acceptTheAlert(int timeout) {
		waitForJSAlert(timeout).accept();

	}

	public void dismissTheAlert(int timeout) {
		waitForJSAlert(timeout).dismiss();

	}

	public void alertSendkeys(String value, int timeout) {
		Alert alert = waitForJSAlert(timeout);
		alert.sendKeys(value);
		alert.accept();

	}

	/**
	 * An expectation for checking whether the given frame is available to switch
	 * to.If the frame is available it switches the given driver to the specified
	 * frame.
	 * 
	 * @param FrameLocator
	 * @param timeout
	 */
	public void WaitForFrame(By FrameLocator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameLocator));
	}

	public void WaitForFrame(By FrameLocator, int timeout, int intervaltime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(intervaltime)).ignoring(NoSuchFrameException.class)
				.withMessage("====Frame is not found====");
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameLocator));
	}

	public void WaitForFrameByIndex(int Frameindex, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(Frameindex));
	}

	public void WaitForFrameByIDorName(String FrameIDorName, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameIDorName));
	}

	public void WaitForFrameByElement(WebElement frameEle, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameEle));
	}

	public boolean waitForWindowsToBe(int totalwindows, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(totalwindows));

	}

	public void isPageLoaded(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"))
				.toString();// "true"

		if (Boolean.parseBoolean(flag)) {
			System.out.println("page is completely loaded");
		} else {
			throw new RuntimeException("page is not loaded");
		}
	}

}
