package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;

import java.io.File;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    public static Logger log = LogManager.getLogger();
    private static ExtentReports extent;
    private static Platform platform;
    private static String macPath = System.getProperty("user.dir")+ "/TestReport";
    private static String windowsPath = System.getProperty("user.dir")+ "\\TestReport";

    private static String reportName;



    //Create an extent report instance
    public static ExtentReports createInstance(String SuiteName) {
        reportName = "Automation " + SuiteName + " webTests";
        platform = getCurrentPlatform();
        String fileName = getReportFileLocation(platform);
        fileName = fileName +FileSystems.getDefault().getSeparator() + SuiteName + ".html";
        log.info("HTML report Location  : " + fileName);
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName).viewConfigurer().viewOrder()
                .as(new ViewName[]{ViewName.DASHBOARD,ViewName.TEST, ViewName.CATEGORY,ViewName.EXCEPTION,ViewName.LOG}).apply();
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle(reportName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(reportName);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        return extent;
    }

    //Select the extent report file location based on platform
    private static String getReportFileLocation (Platform platform) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String reportFileLocation = null;
        String directory = null;
        String newPath= null;
        File createDirectoryWithDate = null;
        switch (platform) {
            case MAC:
                directory = formatter.format(date);
                newPath = macPath+FileSystems.getDefault().getSeparator()+directory;
                createDirectoryWithDate = new File(newPath);
                if (!createDirectoryWithDate.exists()){
                    createDirectoryWithDate.mkdirs();
                }
                reportFileLocation = newPath;
                createReportPath(macPath);
                log.info("ExtentReport Path for MAC: " + newPath + "\n");
                break;
            case WINDOWS:
                directory = formatter.format(date);
                newPath = windowsPath+FileSystems.getDefault().getSeparator()+directory;
                createDirectoryWithDate  = new File(newPath);
                if (!createDirectoryWithDate.exists()){
                    createDirectoryWithDate.mkdirs();
                }

                reportFileLocation = newPath;
                createReportPath(newPath);
                log.info("Extent Report Path for WINDOWS: " + newPath + "\n");
                break;
            default:
                log.error("Extent Report path has not been set! There is a problem!\n");
                break;
        }
        return reportFileLocation;
    }

    //Create the report path if it does not exist
    private static void createReportPath (String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                log.info("Directory: " + path + " is created!");
            } else {
                log.error("Failed to create directory: " + path);
            }
        } else {
            log.info("Directory already exists: " + path);
        }
    }

    //Get current platform
    private static Platform getCurrentPlatform () {
        if (platform == null) {
            String operSys = System.getProperty("os.name").toLowerCase();
            if (operSys.contains("win")) {
                platform = Platform.WINDOWS;
            } else if (operSys.contains("nix") || operSys.contains("nux")
                    || operSys.contains("aix")) {
                platform = Platform.LINUX;
            } else if (operSys.contains("mac")) {
                platform = Platform.MAC;
            }
        }
        return platform;
    }

}
