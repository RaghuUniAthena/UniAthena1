package smokeTest;

import org.testng.annotations.Test;

import utility.DBConnector;
import utility.ExcelUtility;
import utility.GenericUtility;

public class SC004_SearchViewRegisteredCourses extends BaseClass {
	ExcelUtility x = new ExcelUtility("SC004");

	@Test(enabled = true, priority = 0)
	public void SC004_SearchViewRegisteredCourses() throws Exception {
		u.launchUrl(signInUrl);
		email = x.readData("Email");
		password = x.readData("Password");
		objSignIn.loginToApplication(email, password);
		u.objUserDetailsRepo.setAthenaId(x.readData("AthenaID"));
		objDashboard.verifyIfDashboardDisplayed();
		objRegisteredCourses.searchCourseAndValidateResults(x.readData("Course name to be searched"));
		objRegisteredCourses.exitSearch();
		objRegisteredCourses.searchCourseAndValidateResults("dddddddddd",true);
		objRegisteredCourses.exitSearch();
		objRegisteredCourses.verifyCourseCountListed();
		//objRegisteredCourses.verifyCoursePercentageCompletion();// ToDo: Unable to fetch value from webpage
		objRegisteredCourses.validateLastAccessedCourseIsUpdated();
		objRegisteredCourses.validateCourseNamesInCards();
		//objRegisteredCourses.validateLessonsLeftValue();// ToDo: Query to fetch expected count
		objRegisteredCourses.validateIfSharingOptionIsEnabled();
		objRegisteredCourses.validateNavigationsOfSharingOptions();
		objRegisteredCourses.verifyAddCourseCard();
	}
}
