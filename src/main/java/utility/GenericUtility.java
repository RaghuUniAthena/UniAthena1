package utility;

import static org.testng.Assert.assertFalse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import repo.UserDetailsRepo;

/*
 * GenericUtility is inherited by the Page Object Model classes in src/main/java/pages
 */
public class GenericUtility {
	protected WebDriver driver;
	WebElement webElement;
	WebElement e;
	public String currentDirectory = System.getProperty("user.dir");
	String data;
	Actions actions;
	public ExtentReport rep;
	public String reportScreenshotFileName;
	public String reportPath;
	public UserDetailsRepo objUserDetailsRepo;
	public DBConnector objDB;

	public GenericUtility(WebDriver driver, String className) {
		this.driver = driver;
		rep = new ExtentReport(this);
		rep.initiateExtentReport();
		rep.startTest(className);
		InstanceRepo.setGenericUtilityInstance(this);
	}

	public GenericUtility(String browser, ExtentReport rep) throws Exception {
		if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", currentDirectory + "\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", currentDirectory + "\\drivers\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
		    options.addArguments("--start-maximized");
		    options.addArguments("Zoom 80%");
			driver = new ChromeDriver(options);
		} else if (browser.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver", currentDirectory + "\\drivers\\MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
		} else {
			throw new Exception("Browser is not correct");
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		this.rep = rep;
		rep.setGenericUtilityInstance(this);
		objUserDetailsRepo = new UserDetailsRepo();
		try{
			objDB = new DBConnector();
		}catch(CommunicationsException e) {
			rep.logInReport("Fail","DB is down"+e);
		}
		actions = new Actions(driver);
		InstanceRepo.setGenericUtilityInstance(this);

//		rep = new ExtentReport(this);
//		rep.initiateExtentReport(className);
	}
//	public void setReportInstance(ExtentReport rep) {
//		this.rep=rep;
//	}

	// *********************** Screenshot **********************************
//	public String takeScreenshot() {
//		// waitForLoading();
//		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//		File Dest = new File(System.getProperty("user.dir") + "\\ExtentReport\\" + reportScreenshotFileName
//				+ "\\Screenshot" + System.currentTimeMillis() + ".png");
//		String flpath = Dest.getAbsolutePath();
//		try {
//			FileUtils.copyFile(scrFile, Dest);
//		} catch (IOException e) {
//		}
//		return flpath;
//	}

