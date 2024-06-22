package api.apiTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
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

public class ApiTestBase {
	public  Logger log;
	public static String environment = null;
	public static String userType = null;
	public static Properties user_prop;

	@BeforeClass (alwaysRun = true)
	public void setUp(){


	}

	@Parameters({"environment", "userType"})
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(@Optional("qa") String environment,  @Optional("admin") String userType){
		log = LogManager.getLogger(this.getClass());
		this.environment = environment;
		this.userType = userType;
		log.info("environment : "+this.environment);
		log.info("userType : "+this.userType);
	}
}
