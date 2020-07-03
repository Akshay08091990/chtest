package com.casper.pages;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.casper.pageelements.HomepagePageElements;
import com.casper.pageelements.KnowledgePageElements;
import com.casper.pageelements.TrainingPageElements;
import com.casper.pageelements.WelcomePageElements;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class HomePage extends CommonBase {

	public void verifyHomepage() {
		Assert.assertEquals(isElementPresent(HomepagePageElements.myTraningTitle), true);
		Assert.assertEquals(isElementPresent(HomepagePageElements.myTraningCount), true);
		Assert.assertEquals(isElementPresent(HomepagePageElements.myTraningGo), true);
		Assert.assertEquals(isElementPresent(HomepagePageElements.knowledgeCount), true);
		Assert.assertEquals(isElementPresent(HomepagePageElements.knowledgeTitle), true);
		Assert.assertEquals(isElementPresent(HomepagePageElements.knowledgeMore), true);
		Assert.assertEquals(isElementPresent(HomepagePageElements.wellBeingTitle), true);
		Assert.assertTrue(
				getText(HomepagePageElements.myTraningTitle).contains(testdata.getProperty("MyTraningTitle")));

	}

	public void verifyMyTraining() throws InterruptedException {
		tap(HomepagePageElements.myTraningGo);
		waitForElement(TrainingPageElements.lblExcercise);
		Assert.assertEquals(isElementPresent(TrainingPageElements.lblExcercise), true);
		Assert.assertEquals(isElementPresent(TrainingPageElements.lblExcerciseCount), true);
		Assert.assertEquals(isElementPresent(TrainingPageElements.lblMinutes), true);

		tap(TrainingPageElements.bthReady);
		tap(TrainingPageElements.startTraining);
		waitForElement(TrainingPageElements.btnSkip);
		Assert.assertEquals(isElementPresent(TrainingPageElements.videoLayout), true);
		Assert.assertEquals(isElementPresent(TrainingPageElements.excerciseText), true);
		tap(TrainingPageElements.btnSkip);
		verifyPopupText("Skip exercise?",
				"Are you sure you want to skip this exercise? You will not be able to go back.");
		verifyPopupButton("SKIP", "No, Go back");
		tap(WelcomePageElements.leftPopupButton);
		tap(TrainingPageElements.btnStart);
		Assert.assertEquals(isElementPresent(TrainingPageElements.lblGetReady), true);
		Assert.assertEquals(isElementPresent(TrainingPageElements.lblCountDown), true);
		Thread.sleep(5000);
		tap(TrainingPageElements.btnPause);
		waitForElement(TrainingPageElements.excerciseVideoLayout);
		Assert.assertEquals(isElementPresent(TrainingPageElements.mainProgress), true);
		Assert.assertEquals(isElementPresent(TrainingPageElements.totalProgress), true);
		Assert.assertEquals(isElementPresent(TrainingPageElements.lblSets), true);
		((AndroidDriver<MobileElement>) driver).navigate().back();
		verifyPopupText("Quit training?", "Are you sure you want to quit today's training?");
		verifyPopupButton("Yes, Quit", "No, Go back");
		tap(WelcomePageElements.leftPopupButton);
		waitForElement(HomepagePageElements.myTherapy);

	}

	public void verifyKnowldegeSection() throws InterruptedException {
		Thread.sleep(5000);
		waitForElement(HomepagePageElements.knowledgeMore);
		String KnowledgeCount = getText(HomepagePageElements.knowledgeCount);
		tap(HomepagePageElements.knowledgeTitle);
		screenTitle("KNOWLEDGE");
		waitForElement(KnowledgePageElements.iconVideo);
		Assert.assertEquals(isElementPresent(KnowledgePageElements.lblDuration), true);
		Assert.assertEquals(isElementPresent(KnowledgePageElements.lblVideo), true);
	}

}
