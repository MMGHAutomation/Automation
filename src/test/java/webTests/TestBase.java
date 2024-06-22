package webTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import pages.Home.HomePage;
import pages.PageBase;
import pages.StaticThreadLocal;
import utilities.Helper;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.edge.EdgeOptions;

public class TestBase {
	public static Logger log = LogManager.getLogger();
	public static String downloadPath = null;
	public static String baseURL = null;
	public static String environment = null;
	public static String userType = null;
	public static String username = null;
	public static String macDownloadPath = System.getProperty("user.dir")+ "/Downloads";
	public static String windowsDownloadPath= System.getProperty("user.dir")+ "\\Downloads";
	public static String remote_url = "http://localhost:4444/";
	public Capabilities capabilities;

	PageBase pageBase;
	HomePage loginPage;
	HomePage loginpage;
	public static String hms;
	public static String group;
	public static String testStaus;


	public static ChromeOptions chromeOption(){
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default.content_settings.popups", 0);
		Platform platform = Helper.getCurrentPlatform();
		getReportFileLocation(platform);
		log.info("Downloads path : " + downloadPath);
		chromePrefs.put("download.default_directory", downloadPath);
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--no-sandbox"); // Bypass OS security model
		return options;
	}
	public void navigateToHomePage(String baseUrl){
		log.info("Navigate to + " + baseUrl);
		StaticThreadLocal.getWebDriver().navigate().to(baseUrl);
		StaticThreadLocal.getWebDriver().manage().window().maximize();
		StaticThreadLocal.getWebDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	public static WebDriver chromeBrowserSetup() throws MalformedURLException {
		/*DesiredCapabilities caps = new DesiredCapabilities();
		caps.setBrowserName(Browser.CHROME.browserName());
		return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),caps);*/


		ChromeOptions chromeOptions = new ChromeOptions();
		WebDriverManager.chromedriver().setup();
		return new ChromeDriver(chromeOptions);
//		return WebDriverManager.chromedriver()
//				.capabilities(chromeOption())
//				.remoteAddress("http://localhost:4444")
//				.create();
	}
	public static WebDriver firefoxBrowserSetup() throws MalformedURLException {
		/*DesiredCapabilities caps = new DesiredCapabilities();
		caps.setBrowserName(Browser.CHROME.browserName());
		return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),caps);*/


		FirefoxOptions firefoxOptions = new FirefoxOptions();
		WebDriverManager.chromedriver().setup();
		return new FirefoxDriver(firefoxOptions);
//		return WebDriverManager.chromedriver()
//				.capabilities(chromeOption())
//				.remoteAddress("http://localhost:4444")
//				.create();
	}
	@BeforeClass (alwaysRun = true)
	@Parameters({"browser", "site" })
	public void startDriver(@Optional("chrome") String browserName ,@Optional("p5") String site, Method method) throws IOException {

		if(browserName.equals("firefox")) {
			capabilities = new FirefoxOptions();
		} else if (browserName.equals("chrome")) {
			//capabilities = new ChromeOptions();
			WebDriver chromeDriver = chromeBrowserSetup();
			StaticThreadLocal.setDriver(chromeDriver);
		} else if (browserName.equals("edge")) {
			capabilities = new EdgeOptions();
		}
		//StaticThreadLocal.setDriver(new RemoteWebDriver(new URL(remote_url), capabilities));


		//////////////////////////////////////////////////////////////
//		if (browserName.equalsIgnoreCase("chrome")){
//			WebDriver chromeDriver= chromeBrowserSetup();
//			//StaticThreadLocal.setDriver(chromeDriver);
//			StaticThreadLocal.setDriver(new ChromeDriver());
//		}
		//loginPage = new LoginPage(StaticThreadLocal.getWebDriver());
		if(environment.equals("prod") ){

		}else if(environment.equals("qa")){
			if (site.equals("p5")&& userType.equals("admin")) {

				baseURL = Helper.getPropertiesFile("userdata").getString("baseUrl");
				log.info(baseURL);
				username = Helper.getPropertiesFile("userdata").getString("email");
				log.info(username);
				String pass = Helper.getPropertiesFile("userdata").getString("password");
				log.info(pass);
				navigateToHomePage(baseURL);

			}
		}else if(environment.equals("UAT")){

			}
	}
	@AfterMethod (alwaysRun = true)
	public void afterMethod(Method method){

	}
	@Parameters({"environment", "userType"})
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(@Optional("qa") String environment,  @Optional("admin") String userType){
		this.environment = environment;
		this.userType = userType;
		log.info("environment : "+this.environment);
		log.info("userType : "+this.userType);
	}
	@AfterClass(alwaysRun = true)
	public void stopDriver(){
		StaticThreadLocal.closeBrowser();
	}

	private static String getReportFileLocation(Platform platform) {
		String reportFileLocation = null;
		switch (platform) {
			case MAC:
				downloadPath = macDownloadPath;
				break;
			case WINDOWS:
				downloadPath = windowsDownloadPath;
				break;
			default:
				log.info("Download path has not been set! There is a problem!\n");
				break;
		}
		return reportFileLocation;
	}
}
