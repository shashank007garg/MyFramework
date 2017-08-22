package com.test.automation.uiAutomation.uiActions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.automation.uiAutomation.homepage.TC001_VerifyLoginWithInvalidCredentials;

public class HomePage {

	public static final Logger log=Logger.getLogger(HomePage.class.getName());
	WebDriver driver;

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//*[@id='header']/div[2]/div/div/nav/div[1]/a")
	WebElement signIn;

	@FindBy(xpath = "//*[@id='email']")
	WebElement loginEmail;

	@FindBy(xpath = "//*[@id='passwd']")
	WebElement loginPassword;

	@FindBy(id = "SubmitLogin")
	WebElement submitButton;

	@FindBy(xpath = "//*[@id='center_column']/div[1]/ol/li")
	WebElement authenticationFailed;

	public void loginToApplication(String emailAdderess, String password) {
		signIn.click();
		log.info("clickon sigin button"+signIn.toString());
		loginEmail.sendKeys(emailAdderess);
		log.info("enter email adderess"+loginEmail.toString());
		loginPassword.sendKeys(password);
		log.info("enter password"+loginPassword.toString());
		submitButton.click();
		log.info("clickon submit button"+submitButton.toString());
	}

	public String getInvalidLoginTest() {
		log.info("error message is"+authenticationFailed.getText());
		return authenticationFailed.getText();
	}
}
