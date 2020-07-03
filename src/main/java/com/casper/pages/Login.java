package com.casper.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import com.casper.pageelements.HomepagePageElements;
import com.casper.pageelements.LoginPageElements;
import com.casper.pageelements.WelcomePageElements;

public class Login extends CommonBase{

	public void validLogin() throws InterruptedException{
		waitForElement(WelcomePageElements.signIn);
		tap(WelcomePageElements.signIn);
		type(LoginPageElements.email,testdata.getProperty("email"));
		keyBoardShownPressedBack();
		type(LoginPageElements.password,testdata.getProperty("password"));
		keyBoardShownPressedBack();
		tap(LoginPageElements.btnSignIn);
		Thread.sleep(5000);;
		waitForElement(HomepagePageElements.activities);
		
	}
}
