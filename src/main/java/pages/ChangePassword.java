package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class ChangePassword {
	GenericUtility u;

	public ChangePassword(GenericUtility u) {
		this.u = u;
	}

	By byPageHeader = By.xpath("//div[normalize-space(text())='Change Password']");
	By byClosePopup = By.xpath("//div[@class='close-btn']");

	public void closePopup() {
		u.aClick(byClosePopup);
		u.rep.logInReport("Info", "Closing the popup");
	}
	public void verifyChangePasswordPopupIsDisplayed() {
		u.waitToVisible(byPageHeader);
		try{if (u.isDisplayed(byPageHeader))
			u.rep.logInReport("Pass", "ChangePassword popup is displayed successfully");
		else
			u.rep.logInReport("Fail", "Failed to display ChangePassword popup");
		}catch(Exception e) {
			u.rep.logInReport("Fail", "Failed to display ChangePassword popup");
		}
	}
}
