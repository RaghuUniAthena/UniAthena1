package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class MyProfile {
	GenericUtility u;

	public MyProfile(GenericUtility u) {
		this.u = u;
	}

	By byPageHeader = By.xpath("//div[normalize-space(text())='My Personal Profile']");

	public void verifyMyProfilePageIsDisplayed() {
		u.waitToVisible(byPageHeader);
		try{if (u.isDisplayed(byPageHeader))
			u.rep.logInReport("Pass", "My Profile page is displayed successfully");
		else
			u.rep.logInReport("Fail", "Failed to display My Profile page");
		}catch(Exception e) {
			u.rep.logInReport("Fail", "Failed to display My Profile page");
		}
	}
}
