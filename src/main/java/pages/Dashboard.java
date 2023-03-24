package pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class Dashboard {
	GenericUtility u;

	public Dashboard(GenericUtility u) {
		this.u = u;
	}

	By byDashboardProfilePic = By.xpath("//img[@class='mb-2']");
	By byAthenaId = By.xpath("//p[contains(text(),'Athena ID:')]");
	By byNameGreeting = By.xpath("//h3[@class='strong mb-1']");
	
	By UARewardsValue = By.xpath("//h3[text()='UA Rewards']/following-sibling::div");


	public void verifyIfDashboardDisplayed() {
		u.waitForLoading();
		try {
			if (u.isDisplayed(byDashboardProfilePic))
				u.rep.logInReport("Pass", "Dashboard is displayed");
			else
				u.rep.logInReport("Fail", "Failed to display Dashboard");
		} catch (Exception e) {
			u.rep.logInReport("Fail", "Failed to display Dashboard");//ToDo: Need to handle with listeners
		}
		verifyIfIdIsDisplayed();
	}
	
	public String verifyIfFirstLastNameIsDisplayed(String fName, String lName) {
		u.waitForLoading();
		String actualNameGreeting = "";
		String expectedNameGreeting = "Hi, "+fName+" "+lName;
		try {
			if (u.isDisplayed(byNameGreeting)) {
				actualNameGreeting = u.getText(byNameGreeting);
				if(actualNameGreeting.equals(expectedNameGreeting))
				 u.rep.logInReport("Pass", "FirstName and Last Name is displayed.<br>" + actualNameGreeting);
				else
					u.rep.logInReport("Fail", "Failed to display expected name in Dashboard"
							+ "<br>Actual: " + actualNameGreeting
							+ "<br>Expected: " + expectedNameGreeting);
			} else
				u.rep.logInReport("Fail", "Failed to display expected name in Dashboard");
		} catch (Exception e) {
			u.rep.logInReport("Fail", "Failed to display Dashboard");//ToDo: Need to handle with listeners
		}
		return actualNameGreeting;
	}
	public void verifyIfProfilePicIsDisplayed() {
		u.waitForLoading();
		try {
			if (u.isDisplayed(byDashboardProfilePic))
				u.rep.logInReport("Pass", "Profile pic displayed in Dashboard");
			else
				u.rep.logInReport("Fail", "Failed to display profile pic in Dashboard");
		} catch (Exception e) {
			u.rep.logInReport("Fail", "Failed to display Dashboard");//ToDo: Need to handle with listeners
		}
	}
	public String verifyIfIdIsDisplayed() {
		u.waitForLoading();
		String athenaID = "";
		try {
			if (u.isDisplayed(byAthenaId)) {
				athenaID = u.getText(byAthenaId);
				u.rep.logInReport("Pass", "Athena Id is displayed.<br>" + athenaID);
				u.objUserDetailsRepo.setAthenaId(athenaID);
			} else
				u.rep.logInReport("Fail", "Failed to display Athena Id in Dashboard");
		} catch (Exception e) {
			u.rep.logInReport("Fail", "Failed to display Dashboard");//ToDo: Need to handle with listeners
		}
		return athenaID;
	}
	
	public void verifyUARewardsValue(Boolean isExpectedNotZero) {
		u.waitForLoading();
		String a = u.getText(UARewardsValue);
		Double actual = Double.parseDouble(a);
		if(isExpectedNotZero) {
			if(actual==0) u.rep.logInReport("Fail", "UA Reward is not updated. Expected to be >0"
					+ "<br> Actual: "+a);
			else u.rep.logInReport("Pass", "UA Rewards is not zero");
		}
		else {
			if(actual==0) u.rep.logInReport("Fail", "UA Rewards is not zero");
			else u.rep.logInReport("Pass", "UA Reward is not updated. Expected to be >0"
					+ "<br> Actual: "+a);
		}
	}
}