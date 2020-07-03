package com.casper.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.casper.pageelements.HomepagePageElements;
import com.casper.pageelements.KnowledgePageElements;
import com.casper.pageelements.MyTherapyPageElements;

public class MyTherapyPage extends CommonBase {

	public void verifyExercises() {
		tap(HomepagePageElements.myTherapy);
		screenTitle("My therapy week");
		Assert.assertEquals(isElementPresent(MyTherapyPageElements.excerciseCount), true);
		Assert.assertEquals(isElementPresent(MyTherapyPageElements.excerciseTitle), true);
		Assert.assertEquals(isElementPresent(MyTherapyPageElements.excerciseMore), true);
		String excerciseCount = getText(MyTherapyPageElements.excerciseCount);
		tap(MyTherapyPageElements.excerciseMore);
		waitForElement(KnowledgePageElements.iconVideo);
		screenTitle("EXERCISES");
		Assert.assertEquals(isElementPresent(KnowledgePageElements.lblDuration), true);
		Assert.assertEquals(isElementPresent(KnowledgePageElements.lblVideo), true);

	}

	public void verifyStepsSection() throws InterruptedException {
		tap(HomepagePageElements.myTherapy);
		screenTitle("My therapy week");
		Assert.assertEquals(isElementPresent(MyTherapyPageElements.stepsTitle), true);
		tap(MyTherapyPageElements.stepsMore);
		waitForElement(MyTherapyPageElements.stepsGoal);
		String exisitngSteps = null;
		System.out.println(getDob());
		if (isElementPresent(
				By.xpath("//*[@resource-id='com.casparhealth.android.patient:id/date' and @text[contains(.,'"
						+ getDob().toString() + "')]]"))) {
			exisitngSteps = driver
					.findElement(By
							.xpath("//*[@resource-id='com.casparhealth.android.patient:id/date' and @text[contains(.,'"
									+ getDob().toString()
									+ "')]]/following-sibling::android.widget.LinearLayout//android.widget.TextView[@resource-id='com.casparhealth.android.patient:id/stepsCount']"))
					.getText();
			System.out.println(exisitngSteps);
		}
		tap(MyTherapyPageElements.addCount);
		waitForElement(MyTherapyPageElements.enterSteps);
		String stepsCount = randomString(3);
		System.out.println("random : " + stepsCount);
		type(MyTherapyPageElements.enterSteps, stepsCount);
		tap(MyTherapyPageElements.saveSteps);
		waitForElement(MyTherapyPageElements.stepsGoal);
		int totalStepsPerDay = Integer.parseInt(stepsCount) + Integer.parseInt(exisitngSteps);
		System.out.println(totalStepsPerDay);

		By steps = By.xpath("//*[@resource-id='com.casparhealth.android.patient:id/stepsCount' and @text[contains(.,'"
				+ totalStepsPerDay + "')]]");
		Assert.assertEquals(isElementPresent(steps), true);

	}

}
