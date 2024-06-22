package utilities;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


public  class Helper {
	private static Platform platform;

	public static Logger log = LogManager.getLogger();
	// Method to take screenshot when the test cases fail

	public static void captureScreenshot(WebDriver driver , String screenshotname){
		Path dest = Paths.get("./Screenshots",screenshotname+".png");
		try {
			Files.createDirectories(dest.getParent());
			FileOutputStream out = new FileOutputStream(dest.toString());
			out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
			out.close();
		} catch (IOException e) {
			log.error("Excpetion while taking screenshot"+ e.getMessage());
		}
	}
	public static String getScreenShotAsBase64(WebDriver driver, String screenshotname) throws IOException {
		File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		Path dest = Paths.get("./Screenshots",screenshotname+".png");
		String path = dest.toString();
		FileUtils.copyFile(source, new File(path));
		byte[] imageByte = IOUtils.toByteArray(new FileInputStream(path));
		return Base64.getEncoder().encodeToString(imageByte);
	}

	//Get current platform
	public static Platform getCurrentPlatform () {
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
	public static Properties readPropertiesFile(String fileName) throws IOException {
		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream(fileName);
			prop = new Properties();
			prop.load(fis);
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {
			fis.close();
		}
		return prop;
	}

	public static ResourceBundle getPropertiesFile(String fileName) {
		ResourceBundle routs = ResourceBundle.getBundle(fileName);
		return routs;
	}

	public static void deleteFile(String excelFileFullPath){
		log.info("Delete Path : "+excelFileFullPath);
		File file = new File(excelFileFullPath);
		if(file.exists()){
			file.delete();
			log.info("File deleted!");
		}
	}
	public static void deleteFileBySubString(String downloadPath,String fileNameSubStr ){
		File directory = new File(downloadPath);
		File[] files = directory.listFiles();
		for (File f : files){
			if (f.getName().startsWith(fileNameSubStr)){
				log.info("File "+f.getName()+" Found!");
				f.delete();
				log.info("File "+f.getName()+" deleted!");
			}
		}
	}
	public static String  getFilePath(String downloadPath,String fileNameSubStr ){
		File directory = new File(downloadPath);
		File[] files = directory.listFiles();
		for (File f : files){
			if (f.getName().startsWith(fileNameSubStr))
			{
				return f.getAbsolutePath();

			}
		}
		return  null;
	}
	public static boolean isFileExist(String excelFileFullPath){
		log.info("File Path exist : "+excelFileFullPath);
		File file = new File(excelFileFullPath);
		if(file.exists()){
			return true;
		}
		return false;
	}
    public static String getDateForwardByOffset(int offset){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// convert date to calendar
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		// manipulate date
		c.add(Calendar.DATE, offset); //same with c.add(Calendar.DAY_OF_MONTH, 1);
		// convert calendar to date
		Date currentDatePlusOne = c.getTime();
		return dateFormat.format(currentDatePlusOne);
    }

	public static boolean isExpiredDate (String companyDatestr){
		Date companyDate = null;
		try {
			companyDate = getDateFromString(companyDatestr + " 00:00");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		Date date_now = new Date();
		if (companyDate.before(date_now)) {
			return true;
		}
		return false;
	}
	public static Date getDateFromString(String date) throws ParseException {
		Date date_out = null;
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		date_out = inputFormat.parse(date);
		return date_out;
	}

	public static int getDayFromTodaysDate() throws ParseException {
		Date date=new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	public static boolean isNumeric(String strNum) {
		Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
		if (strNum == null) {
			return false;
		}
		return pattern.matcher(strNum).matches();
	}
}
