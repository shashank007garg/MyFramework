package com.test.automation.uiAutomation.registrationPage;

import org.testng.Assert;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.automation.uiAutomation.testBase.TestBase;
import com.test.automation.uiAutomation.uiActions.LoginPage;

public class TC003_VerifyLoginWithDifferentData extends TestBase{
	LoginPage loginpage;
	String userName;
	String password;
	String runMode;
	
	@DataProvider(name="loginData")
	public String[][] getTestData(){
		String[][] testRecords = getData("LoginTest","Login.xlsx");
		return testRecords;
	}
	@BeforeTest
	public void setUp() {

		init();
	}
	
	@Test(dataProvider="loginData")
	public void verifyLoginData(String userName,String password,String runMode) throws InterruptedException {
		if(runMode.equalsIgnoreCase("N")){
			throw new SkipException("user marked this record as no run");
		}
		loginpage=new LoginPage(driver);
		loginpage.loginApp(userName, password);
		Assert.assertEquals("My account - My Store",
				loginpage.getRegistrationSuccessMessage());
		
		loginpage.logOut();
		
		
		
	}
	
	@AfterTest
	public void endTest() {
		driver.close();
	}
	
}
