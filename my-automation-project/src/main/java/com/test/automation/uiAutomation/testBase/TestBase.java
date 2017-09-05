package com.test.automation.uiAutomation.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.uiAutomation.customListener.Listener;
import com.test.automation.uiAutomation.excelReader.Excel_Reader;

public class TestBase {

	public static final Logger log = Logger.getLogger(TestBase.class.getName());

	public static WebDriver driver;
	//String url = "http://automationpractice.com/index.php";
	//String browser = "chrome";
	Excel_Reader reader;
	Properties OR;
	public static ExtentReports extent;
	public static ExtentTest test;
	public ITestResult result;

	public void init() throws IOException {
		loadData();
		extent = new ExtentReports(
				System.getProperty("user.dir")
						+ "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\report\\test.html",
				false);
		selectBrowser(OR.getProperty("browser"));
		getUrl(OR.getProperty("url"));
		String log4jConfPath = "log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
	}
	public void loadData() throws IOException {
		OR = new Properties();
		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir")
						+ "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\config\\config.properties");

		OR.load(file);
	}

	public void selectBrowser(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"drivers/chromedriver.exe");
			log.info("creating object of " + browser);
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					"drivers/geckodriver.exe");
			log.info("creating object of " + browser);
			driver = new FirefoxDriver();
			
			
		}
	}

	public void getUrl(String url) {
		log.info("navigating to" + url);
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public String[][] getData(String sheetName, String excelName) {
		String path = System.getProperty("user.dir")
				+ "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\data\\"
				+ excelName;
		reader = new Excel_Reader(path);
		String[][] data = reader.getDataFromSheet(sheetName);
		return data;
	}

	public void waitForElement(int timeOutInSeconds, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));

	}

	public void getScreenShot(String name) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_mm_yyyy_hh_mm_ss");

		File srcfile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			String reportDirectory = new File(System.getProperty("user.dir"))
					.getAbsolutePath()
					+ "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\screenshot\\";
			File destFile = new File((String) reportDirectory + name + "_"
					+ formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(srcfile, destFile);
			Reporter.log("<a href='" + destFile.getAbsolutePath()
					+ "'> <img src='" + destFile.getAbsolutePath()
					+ "' height='100' width='100'/></a>");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void closeBrowser() {
		driver.quit();
		extent.endTest(test);
		extent.flush();
	}

	public Iterator<String> getAllWindows() {
		Set<String> window = driver.getWindowHandles();
		Iterator<String> Iterator = window.iterator();
		return Iterator;
	}
	
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, result.getName() + "test is pass");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, result.getName()
					+ "test is skip and skip reason is" + result.getThrowable());
		}

		else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, test.addScreenCapture(captureScreen("")));
		}

		else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + "test is started");
		}
	}

	private String captureScreen(String fileName) {
		if(fileName==""){
			fileName="blank";
		}
		File destFile =null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_mm_yyyy_hh_mm_ss");

		File srcfile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			String reportDirectory = new File(System.getProperty("user.dir"))
					.getAbsolutePath()
					+ "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\screenshot\\";
			 destFile = new File((String) reportDirectory + fileName + "_"
					+ formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(srcfile, destFile);
			Reporter.log("<a href='" + destFile.getAbsolutePath()
					+ "'> <img src='" + destFile.getAbsolutePath()
					+ "' height='100' width='100'/></a>");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.toString();
		
		
	}
	
	@BeforeMethod()
	public void beforeMethod(Method  method){
		test=extent.startTest(method.getName());
		test.log(LogStatus.INFO, method.getName()+"test Started");
	}
	
	@AfterMethod()
	public void afterMethod(ITestResult result){
		getResult(result);
	}
	
	@AfterClass(alwaysRun=true)
	public void endTest(){
		closeBrowser();
	}
}
