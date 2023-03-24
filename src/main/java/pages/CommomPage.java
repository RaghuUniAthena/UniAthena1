package pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class CommomPage {
	GenericUtility u;

	public CommomPage(GenericUtility u) {
		this.u = u;
	}

	By byLoading = By.xpath("//img[@class='ath-spinner']");	
	By byProfileIconDropdown = By.xpath("//img[@src='assets/discussion-forum/dropdown.svg']");
	By byProfileIconDropdownOptions(String option) {
		switch (option.toUpperCase()) {
		case "MY PROFILE":
			return By.xpath("//p[normalize-space(text())='My Profile']");
		case "CHANGE PASSWORD":
			return By.xpath("//p[normalize-space(text())='Change Password']");
		case "CONTACT US":
			return By.xpath("//p[normalize-space(text())='Contact Us']");
		case "FAQ":
			return By.xpath("//p[normalize-space(text())='FAQ']");
		case "LOGOUT":
			return By.xpath("//p[normalize-space(text())='Logout']");
		default:
			return By.xpath("Invalid option:" + option);
		}
	}
	
	public void clickOptionFromProfileDropdown(String option) {
		u.aClick(byProfileIconDropdown);
		u.aClick(byProfileIconDropdownOptions(option));
		u.waitForLoading();
	}
	
	public void waitForLoading() {
		u.waitUntilInvisible(byLoading);
	}
	public void validateProfileIconDropdowns() {
		u.aClick(byProfileIconDropdown);
		List<String> options = new ArrayList<String>();
		options.addAll(Arrays.asList("My profile", "Change Password", "Contact Us", "FAQ", "Logout"));
		for (String op : options) {
			try {
				if (u.isDisplayed(byProfileIconDropdownOptions(op)))
					u.rep.logInReport("Pass", op+": Option is displayed");
				else
					u.rep.logInReport("Fail", "Failed to display option: "+op);
			} catch (Exception e) {
				u.rep.logInReport("Fail", "Failed to display option "+op);// ToDo: Need to handle with listeners
			}
		}
	}



	
	

}