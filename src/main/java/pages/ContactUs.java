package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class ContactUs {
	GenericUtility u;

	public ContactUs(GenericUtility u) {
		this.u = u;
	}

	By byPageHeader = By.xpath("//h1[@class='contact-heading']");

	public void verifyContactUsPageIsDisplayed() {
		u.waitToVisible(byPageHeader);
		try{if (u.isDisplayed(byPageHeader))
			u.rep.logInReport("Pass", "Contact Us page is displayed successfully");
		else
			u.rep.logInReport("Fail", "Failed to display Contact Us page");
		}catch(Exception e) {
			u.rep.logInReport("Fail", "Failed to display Contact Us page");
		}
	}
}
