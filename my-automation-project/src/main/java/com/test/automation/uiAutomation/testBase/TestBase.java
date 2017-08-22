package com.test.automation.uiAutomation.testBase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.test.automation.uiAutomation.excelReader.Excel_Reader;

public class TestBase {

	public static final Logger log = Logger.getLogger(TestBase.class.getName());

	public WebDriver driver;
	String url = "http://automationpractice.com/index.php";
	String browser = "chrome";
	Excel_Reader reader;

	public void init() {
		selectBrowser(browser);
		getUrl(url);
		String log4jConfPath = "log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
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
			Reporter.log("<a href='"+destFile.getAbsolutePath()+ "'> <img src='"+destFile.getAbsolutePath()+"' height='100' width='100'/></a>");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
