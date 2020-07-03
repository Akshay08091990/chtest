package com.casper.pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.casper.pageelements.WelcomePageElements;
import com.casper.utils.PropertyReader;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class CommonBase {
	public static final String FILEPATH = "/test.txt";
	public static AppiumDriver<MobileElement> driver;
	public WebDriverWait wait;
	PropertyReader properties = PropertyReader.getPropertyReader("/testconfig.properties");
	PropertyReader testdata = PropertyReader.getPropertyReader("/testdata.properties");

	public Logger logger = Logger.getLogger("MyLog");
	public String lLogLocation = "./AutomationLog.log";
	DesiredCapabilities capabilities;

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws InterruptedException, IOException {
		File path = null;
		capabilities = new DesiredCapabilities();
		String deviceName = null;
		String OS = System.getProperty("os.name");
		final File classpathRoot = new File(System.getProperty("user.dir"));
		final File appDir = new File(classpathRoot, "");
		File app;
		app = new File(appDir, properties.getProperty("appName"));
		capabilities.setCapability("app", app.getAbsolutePath());

		if (OS.contains("Windows")) {
			String[] commands = new String[] { "cmd.exe", "/c", "adb devices" };
			deviceName = getDeviceID(commands);
			/* the above code is used to get device name programmatically */
		} else {

			deviceName = properties.getProperty("devicename");
		}
		capabilities.setCapability("deviceName", properties.getProperty("devicename"));

		capabilities.setCapability(CapabilityType.VERSION, properties.getProperty("androidversion"));
		capabilities.setCapability("appPackage", properties.getProperty("appPackage"));
		capabilities.setCapability("appWaitActivity", properties.getProperty("appWaitActivity"));
		capabilities.setCapability("autoGrantPermissions", true);
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");
		capabilities.setCapability("noReset", "false");
		driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 30);

	}

	public String getDeviceID(String[] commands) throws IOException {
		String devicename = null;
		ProcessBuilder pb = new ProcessBuilder(commands);

		try {
			System.out.println("Start");
			Process pc = pb.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(pc.getInputStream()));
			String line;
			String device = null;
			int loop = 0;
			while ((line = reader.readLine()) != null) {
				if (line.contains("device") && line != null) {
					device = line;
					loop++;
				} else {
					System.out.println("No device found " + loop + "" + line);
				}
			}
			System.out.println("device response" + device);
			if (device != null) {
				devicename = device.substring(0, device.indexOf("\t"));
				properties.setProperty("devicename", device.substring(0, device.indexOf("\t")),
						"testconfig.properties");
			} else {
				System.out.println("No device found" + device);
			}
			int exitCode = pc.waitFor();
			System.out.println("\nExited with error code : " + exitCode);
			System.out.println("deviceName : " + devicename);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return devicename;
	}

	public void tap(By locator) {
		try {
			waitForElement(locator);
			driver.findElement(locator).click();
		} catch (Exception e) {
			Assert.assertEquals(isElementPresent(locator), true, "Cannot click on " + locator + "");
		}

	}

	public void type(By locator, String testdata) throws InterruptedException {
		try {
			waitForElement(locator);
			tap(locator);
			driver.findElement(locator).clear();
			Thread.sleep(500);
			driver.findElement(locator).sendKeys(testdata);
		} catch (Exception e) {
			Assert.assertEquals(isElementPresent(locator), true, "Element " + locator + " not located");
		}
	}

	public String getText(By element) {
		String text = null;
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try {
			text = driver.findElement(element).getText();
		} catch (NoSuchElementException e) {
			logger.info("Element not found => " + text);

		}
		return text;
	}

	public boolean isElementDisplayed(By locator) {
		boolean elementDisplayed = false;
		try {
			List<MobileElement> w1 = driver.findElements(locator);
			if (!w1.isEmpty()) {
				elementDisplayed = true;
			} else {
				elementDisplayed = false;
			}
			return elementDisplayed;
		} catch (NoSuchElementException e) {
			return elementDisplayed;
		}

	}

	public void verifyPopupText(String popupTitle, String popupMsg) {
		try {
			waitForElement(WelcomePageElements.popupMessage);
			Assert.assertEquals(getText(WelcomePageElements.popupheader), popupTitle,
					"Popup Title is not as expected: ");
			Assert.assertEquals(getText(WelcomePageElements.popupMessage), popupMsg,
					"Popup Message is not as expected: ");

		} catch (Exception e) {
			Assert.assertEquals(isElementPresent(WelcomePageElements.popupMessage), true, "Popup not located");
		}
	}

	public void verifyPopupButton(String leftButtonText, String rightButtonText) {
		try {
			waitForElement(WelcomePageElements.rightPopupButton);
			Assert.assertEquals(getText(WelcomePageElements.leftPopupButton), leftButtonText,
					"Popup Button Text is not as expected: ");
			Assert.assertEquals(getText(WelcomePageElements.rightPopupButton), rightButtonText,
					"Popup Button Text is not as expected: ");

		} catch (Exception e) {
			Assert.assertEquals(isElementPresent(WelcomePageElements.popupMessage), true, "Popup not located");
		}

	}

	public void scrollAndClick(String visibleText) {

		((AndroidDriver<MobileElement>) driver).findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
						+ visibleText + "\").instance(0))"))
				.click();

	}

	public void screenTitle(String title) {
		try {
			Assert.assertEquals(getText(WelcomePageElements.screenTitle), title, "ScreenTITLE is not as expected");
		} catch (Exception e) {
			logger.info("Screen Not Displayed");
		}
	};

	public String getDob() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, 0);
		return simpleDateFormat.format(cal.getTime());

	}

	public void scroll(int swipes) {

		((AndroidDriver<MobileElement>) driver).findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(1)).setMaxSearchSwipes(" + swipes
						+ ").scrollIntoView(new UiSelector().instance(0))"));
	}

	public void horizontalScrollDown() {
		Dimension dimensions = driver.manage().window().getSize();
		int anchor = (int) (dimensions.height * 0.5);
		Double screenHeightStart = dimensions.getWidth() * 0.9;
		int scrollStart = screenHeightStart.intValue();
		Double screenHeightEnd = dimensions.getWidth() * 0.01;
		int scrollEnd = screenHeightEnd.intValue();

		new TouchAction(driver).press(PointOption.point(scrollStart, anchor))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(400)))
				.moveTo(PointOption.point(scrollEnd, anchor)).release().perform();
	}

	public void verticalScrollDown() {
		Dimension dimensions = driver.manage().window().getSize();
		Double screenHeightStart = dimensions.getHeight() * 0.5;
		int scrollStart = screenHeightStart.intValue();
		Double screenHeightEnd = dimensions.getHeight() * 0.2;
		int scrollEnd = screenHeightEnd.intValue();

		new TouchAction(driver).press(PointOption.point(0, scrollStart))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(400))).moveTo(PointOption.point(0, scrollEnd))
				.release().perform();
	}

	public MobileElement getItemViews(By locator) {
		return driver.findElement(locator);
	}

	public List<MobileElement> getItemWebView(By locator) {
		return driver.findElements(locator);
	}

	public void scrollToElement(By elementToFind, String scrollType) {
		int scrollNumber = 0;
		while ((getItemWebView(elementToFind)).isEmpty() && scrollNumber <= 15) {
			logger.info("ScrollNumber >> " + scrollNumber);
			if (scrollType.equals("Vertical")) {
				verticalScrollDown();
			} else {
				horizontalScrollDown();
			}
			logger.info("GetList >> " + getItemWebView(elementToFind).size());
			scrollNumber++;

		}

	}

	public boolean isElementPresent(By locator) {
		try {
			driver.findElement(locator);

		} catch (NoSuchElementException e) {
			return false;
		}

		return true;
	}

	public void waitForElement(By locator) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (Exception e) {
			Assert.assertEquals(isElementPresent(locator), true, "Element '" + locator + "'not located");

		}

	}

	public void keyBoardShownPressedBack() {
		if (((AndroidDriver) driver).isKeyboardShown()) {
			driver.navigate().back();

		} else {
			logger.info("keyBoard not shown");
		}
	}

	public static String randomString(int lengthOfString) {

		int upperIndex = 0;
		String text = "";

		String possible = "0123456789";

		for (int i = 0; i < lengthOfString; i++) {
			upperIndex = (int) (Math.random() * possible.length());
			text = text + possible.charAt(upperIndex);
		}

		return text;
	}

	@AfterMethod
	public void tearDown() throws IOException {

		if (driver != null) {
			driver.quit();
			capabilities.setCapability("unicodeKeyboard", false);
			capabilities.setCapability("resetKeyboard", false);
			logger.info("Driver Quit");

		}
	}
}
