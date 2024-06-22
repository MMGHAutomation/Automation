package pages;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class StaticThreadLocal {
    public static Logger log = LogManager.getLogger();
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> threadLocalExtentTest = new ThreadLocal<>();

    public static  void setDriver(WebDriver webDriver) {
        driver.set(webDriver);

    }

    public static WebDriver getWebDriver(){
        return driver.get();
    }

    public static void closeBrowser() {
        getWebDriver().close();
        getWebDriver().quit();
        driver.remove();
    }

    public static void setExtentTest(ExtentTest extentTest) {
        threadLocalExtentTest.set(extentTest);
    }

    public static ExtentTest getExtentTest(){
        return threadLocalExtentTest.get();
    }
    public static void setExtentTestInfo(String message) {
        getExtentTest().info(message);
    }
}
