package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PageBase {
	public Logger log;
	protected WebDriver driver ;
	public JavascriptExecutor jse ; 
	public Select select ; 
	public Actions action ;
	public int timeOut = 10;
	public int pageloadTimeOut = 10;


	// create constructor 
	public PageBase(WebDriver driver){
		this.driver = driver;
		log = LogManager.getLogger(this.getClass());
	}

	protected WebElement getElement(By by){
		return driver.findElement(by);
	}

	protected void clickButton(By by){
		waitUntilVisible(by);
		getElement(by).click();
	}
	// Method to send Keys
	protected  void clearAndSetText(By by, String value) {
		waitUntilVisible(by);
		getElement(by).clear();
		getElement(by).sendKeys(value);
	}

	protected  void setText(By by, String value) {
		waitUntilVisible(by);
		getElement(by).sendKeys(value);
	}

	protected  void clearText(By by) {
		waitUntilVisible(by);
		getElement(by).clear();
	}

	protected  void selectCheckBox(By by) {
		waitUntilVisible(by);
		if(!getElement(by).isSelected()){
			waitUntilElementClickableAndClick(by);
		}
	}

	protected  void unSelectCheckBox(By by) {
		waitUntilVisible(by);
		if(getElement(by).isSelected())
		{
			clickButton(by);
		}
	}

	public void scrollToElement(By by){
		log.info("Wait element to scroll");
		waitUntilVisible(by);
		log.info("Move to element");
		Actions actions = new Actions(driver);
		actions.moveToElement(getElement(by));
		actions.release().build().perform();
		log.info("Scroll action done");
	}
	public void mouseHover(By by){
		Actions action = new Actions(driver);
		action.moveToElement(getElement(by)).perform();
	}


	public void scrollToBottom(){
		jse.executeScript("scrollBy(0,2500)"); 
	}
	public void scrollToRight(By by){
//		JavascriptExecutor jse = (JavascriptExecutor)driver;
//		jse.executeScript("window.scrollBy(2000,0)");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getElement(by));
	}

	public void scrollToTop(){
		jse.executeScript("scrollBy(0,-1500)");
	}
	public boolean waitForPageLoading(){
	     JavascriptExecutor jse = (JavascriptExecutor)driver;
		for (int i=0; i<pageloadTimeOut; i++){
			try {
				Thread.sleep(1000);
			}catch (InterruptedException ex) {
				log.info("Page has not loaded yet ");
			}
			//again check page state
			if (jse.executeScript("return document.readyState").toString().equals("complete")){
				log.info("Page loaded Successfully ");
				return true;
			}
		}
		log.error("Failed to load page ");
	   return true;
	}

	// Wait for Next button to be visible.
	public  boolean waitUntilVisible(By by){
		// Wait for Element to be visible.
		try {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			// element found, return true
			return true;
		} catch (Exception e) {
			// Element not found, return false
			log.error("Element not found : " +by.toString());
			e.printStackTrace();
			return false;
		}

	}

	public  boolean waitUntilElementClickable(By by){
		// Wait for Element to be visible.
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.elementToBeClickable(getElement(by)));
			// element found, return true
			return true;
		} catch (Exception e) {
			// Element not found, return false
			log.error("Element not clickable : " + by.toString());
			e.printStackTrace();
			return false;
		}

	}

	public void waitUntilInvisibilityOfElement(By by){
		// Wait for Element to be visible.
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			//wait.until(ExpectedConditions.visibilityOf(getElement(by))); // wait for loader to appear
			wait.until(ExpectedConditions.invisibilityOf(getElement(by))); // wait for loader to disappear

	}
	public boolean waitUntilElementClickableAndClick(By by){
		// Wait for Element to be visible.
		try {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.elementToBeClickable(getElement(by)));
			clickButton(by);
			return true;
		} catch (Exception e) {
			// Element not found, return false
			log.error("Element not clickable : " + by.toString());
			e.printStackTrace();

			return false;
		}

	}

	// Method to Press Enter
	public void keyPressEnter(By by) {
		getElement(by).sendKeys(Keys.ENTER);
	}

	public void keyPressTab(WebElement element) {
		element.sendKeys(Keys.TAB);
	}

	public void selectFromDropDownList( WebElement element, String selectItem){
		Select drop = new Select(element);
		drop.selectByVisibleText(selectItem);
	}

	public String getText(By by){
		waitUntilVisible(by);
		String returnedText = getElement(by).getText();
		return returnedText;
	}

	public WebElement getElementByText(String text) {
		By by = By.xpath("//*[contains(text(), '" + text + "')]");
		waitUntilVisible(by);
		return getElement(by);
	}
	public By getBy(String text) {
		return By.xpath("//*[contains(text(), '" + text + "')]");
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	public void navigateToPage(String url) {
		driver.navigate().to(url);
	}
	public boolean waitUntilURLDisplayed(String url){
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.urlContains(url));
	}
	public void navigateToTab(int tabIndex) {
		ArrayList<String> tab = new ArrayList<>(driver.getWindowHandles());
		if (tab.size() > 0) {
			driver.switchTo().window(tab.get(tabIndex));
		}else{
			log.error("No tabs found");
		}

	}

	public String getCellfromDataGrid(int row, int cellNumber) throws InterruptedException {
		By by = By.xpath("//tr[@aria-rowindex='"+row+"']/td[@data-kendo-grid-column-index='"+cellNumber+"']");
		scrollToElement(by);
		return getElement(by).getText();
	}

	public String getCellValueFromAdminTable(int row, int cellNumber){
		By by = By.xpath("//table/tbody/tr["+row+"]/td["+cellNumber+"]");
		scrollToElement(by);
		return getElement(by).getText();
	}
	public ArrayList<String> getRowfromDataGrid(int row, int colNum) throws InterruptedException {
		ArrayList<String> rowList = new ArrayList<String>();
		colNum = colNum+1;
		for (int i = 0; i < colNum ; i++) {
			String value = getCellfromDataGrid(row,i);
			rowList.add((value.replaceAll("[\r\n]+", " ")));
		}
		return rowList;
	}

	public List<WebElement> getListOfChildElements(String xpath) {
		List<WebElement> parentEelementList = driver.findElements(By.xpath(xpath));
		return parentEelementList;
	}


	public boolean isElementExistInParent(WebElement parentElement, String  tag){
		List<WebElement> childList = parentElement.findElements(By.xpath("./child::*"));
		for (WebElement element:childList){
			List<WebElement> list = element.findElements(By.xpath("./child::*"));
			for (WebElement ele:list){
				if (ele.getTagName().equals(tag)) {
					return true;
				}else{
					return false;
				}
			}

		}
		return false;
	}

	public List<WebElement> getColumnFromDataGridByColID( int colNum) throws InterruptedException {
		List<WebElement> colList = new ArrayList<WebElement>();
		colList = driver.findElements(By.xpath("//td[@aria-colindex='"+colNum+"']"));
		return colList;
	}

	public List<WebElement> getColumnFromDataGrid(String colTag) {
		List<WebElement> colList = new ArrayList<WebElement>();
		List<WebElement> listCount = driver.findElements(By.xpath("//"+colTag+""));
		log.info("Column Size : -----> "+listCount.size());
		for (int i = 1; i <= listCount.size(); i++) {
			scrollToElement(By.xpath("(//partnumber-cell-grid)["+i+"]"));
			colList.add(getElement(By.xpath("(//partnumber-cell-grid)["+i+"]")));
		}
		return colList;
	}
	public boolean isAttributeExists(By by, String attribute){
		WebElement ele = getElement(by);
		String attr = ele.getAttribute(attribute);
		if (attr == null){
			return false;
		}
		return true;
	}

	public String getAttributeValue(By by, String attribute){
		return getElement(by).getAttribute(attribute);
	}

	public void waitUntilGridLoaderRemove() {
        //spinner//div[@class='p5-spinner']
		//div[@class='kendo-loading-custom']/spinner//div[@class='p5-spinner']
		log.info("Wait until loader removed");
		try{
			waitUntilInvisibilityOfElement(By.xpath("//div[@class='kendo-loading-custom']/spinner"));
		}catch(StaleElementReferenceException e){
			e.printStackTrace();
			log.error("--> wait until grid column removed : StaleElementReferenceException thrown");
			try {
				log.error("--> wait 3 seconds ");
				Thread.sleep(3000);
			} catch (InterruptedException interruptedException) {
				interruptedException.printStackTrace();
			}
		}catch (NoSuchElementException e){
			e.printStackTrace();
		}
	}

	public void waitUntilPageLoaderRemoved() {
		log.info("Wait until loader removed");
		try{
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofMillis(5000))
					.pollingEvery(Duration.ofMillis(500))
					.ignoring(NoSuchElementException.class);
			//WebDriverWait waitLoaderRemoved = new WebDriverWait(driver, Duration.ofSeconds(20));
			//waitLoaderVisible.until(ExpectedConditions.visibilityOf(loader)); // wait for loader to appear
			//log.info("Loader visible");
			wait.until(ExpectedConditions.invisibilityOf(getElement(By.xpath("//div[@class='p5-spinner']")))); // wait for loader to disappear
			log.info("Loader removed");
		}catch(Exception e){
			log.error("Exception thrown");
			e.printStackTrace();
			try {
				log.info("--> wait 1 seconds ");
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}

		}
	}

	public void clearColumnFiltartion(By colHeader, By by){
		if (getAttributeValue(colHeader, "class").equals("k-header k-filterable filterd-th")){
			clickButton(by);
			waitUntilGridLoaderRemove();
			waitUntilElementClickableAndClick(By.xpath("//button[text()='Clear']"));
		}
	}

	public void setColumnFiltration(By colHeader, By filterButton, String filterValue){
		if (getAttributeValue(colHeader, "class").equals("k-header k-filterable")){
			clickButton(filterButton);
			waitUntilVisible(By.xpath("//input[@id='filter-search-input']"));
			clearAndSetText(By.xpath("//input[@id='filter-search-input']"), filterValue);
			waitUntilVisible(By.xpath("//span[contains(@title,'Active')]"));
			clickButton(By.xpath("//span[contains(@title,'Active')]"));
			waitUntilElementClickableAndClick(By.xpath("//button[@type='submit']"));
			waitUntilVisible(By.xpath("//tbody/tr[1]"));
//			waitUntilGridLoaderRemove();
		}
	}

}
