package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import utility.GenericUtility;

public class SignUp {
	GenericUtility u;

	public SignUp(GenericUtility u) {
		this.u = u;
	}

	public static final String FIRSTNAME_ERROR = "First Name is required";
	public static final String LASTNAME_ERROR = "Last name is required";
	public static final String EMAIL_ERROR = "Email ID is required";
	public static final String PASSWORD_ERROR = "Password is required";
	public static final String TERMS_CONDITION_ERROR = "Terms and condition is required";

	By byShortCourses = By.xpath("//a[normalize-space(text())='Short Courses']");
	By byFirstName = By.id("reg_first_name");
	By byLastName = By.id("reg_last_name");
	By byEmail = By.id("reg_email");
	By byCountryCode = By
			.xpath("//input[@id='contact_number']/preceding-sibling::*//div[@class='selected-flag dropdown-toggle']");
	By byCountryCodeDropdownValues = By
			.xpath("//input[@id='contact_number']/preceding-sibling::*//span[@class='country-name']");
	By byCountryCodeSearchBox = By.id("country-search-box");
	By byCountryCodeDropdownValuesByCountry;
	By byMobileNo = By.id("contact_number");
	By byMobileNo_Error_Invalid = By.xpath("//mat-error[normalize-space(text())='Invalid Mobile No']");
	By byPassword = By.id("password");
	By byTermsAndConditions = By.id("reg_terms");
	By bySignUpButton = By.id("registration_form");
	By byMandatoryErrors = By.xpath("//mat-error[@role='alert'  and text()]");

	public String signUpForNewUser(String fName, String lName, String country, String mobileNo, String email,
			String pWord) {
		email = email.replace("<>", "_" + u.getRandomValue());
		u.enterTextbox(byFirstName, fName);
		u.enterTextbox(byLastName, lName);
		u.enterTextbox(byEmail, email);
		u.click(byCountryCode);

		u.aClick(byCountryCodeSearchBox);
		u.enterTextbox(byCountryCodeSearchBox, country);
		u.waitTime(2);
		try {
			byCountryCodeDropdownValuesByCountry = By.xpath(
					"//input[@id='contact_number']/preceding-sibling::*//span[@class='country-name' and contains(text(),\""
							+ country + " (\")]");
			u.getDriver().findElement(byCountryCodeDropdownValuesByCountry).click();
		} catch (Exception e) { 
			byCountryCodeDropdownValuesByCountry = By
					.xpath("//input[@id='contact_number']/preceding-sibling::*//span[@class='country-name' and text()='"
							+ country + "']");
			u.click(byCountryCodeDropdownValuesByCountry);
		}
		u.enterTextbox(byMobileNo, mobileNo);
		u.enterTextbox(byPassword, pWord);
		try {
			if (u.isDisplayed(byMobileNo_Error_Invalid)) {
				mobileNo = GenericUtility.getRandomPhoneNumber(country);
				u.enterTextbox(byMobileNo, mobileNo);
			}
		} catch (Exception e) {
		}
		u.click(byTermsAndConditions);
		u.click(bySignUpButton);
		u.rep.logInReport("Info",
				"SignUp initiated for user:" + "<br>Email: " + email + "<br> Password: " + pWord + "");
		u.waitForLoading();
		return email;
	}

	public void clickShortCourses() {
		u.aClick(byShortCourses);
	}

	public void clickOnSignUpButton() {
		u.aClick(bySignUpButton);
	}

	public void validateMandatoryErrors() {
		List<WebElement> errors = u.elements(byMandatoryErrors);
		if (errors.get(0).getText().equals(FIRSTNAME_ERROR))
			u.rep.logInReport("Pass", "Mandatory error message displayed for First Name field");
		else
			u.rep.logInReport("Fail", "Failed to display Mandatory Error message" + "<br>Expected: " + FIRSTNAME_ERROR);
		if (errors.get(1).getText().equals(LASTNAME_ERROR))
			u.rep.logInReport("Pass", "Mandatory error message displayed for Last Name field");
		else
			u.rep.logInReport("Fail", "Failed to display Mandatory Error message" + "<br>Expected: " + LASTNAME_ERROR);
		if (errors.get(2).getText().equals(EMAIL_ERROR))
			u.rep.logInReport("Pass", "Mandatory error message displayed for Email ID field");
		else
			u.rep.logInReport("Fail", "Failed to display Mandatory Error message" + "<br>Expected: " + EMAIL_ERROR);
		if (errors.get(3).getText().equals(PASSWORD_ERROR))
			u.rep.logInReport("Pass", "Mandatory error message displayed for Password field");
		else
			u.rep.logInReport("Fail", "Failed to display Mandatory Error message" + "<br>Expected: " + PASSWORD_ERROR);
		if (errors.get(4).getText().equals(TERMS_CONDITION_ERROR))
			u.rep.logInReport("Pass", "Mandatory error message displayed for Terms & Condition checkbox");
		else
			u.rep.logInReport("Fail",
					"Failed to display Mandatory Error message" + "<br>Expected: " + TERMS_CONDITION_ERROR);

	}

}
