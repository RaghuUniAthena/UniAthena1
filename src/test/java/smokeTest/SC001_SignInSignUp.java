package smokeTest;

import org.testng.annotations.Test;
import utility.ExcelUtility;
import utility.GenericUtility;

public class SC001_SignInSignUp extends BaseClass {
	ExcelUtility x = new ExcelUtility("Login");

	@Test(enabled = true, priority=0)
	public void validateSignUpMandatoryErrors() throws Exception {
		u.rep.logInReport("info", "Test to validate Mandatory messages in Sign up page");
		u.launchUrl(signInUrl);
		objSignIn.clickOnSignUp();
		objSignUp.clickShortCourses();
		objSignUp.clickOnSignUpButton();
		objSignUp.validateMandatoryErrors();
		u.rep.logInReport("info", "Mandatory message validation completed!");
	}
	
	@Test(enabled = true, priority=1)
	public void validateSignUp() throws Exception {
		u.rep.logInReport("info", "Test to validate Sign up");
		u.launchUrl(prop.getPropData().getProperty("URL"));
		objSignIn.clickOnSignUp();
		objSignUp.clickShortCourses();
		email = x.readData("Email");
		mobileNumber=GenericUtility.getRandomPhoneNumber(x.readData("Country"));
		password = x.readData("Password");
		email = objSignUp.signUpForNewUser(x.readData("FirstName"), x.readData("LastName"), x.readData("Country"), 
				mobileNumber, email, password);
		objDashboard.verifyIfDashboardDisplayed();
		u.rep.logInReport("info", "Sign up validation completed!");
		
	}
	@Test(enabled = true, priority=2)
	public void validateSignIn() throws Exception {
		u.rep.logInReport("info", "Test to validate Sign in");
		u.launchUrl(prop.getPropData().getProperty("URL"));
		objSignIn.loginToApplication(email,password);
		objDashboard.verifyIfDashboardDisplayed();
		u.rep.logInReport("info", "Sign in validation completed!");
	}

	
}
