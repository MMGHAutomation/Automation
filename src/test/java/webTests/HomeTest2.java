package webTests;

import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.Home.HomePage;
import pages.StaticThreadLocal;

public class HomeTest2 extends TestBase {
	HomePage homePage;

	@BeforeClass(alwaysRun = true)
	public void initialize() {
		homePage = new HomePage(StaticThreadLocal.getWebDriver());
		log = LogManager.getLogger(this.getClass());
	}
	@Test(priority = 1, testName = "Home : Check fashion",
			suiteName = "Home", groups = {"Home"},
			description="description test")
	public void checkFashionTitle(){
		log.info("in test case 1 , first step");
		StaticThreadLocal.setExtentTestInfo("in test case 1 , first step");
		System.out.println("Check fashion");
		Assert.assertTrue(homePage.checkFashion());
	}
	@Test(priority = 2, testName = "Home : Refresh_your_space",
			suiteName = "Home", groups = {"Home"},
			description="Refresh_your_space")
	public void tc2(){
		log.info("in test case 2 , first step");
		StaticThreadLocal.setExtentTestInfo("in test case 2 , first step");
		System.out.println("Test case 2");
		Assert.assertTrue(homePage.checkRefresh_your_space());
	}
	@Test(priority = 3, testName = "Home : dad gift",
			suiteName = "Home", groups = { "Home"},
			description="dad gift")
	public void checkDadGift(){
		log.info("in test case 3 , first step");
		StaticThreadLocal.setExtentTestInfo("in test case 3 , first step");
		System.out.println("Test case 3");
		Assert.assertTrue(homePage.checkDadGift());
	}
	@Test(priority = 4, testName = "Home : check pcs",
			suiteName = "Home", groups = { "Home"},
			description="Check pcs")
	public void tc4(){
		log.info("in test case 4 , first step");
		StaticThreadLocal.setExtentTestInfo("in test case 4 , first step");
		System.out.println("Test case 4");
		Assert.assertTrue(homePage.checkpcs());
	}
}
