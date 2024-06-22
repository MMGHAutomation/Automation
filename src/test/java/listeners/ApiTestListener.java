package listeners;

import api.apiTests.ApiTestBase;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import pages.StaticThreadLocal;
import utilities.ExtentManager;
import utilities.Helper;
import webTests.TestBase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ApiTestListener extends ApiTestBase implements ITestListener {
    public static ExtentReports apiExtentReport;
    public static ExtentTest apiExtentTest;
    public Logger  log = LogManager.getLogger(this.getClass());
    @Override
    public synchronized void onStart(ITestContext context) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
        String dateStr = formatter.format(date);
        String reportName = context.getName() + "-"+ userType+" User -"+environment+"-"+dateStr;
        log.info("Extent Reports Version 3 Test Suite started!");
        log.info(context.getName());
        apiExtentReport = ExtentManager.createInstance(reportName);
    }

    @SneakyThrows
    @Override
    public synchronized void onFinish(ITestContext context) {
        log.info("Extent Reports Version 3  Test Suite is ending!");
        apiExtentReport.setSystemInfo("Environment",environment);
        apiExtentReport.setSystemInfo("UserType",userType);
        apiExtentReport.flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        log.info(result.getMethod().getMethodName() + " started!");
        Test mtest = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class);
        String testname = mtest.testName();
        String testdesc= mtest.description();
        String testgroups = mtest.groups()[0];
        apiExtentTest = apiExtentReport.createTest(testname,testdesc);
        StaticThreadLocal.setExtentTest(apiExtentTest);
        StaticThreadLocal.getExtentTest().assignCategory(testgroups);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        log.info(result.getMethod().getMethodName() + " passed!");
        StaticThreadLocal.getExtentTest().pass("Test passed");
    }


    @Override
    public synchronized void onTestFailure(ITestResult result) {
        log.info(result.getMethod().getMethodName() + " failed!");
        StaticThreadLocal.getExtentTest().fail(result.getThrowable());
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        log.info(result.getMethod().getMethodName() + " skipped!");
        StaticThreadLocal.getExtentTest().skip(result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.info("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName());
    }


}
