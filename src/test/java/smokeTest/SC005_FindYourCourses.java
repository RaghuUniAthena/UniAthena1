package smokeTest;

import org.testng.annotations.Test;

import utility.DBConnector;
import utility.ExcelUtility;
import utility.GenericUtility;

public class SC005_FindYourCourses extends BaseClass {
	ExcelUtility x = new ExcelUtility("SC004");
	String[] categories_FindYourCourse = {"Trending",
			"Newly Released",
			"All",
			"Basics",
			"Essentials",
			"MasteringDiploma",
			"Executive Diploma",
			"MBA Essentials"};

	@Test(enabled = true, priority = 0)
	public void SC004_SearchViewRegisteredCourses() throws Exception {
		u.launchUrl(signInUrl);
		email = x.readData("Email");
		password = x.readData("Password");
		objSignIn.loginToApplication(email, password);
		u.objUserDetailsRepo.setAthenaId(x.readData("AthenaID"));
		objDashboard.verifyIfDashboardDisplayed();
		objFindYourCourses.verifyIfCategoriesAreDisplayed(categories_FindYourCourse);
//		objFindYourCourses.verifyIfUserCanSelectCategories();
//		objFindYourCourses.verifyIfSearchWorksProperly();
//		objFindYourCourses.verifyIfSpecializationsAreAvailalble();
//		objFindYourCourses.verifyCurrentlySelectedField();
//		objFindYourCourses.verifyClearFilterFunctionlity();
		
		
	}
}
