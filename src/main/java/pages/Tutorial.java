package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class Tutorial {
	GenericUtility u;

	public Tutorial(GenericUtility u) {
		this.u = u;
	}

	By bySkip = By.xpath("//button[text()='Skip This Tutorial']");
	By byAthenaId = By.xpath("//p[contains(text(),'Athena ID:')]");

	public void clickOnSkipTutorial() {
		u.waitForLoading();
		try{u.aClick(bySkip);
		u.rep.logInReport("Info", "Skipped Tutorial");
		}catch(Exception e) {}
		u.waitForLoading();
	}
	
	public void verifyIfTutorialPageIsDisplayed() {
		try{if (u.isDisplayed(bySkip))
			u.rep.logInReport("Pass", "Tutorial is displayed");
		else
			u.rep.logInReport("Fail", "Failed to display Tutorial page");
	}catch(Exception e) {
		u.rep.logInReport("Fail", "Failed to display Tutorial page");//Need to handle with listeners
	}
	}

}