package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class SignIn {
	GenericUtility u;

	public SignIn(GenericUtility u) {
		this.u = u;
	}

	By byShortCourses = By.xpath("//a[normalize-space(text())='Short Courses']");
	By byEmail = By.id("reg_email");
	By byPassword = By.id("password");
	By byEmailError = By.id("mat-error-1");
	By login = By.id("registration_form");
	By bySignUpLink = By.xpath("//a[normalize-space(text())='Sign Up']");
	By bySignInGmail = By.xpath("//div[@class='social-login']//img[contains(@src,'google')]");
	By bySignInFb = By.xpath("//div[@class='social-login']//img[contains(@src,'facebook')]");
	
	By byForgotPassword = By.xpath("//a[normalize-space(text())='Forgot Password ?']");
	By byForgotPasswordPopupEmail = By.xpath("//input[@ng-reflect-name=\"recovery_email\"]");
	By byForgotPasswordPopupCancel = By.xpath("//span[normalize-space(text())='Cancel']");
 
	public void loginToApplication(String usrName, String pWord) {
		u.rep.logInReport("Info", "Signing into the application");
		u.enterTextbox(byEmail, usrName);
		u.enterTextbox(byPassword, pWord);
		u.click(login);
		u.waitForLoading();
	}
	
	public void clickOnSignUp() {
		u.rep.logInReport("Info", "Clicking on Sign up button");
		u.click(bySignUpLink);
	}

	public void clickShortCourses() {
		u.rep.logInReport("Info", "Clicking on 'Short course'");
		u.click(byShortCourses);
	}
	public void clickForgotPassword() {
		u.rep.logInReport("Info", "Clicking on 'Forgot Password' button");
		u.click(byForgotPassword);
	}
	public void verifyForgotPasswordIsDisplayed() {
		u.waitToVisible(byForgotPasswordPopupEmail);
		if(u.isDisplayed(byForgotPasswordPopupEmail))
			u.rep.logInReport("Pass", "Forgot password popup displayed successfully");
		else
			u.rep.logInReport("Fail", "Failed to display Forgot Password popup");
	}
	
	public void closeForgotPasswordPopup() {
		u.click(byForgotPasswordPopupCancel);
	}

}
