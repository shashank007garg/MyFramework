package com.test.automation.uiAutomation.customListener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
	private int retryCount = 0;
	private int maxRetryCount = 2;

	public boolean retry(ITestResult result) {
		if (retryCount < maxRetryCount) {
			System.out.println("Retrying Test" + result.getName()
					+ "with status" + getResultStatusName(result.getStatus())
					+ "for the" + (retryCount + 1)+"time(s).");
			retryCount++;
			return true;
		}
		return false;
	}

	private String getResultStatusName(int status) {
	String resultName=null;
	if(status==1){
		resultName="SUCESS";
	}
	if(status==2){
		resultName="FAILURE";
	}
	
	if(status==3){
		resultName="SKIP";
	}
	
		return resultName;
	}

}
