package smokeTest;

import org.testng.annotations.Test;
import utility.ExcelUtility;
import utility.GenericUtility;

public class SC002_ForgotPassword extends BaseClass {
	ExcelUtility x = new ExcelUtility("Login");

	@Test(enabled = true, priority = 0)
	public void validateSignUpMandatoryErrors() throws Exception {
		u.launchUrl(signInUrl);
		objSignIn.clickForgotPassword();
		objSignIn.verifyForgotPasswordIsDisplayed();
		objSignIn.closeForgotPasswordPopup();
		u.rep.logInReport("info", "Fogot password popup validation completed!");
	}
	@Test(enabled = true, priority=1)
	public void validateSignUpTutorial() throws Exception {
		u.rep.logInReport("info", "Tutorial validation completed!");
		u.launchUrl(prop.getPropData().getProperty("URL"));
		objSignIn.clickOnSignUp();
		objSignUp.clickShortCourses();
		password = x.readData("Password");
		mobileNumber = GenericUtility.getRandomPhoneNumber(x.readData("Country"));
		email = objSignUp.signUpForNewUser(x.readData("FirstName"), x.readData("LastName"), x.readData("Country"), 
				mobileNumber, x.readData("Email"), password);
		objTutorial.verifyIfTutorialPageIsDisplayed();
		objTutorial.clickOnSkipTutorial();
		objDashboard.verifyIfDashboardDisplayed();
		
	}

}
