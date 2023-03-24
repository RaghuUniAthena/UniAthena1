package pages;

import org.openqa.selenium.By;
import utility.GenericUtility;

public class Admin_SettledPayments {
	GenericUtility u;

	public Admin_SettledPayments(GenericUtility u) {
		this.u = u;
	}

	By bySearchbyDropdown = By.xpath("(//mat-select)[1]");
	By bySearchbyDropdown_OptionOrderId = By
			.xpath("//span[@class='mat-option-text' and normalize-space(text())='Order Id']");
	By byOrderId = By.id("mat-input-0");
	By byOrderIdSubmit = By.xpath("(//button/span[normalize-space(text())='Submit'])[1]");
	
	By bySearchbyDropdown_Email = By.xpath("(//mat-select)[1]");
	By bySearchbyDropdown_OptionEmailId = By.xpath("//span[@class='mat-option-text' and normalize-space(text())='Email ID']");
	By byEmailId = By.xpath("//input[@placeholder='Email Id']");
	By byEmailIdSubmit = By.xpath("(//button/span[normalize-space(text())='Submit'])[1]");
	

	public void searchByOrderId(String orderId) {
		u.waitForLoading_Admin();
		u.aClick(bySearchbyDropdown);
		u.waitTime(3);
		u.aClick(bySearchbyDropdown_OptionOrderId);
		u.waitTime(1);
		u.enterTextbox(byOrderId, orderId);
		u.waitTime(1);
		u.aClick(byOrderIdSubmit);

		u.rep.logInReport("Info", "Searching by Order id in Settled Page<br>Order id: " + orderId);
	}
	public void searchByEmailId(String emailId) {
		u.waitForLoading_Admin();
		u.aClick(bySearchbyDropdown_Email);
		u.waitTime(3);
		u.aClick(bySearchbyDropdown_OptionEmailId);
		u.waitTime(1);
		u.enterTextbox(byEmailId, emailId);
		u.waitTime(1);
		u.aClick(byEmailIdSubmit);
		
		u.rep.logInReport("Info","Searching by Email id<br>Email id: "+emailId);
	}

	public void verifyIfOrderIdIsSettled(String orderId, Boolean bExpectedToDisplay) {
		By dynamicXpath_Apply = By.xpath("//td[normalize-space(text())='" + orderId + "']");
		if (bExpectedToDisplay) {
			if (u.isDisplayed(dynamicXpath_Apply))
				u.rep.logInReport("Pass",
						"Order Id is settled and is displayed in Settled Payments page" + "<br>Order Id: " + orderId);
			else
				u.rep.logInReport("Fail", "Order id is not settled" + "<br>Order Id: " + orderId);
		} else {
			if (!u.isDisplayed(dynamicXpath_Apply))
				u.rep.logInReport("Pass",
						"Order Id is settled and is NOT displayed in Settled Payments page" + "<br>Order Id: " + orderId);
			else
				u.rep.logInReport("Fail", "Order id is settled" + "<br>Order Id: " + orderId);
		}
	}
	public void verifyIfEmailIdIsSettled(String emailId, Boolean bExpectedToDisplay) {
		By dynamicXpath_Apply = By.xpath("//td[normalize-space(text())='" + emailId + "']");
		if (bExpectedToDisplay) {
			if (u.isDisplayed(dynamicXpath_Apply))
				u.rep.logInReport("Pass",
						"Email Id is settled and is displayed in Settled Payments page" + "<br>Email Id: " + emailId);
			else
				u.rep.logInReport("Fail", "Email id is not settled" + "<br>Email Id: " + emailId);
		} else {
			if (!u.isDisplayed(dynamicXpath_Apply))
				u.rep.logInReport("Pass",
						"Email Id is settled and is NOT displayed in Settled Payments page" + "<br>Email Id: " + emailId);
			else
				u.rep.logInReport("Fail", "Email id is settled" + "<br>Email Id: " + emailId);
		}
	}
	
}
