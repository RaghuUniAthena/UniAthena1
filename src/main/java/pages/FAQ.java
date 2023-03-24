package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class FAQ {
	GenericUtility u;

	public FAQ(GenericUtility u) {
		this.u = u;
	}

	By byPageHeader = By.xpath("//h3[normalize-space(text())='how can we help?']");

	public void verifyFAQPageIsDisplayed() {
		u.waitToVisible(byPageHeader);
		try{if (u.isDisplayed(byPageHeader))
			u.rep.logInReport("Pass", "FAQ page is displayed successfully");
		else
			u.rep.logInReport("Fail", "Failed to display FAQ page");
		}catch(Exception e) {
			u.rep.logInReport("Fail", "Failed to display FAQ page");
		}
	}
}
