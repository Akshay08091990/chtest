package com.casper;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.casper.pages.HomePage;
import com.casper.pages.Login;
import com.casper.pages.MyTherapyPage;
import com.casper.utils.TestListener;
@Listeners({TestListener.class})

public class MyTherapyTest extends MyTherapyPage {
	HomePage home = new HomePage();
	Login userLogin = new Login();
	MyTherapyPage therapy = new MyTherapyPage();

	@Test
	public void verifyMyTraining() throws InterruptedException{
		userLogin.validLogin();
		therapy.verifyExercises();
		
	}

	@Test
	public void verifySteps() throws InterruptedException{
		userLogin.validLogin();
		therapy.verifyStepsSection();
	}
}
