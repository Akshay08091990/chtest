package com.casper;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.casper.utils.TestListener;
import com.casper.pages.Login;
import com.casper.pages.HomePage;

@Listeners({TestListener.class})
public class HomePageTest extends HomePage {
	HomePage home = new HomePage();
	Login userLogin = new Login();

	@Test
	public void verifyMyTraining() throws InterruptedException{
		userLogin.validLogin();
		home.verifyHomepage();
		home.verifyMyTraining();
		
	}
	
	@Test
	public void verifyMyExercise() throws InterruptedException{
		userLogin.validLogin();
		home.verifyKnowldegeSection();
	}
}
