package com.test.automation.uiAutomation.testBase;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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

	public String[][] getData(String sheetName,String excelName) {
		String path = System.getProperty("user.dir")
				+ "\\src\\main\\java\\com\\test\\automation\\uiAutomation\\data\\"+excelName;
		reader = new Excel_Reader(path);
		String[][] data=reader.getDataFromSheet(sheetName);
		return data;
	}
}