	public String takeScreenshot() {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    String encodedBase64 = null;
	    FileInputStream fileInputStreamReader = null;
	    try {
	        fileInputStreamReader = new FileInputStream(scrFile);
	        byte[] bytes = new byte[(int)scrFile.length()];
	        fileInputStreamReader.read(bytes);
	        encodedBase64 = new String(Base64.encodeBase64(bytes));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return "data:image/png;base64,"+encodedBase64;
	}

	// *********************** Launching URL **********************************
	public void launchUrl(String url) {
		driver.get(url);
		driver.manage().window().maximize();
		waitForLoading();
	}

	// *********************** Navigation Commands ****************************
	public void navigateBack() {
		driver.navigate().back();
	}

	// *********************** Wait Commands **********************************
	public WebElement waitToClick(By by) {
		// waitForLoading();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(by)));

		} catch (Exception e) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, 5);
				wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(by)));

			} catch (Exception e1) {
				try {
					WebDriverWait wait = new WebDriverWait(driver, 10);
					wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(by)));

				} catch (Exception e2) {
					waitTime(2);
					WebDriverWait wait = new WebDriverWait(driver, 10);
					wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(by)));

				}

			}

		}

		return driver.findElement(by);
	}

	public WebElement waitToClick(WebElement e) {
		// waitForLoading();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(e));

		} catch (Exception ex) {
			waitTime(2);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(e));
		}
		return e;
	}

	public WebElement waitToVisible(By by) {
		// waitForLoading();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
		} catch (Exception e) {
			waitTime(2);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
		}
		return driver.findElement(by);
	}

	public void waitUntilInvisible(By by) {
		waitUntilInvisible(by,3600);
		}
		public void waitUntilInvisible(By by, int timeOutInSec) {
			waitTime(2);
			for (int i = 0; i <= (timeOutInSec/3); ++i) {
				if (driver.findElements(by).isEmpty()) {
					break;
				} else {
					waitTime(3);
				}
				if (i == (timeOutInSec/3))
					rep.logInReport("Fail", "Element not disappeared within "+timeOutInSec+" seconds. Locator: "+by);
			}
//			WebDriverWait wait = new WebDriverWait(driver, 60);
//			wait.ignoring(NoSuchElementException.class);
//			wait.ignoring(ElementNotVisibleException.class);
//			wait.ignoring(StaleElementReferenceException.class);
//			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));

//		for (int i = 0; i < 30; i++) {
//			try {
//				if (driver.findElement(by).isDisplayed())
//					waitTime(2);
//				else
//					break;
//			} catch (Exception e) {
//				break;
//			}
//		}
	}

	public void waitTime(int timeInSecs) {
		try {
			Thread.sleep(1000 * timeInSecs);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ************************** Web Element Commands *************************
	public WebElement element(By by) {
		// waitForLoading();
		// waitToClick(by);
		e = driver.findElement(by);
		return e;
	}

	public List<WebElement> elements(By by) {
		// waitForLoading();
		//waitToVisible(by);
		return driver.findElements(by);
	}

	public WebElement click(By by) {
		waitToClick(by);
		e = driver.findElement(by);
		e.click();
		return e;
	}

	public WebElement enterTextbox(By by, String text) {
		// waitForLoading();
		waitToClick(by);
		e = driver.findElement(by);
		aClick(by);
		e.clear();
		e.sendKeys(text);
		return e;
	}

	public String getText(By locator) {
		// waitForLoading();
		webElement = driver.findElement(locator);
		data = webElement.getText();
		return data;
	}

	public void isDisplayed(By byLocator, String elementName) {
		// waitForLoading();
		waitToVisible(byLocator);
		if (driver.findElement(byLocator).isDisplayed())
			rep.logInReport("Pass", elementName + " is displayed");
		else
			rep.logInReport("Fail", elementName + " is NOT displayed");
	}

	public boolean isDisplayed(By byLocator) {
		// waitForLoading();
		//waitToVisible(byLocator);
		if (driver.findElement(byLocator).isDisplayed())
			return true;
		else
			return false;
	}

	// ************************** Javascript Class Commands *************************

	public void jsClick(By by) {
		// waitForLoading();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", element(by));

	}

	// ************************** Actions Class Commands *************************
	public WebElement aClick(By by) {
		// waitForLoading();
		waitToClick(by);
		e = driver.findElement(by);
		actions.moveToElement(e).click(e).build().perform();
		return e;
	}

	public void aClick(WebElement e) {
		waitToClick(e);
		actions.moveToElement(e).click(e).build().perform();

	}

	public WebElement aSendKeys(By by, String value) {
		// waitForLoading();
		waitToClick(by);
		e = driver.findElement(by);
		actions.moveToElement(e).sendKeys(e, value).build().perform();
		return e;
	}

	public void aSendKeys(String value) {
		actions.sendKeys(value).build().perform();

	}

	public WebElement aHover(By by) {
		// waitForLoading();
		waitToClick(by);
		e = driver.findElement(by);
		actions.moveToElement(e).build().perform();
		return e;
	}

	public WebElement aHover(WebElement e) {
		// waitForLoading();
		waitToClick(e);
		// e = driver.findElement(by);
		actions.moveToElement(e).build().perform();
		return e;
	}

	// ************************** Drop Down Commands *************************
	public WebElement selectDropDownByIndex(By by, int index) {
		// waitForLoading();
		waitToClick(by);
		e = driver.findElement(by);
		e.isSelected();
		Select s1 = new Select(e);
		s1.selectByIndex(index);
		return e;
	}

	public WebElement selectDropDownByValue(By by, String value) {
		// waitForLoading();
		waitToClick(by);
		e = driver.findElement(by);
		e.isSelected();
		Select s1 = new Select(e);
		s1.selectByValue(value);
		return e;
	}

	public WebElement selectDropDownByVisibleText(By by, String text) {
		// waitForLoading();
		waitToClick(by);
		e = driver.findElement(by);
		e.isSelected();
		Select s1 = new Select(e);
		s1.selectByVisibleText(text);
		return e;
	}

	public WebDriver getDriver() {
		return driver;
	}

	// ******************************** Misc *********************************
	public static String getRandomValue() {
		return "" + System.currentTimeMillis();
	}

	public static String getDateTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return LocalDate.now().format(formatter) + "_" + getRandomValue();
	}

	public static String getRandomPhoneNumber(String countryCode) throws Exception {
		String phoneNumber = "";
		Random rand = new Random();
		switch (countryCode.toUpperCase()) {
		case "INDIA":
			// int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) +
			// rand.nextInt(8);
			int num1 = ((rand.nextInt(2) + 7) * 100) + (rand.nextInt(8) * 10) + rand.nextInt(9);
			int num2 = rand.nextInt(743);
			int num3 = rand.nextInt(10000);
			DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
			DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros
			phoneNumber = df3.format(num1) + df3.format(num2) + df4.format(num3);
			break;
		case "CANADA":
			int[] areaCode = { 418, 289, 902, 250, 780, 506, 709, 902, 867, 613, 418, 306, 709, 807, 416, 204 };
			num1 = areaCode[rand.nextInt(areaCode.length - 1)];
			// num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) +
			// rand.nextInt(8);
			// int num1 = (rand.nextInt(8) * 10) + rand.nextInt(8);
			// num2 = rand.nextInt(743);
			num2 = (rand.nextInt(7) + 2) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
			num3 = rand.nextInt(10000);
			df3 = new DecimalFormat("000"); // 3 zeros
			df4 = new DecimalFormat("0000"); // 4 zeros
			phoneNumber = "(" + df3.format(num1) + ")" + df3.format(num2) + "-" + df4.format(num3);
			break;
		default:
			throw new Exception("Country number setup not implemented");
		}
		System.out.println(phoneNumber);
		return phoneNumber;
	}
	// ******************************** Project Specific *********************************

	public void waitForLoading() {
		By byLoading = By.xpath("//img[@class='ath-spinner']");
		//waitTime(2);
		try {
			waitUntilInvisible(byLoading);
		} catch (Exception e) {
		}
	}

	public void waitForLoading_Admin() {
		By byLoading = By.xpath("(//circle)[1]");
		//waitTime(3);
		try {
			waitUntilInvisible(byLoading);
		} catch (Exception e) {
		}
	}

	public String openNewTab() {
		// aSendKeys(By.tagName("body"),Keys.CONTROL + "T");
		((JavascriptExecutor) driver).executeScript("window.open();");
		waitTime(3);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		String oldTab = driver.getWindowHandle();
		driver.switchTo().window(tabs.get(1));
		return oldTab;
	}

	public String switchToNextTab() {
		// aSendKeys(By.tagName("body"),Keys.CONTROL + "T");
		// ((JavascriptExecutor)driver).executeScript("window.open();");
		waitTime(3);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		String oldTab = driver.getWindowHandle();
		for (String tab : tabs) {
			if (tab != oldTab)
				driver.switchTo().window(tab);
		}
		return oldTab;
	}

}
