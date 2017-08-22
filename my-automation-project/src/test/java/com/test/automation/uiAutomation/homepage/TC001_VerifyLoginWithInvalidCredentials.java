package com.test.automation.uiAutomation.homepage;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.automation.uiAutomation.testBase.TestBase;
import com.test.automation.uiAutomation.uiActions.HomePage;

public class TC001_VerifyLoginWithInvalidCredentials extends TestBase {
	public static final Logger log=Logger.getLogger(TC001_VerifyLoginWithInvalidCredentials.class.getName());
	
	HomePage homepage;

	@BeforeTest
	public void setUp() {

		init();
	}

	@Test
	public void verifyLoginWithInvalidCredentials() {
		log.info("----Starting VerifyLoginWithInvalidCredentials  Test");
		homepage = new HomePage(driver);
		homepage.loginToApplication("test@gmail.com", "password1");
		Assert.assertEquals(homepage.getInvalidLoginTest(),
				"Authentication failed.");
		log.info("----Finished VerifyLoginWithInvalidCredentials  Test");
	}

	@AfterClass
	public void endTest() {
		driver.close();
	}
}
