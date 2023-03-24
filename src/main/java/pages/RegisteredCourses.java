package pages;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import utility.DBConnector;
import utility.GenericUtility;

public class RegisteredCourses {
	GenericUtility u;

	public RegisteredCourses(GenericUtility u) {
		this.u = u;
	}

	By byRegisteredCourseTitle = By.xpath("//div[@class='row reg-courses']//h2");
	By byLastAccessedCourseLabel = By.xpath("//span[@class='last-accessed-course']/parent::p");
	By byLastAccessedCourse = By.xpath("//span[@class='last-accessed-course']");

	By bySearchInput = By.xpath("//input[@placeholder='Search your Registered Course']");
	By bySearchIcon = By.xpath(
			"//input[@placeholder='Search your Registered Course']/following-sibling::*//span[contains(@class,'find')]");
	By bySearchClose = By.xpath("//span[normalize-space(text())='close']");
	By bySearchNoResult = By.xpath("//p[text()='No Search Results Found!!']");

	By byList_TotalTiles = By
			.xpath("//div[@class='course-details']/ancestor::div[@class='carousel-cell ng-star-inserted']");
	By byList_TotalTileHeaders = By
			.xpath("//div[@class='carousel-cell ng-star-inserted']//div[@class='course-details']//h3");
	By byList_TotalTilePercentage = By
			.xpath("//div[@class='course-details']/ancestor::div[@class='carousel-cell ng-star-inserted']");
	By byList_TotalTileCourseDetailsHoverButton = By.xpath(
			"//div[contains(@class,'course-item-hover')]//button[@class='goto-course' and normalize-space(text())='Go to Course Details']");

	By byList_LessonsLearnedLabels = By.xpath("//p[normalize-space(text())='Lessons Left']");
	By byList_LessonsLearnedValues = By.xpath("//p[normalize-space(text())='Lessons Left']/preceding-sibling::p");
	By byList_LessonsLearnedCourseNames = By
			.xpath("//p[normalize-space(text())='Lessons Left']/ancestor::div[@class='course-details']//h3");

	By byList_TileShareFacebook = By.xpath(
			"//div[@class='course-details']/ancestor::div[@class='carousel-cell ng-star-inserted']//a/img[contains(@src,'facebook')]");
	By byList_TileShareTwitter = By.xpath(
			"//div[@class='course-details']/ancestor::div[@class='carousel-cell ng-star-inserted']//a/img[contains(@src,'twitter')]");
	By byList_TileShareLinkedin = By.xpath(
			"//div[@class='course-details']/ancestor::div[@class='carousel-cell ng-star-inserted']//a/img[contains(@src,'linkedin')]");
	By byList_TileShareWhatsapp = By.xpath(
			"//div[@class='course-details']/ancestor::div[@class='carousel-cell ng-star-inserted']//a/img[contains(@src,'whatsapp')]");
	By byList_TileShareEmail = By.xpath(
			"//div[@class='course-details']/ancestor::div[@class='carousel-cell ng-star-inserted']//a/img[contains(@src,'email')]");

	By byLoading_RegisteredCouses = By.xpath("//img[@alt='Registered Courses']");
	By byPageCounts = By.xpath("//ul[@class='pagination']/li");

	By byAddCourse = By.xpath("//div[@class='addCourse-img text-center']");

	public void searchCourseAndValidateResults(String courseName) {
		searchCourseAndValidateResults(courseName, false);
	}

	public void searchCourseAndValidateResults(String courseName, Boolean expectedIsNoResultFound) {
		u.aClick(bySearchInput);
		u.enterTextbox(bySearchInput, courseName);
		u.aClick(bySearchIcon);
		u.waitUntilInvisible(byLoading_RegisteredCouses);

		if (!expectedIsNoResultFound) {
			Boolean bSearchWorksFine = true;

			String actualHeader = "";
			List<WebElement> eTotalTileHeaders = u.elements(byList_TotalTileHeaders);
			for (WebElement eHeader : eTotalTileHeaders) {
				actualHeader = eHeader.getText();
				if (!actualHeader.toLowerCase().contains(courseName.toLowerCase())) {
					bSearchWorksFine = false;
				}
			}
			if (bSearchWorksFine)
				u.rep.logInReport("Pass", "Search works as expected for keyword: " + courseName);
			else
				u.rep.logInReport("Fail", "Failed to display search result based on the keyword '" + courseName + "'"
						+ "<br>Course listed incorrectly: " + actualHeader);
		} else {
			if (u.isDisplayed(bySearchNoResult))
				u.rep.logInReport("Pass",
						"'No result found' message is displayed when no matches are found.<br>Course Name:  "
								+ courseName);
			else
				u.rep.logInReport("Fail",
						"Failed to display 'No result found' message when no matches are available.<br>Course Name: '"
								+ courseName);
		}
	}

	public void exitSearch() {
		u.aClick(bySearchClose);
		u.waitForLoading();
		u.waitUntilInvisible(byLoading_RegisteredCouses);
		// u.getDriver().navigate().refresh();
	}

	public void validateLastAccessedCourseIsUpdated() {
		String expectedLabel = "Last accessed course: ";
		if (u.getText(byLastAccessedCourseLabel).equals(expectedLabel))
			u.rep.logInReport("Pass",
					"Last accessed label displayed correctly as " + u.getText(byLastAccessedCourseLabel));
		else
			u.rep.logInReport("Fail", "Failed to display Last accessed label correctly" + "<br>Expected: "
					+ expectedLabel + "<br>Actual: " + u.getText(byLastAccessedCourseLabel));

		String oldLastAccessedCourse = u.getText(byLastAccessedCourse);
		String expectedLastAccessedCourse = "";
//		Random rn = new Random();
//		int randomTileNumber = rn.nextInt(12 - 1 + 1) + 1;
		List<WebElement> eTileHeaders = u.elements(byList_TotalTileHeaders);
		for (int i = 0; i < eTileHeaders.size(); i++) {
			expectedLastAccessedCourse = eTileHeaders.get(i).getText();
			if (!expectedLastAccessedCourse.equals(oldLastAccessedCourse)) {
				try {
					u.elements(byList_TotalTilePercentage).get(i).click();
				} catch (Exception e) {
					String dynamicXpath_BtnGoToCourseDetails = "(//div[@class='course-details']/ancestor::div[@class='carousel-cell ng-star-inserted'])["
							+ i
							+ "]//div[contains(@class,'course-item-hover')]//button[@class='goto-course' and normalize-space(text())='Go to Course Details']";
					u.aClick(By.xpath(dynamicXpath_BtnGoToCourseDetails));
				}
				break;
			}
		}
		u.waitForLoading();
		CourseDetails objCourseDetails = new CourseDetails(u);
		objCourseDetails.verifyCourseDetailsPageIsDisplayed();
		objCourseDetails.navigateBackToDashboard();
		u.waitForLoading();
		String newLastAccessedCourseDetails = u.getText(byLastAccessedCourse);
		if (!oldLastAccessedCourse.equals(newLastAccessedCourseDetails))
			u.rep.logInReport("Pass",
					"Last accessed course name is displayed as expected: " + newLastAccessedCourseDetails);
		else
			u.rep.logInReport("Fail", "Failed to display course count correctly " + "<br>Expected: "
					+ expectedLastAccessedCourse + "<br>Actual: " + newLastAccessedCourseDetails);
	}

	public void verifyCourseCountListed() throws NumberFormatException, ClassNotFoundException, SQLException {
		String actualCourseCount = u.getText(byRegisteredCourseTitle).replace("Registered Courses ", "")
				.replace("-", "").trim();
		if (Integer.parseInt(actualCourseCount) == getCourseCountFromDB())
			u.rep.logInReport("Pass",
					"The label 'Registered Courses  - ' is displayed along with the number of enrolled courses");
		else
			u.rep.logInReport("Fail", "Failed to display course count correctly " + "<br>Expected: "
					+ getCourseCountFromDB() + "<br>Actual: " + actualCourseCount);
	}

	public void verifyCoursePercentageCompletion() throws NumberFormatException, ClassNotFoundException, SQLException {
		String actualPercentageCompletion = "";
		String courseName = "";
		String cid = "";

		u.waitForLoading();
		u.waitUntilInvisible(byLoading_RegisteredCouses);
		u.waitTime(7);
		List<WebElement> eTileHeaders = u.elements(byList_TotalTileHeaders);
		for (int i = 0; i < eTileHeaders.size(); i++) {
			By dynamicTilePercentage = By.xpath("(//div[contains(@class,'course-area')]/span)[" + (i+1) + "]");
//			actualPercentageCompletion = u.getText(dynamicTilePercentage);
			actualPercentageCompletion = u.getDriver().findElement(dynamicTilePercentage).getAttribute("");
			courseName = eTileHeaders.get(i).getText();
			try {
				u.elements(byList_TotalTilePercentage).get(i).click();
			} catch (Exception e) {
				String dynamicXpath_BtnGoToCourseDetails = "(//div[@class='course-details']/ancestor::div[@class='carousel-cell ng-star-inserted'])["
						+ i+1
						+ "]//div[contains(@class,'course-item-hover')]//button[@class='goto-course' and normalize-space(text())='Go to Course Details']";
				u.aClick(By.xpath(dynamicXpath_BtnGoToCourseDetails));
			}
			u.waitForLoading(); 
			u.waitTime(3);
			cid = new CourseDetails(u).getCourseCode();
						u.getDriver().navigate().back();
			if (actualPercentageCompletion == getCoursePercentageCompletionFromDB(cid))
				u.rep.logInReport("Pass", "Percentage of completion is listed as expected."
						+ "<br>CourseName: "+courseName
						+"<br>Value: "+actualPercentageCompletion);
			else
				u.rep.logInReport("Fail", "Failed to display course percentage completion correctly " + "<br>Expected: "
						+ getCoursePercentageCompletionFromDB(cid) + "<br>Actual: " + actualPercentageCompletion);
			
		}
	}

	public void validateCourseNamesInCards() {
		u.waitForLoading();
		u.waitUntilInvisible(byLoading_RegisteredCouses);
		u.waitTime(5);
		String actualCourseName = "";
		List<WebElement> eTileHeaders = u.elements(byList_TotalTileHeaders);
		for (int i = 0; i < eTileHeaders.size(); i++) {
			actualCourseName = eTileHeaders.get(i).getText();
			try {
				u.elements(byList_TotalTilePercentage).get(i).click();
			} catch (Exception e) {
				String dynamicXpath_BtnGoToCourseDetails = "(//div[@class='course-details']/ancestor::div[@class='carousel-cell ng-star-inserted'])["
						+ i
						+ "]//div[contains(@class,'course-item-hover')]//button[@class='goto-course' and normalize-space(text())='Go to Course Details']";
				u.aClick(By.xpath(dynamicXpath_BtnGoToCourseDetails));
			}

			u.waitForLoading();
			CourseDetails objCourseDetails = new CourseDetails(u);
			objCourseDetails.verifyCourseDetailsPageIsDisplayed();
			String expectedCourseName = objCourseDetails.getCourseName();
			objCourseDetails.navigateBackToDashboard();
			u.waitForLoading();
			if (expectedCourseName.trim().toLowerCase().equalsIgnoreCase(actualCourseName.trim().toLowerCase()))
				u.rep.logInReport("Pass",
						"Course name in cards are displayed as expected for course: " + actualCourseName);
			else
				u.rep.logInReport("Fail", "Failed to display Course name in cards correctly " + "<br>Expected: "
						+ expectedCourseName + "<br>Actual: " + actualCourseName);
		}
	}

	public int getCourseCountFromDB() throws ClassNotFoundException, SQLException {
		DBConnector d = new DBConnector();
		return d.getSingleColumnDataCount(d.SelectFromCourseEnrollment_Uid(u.objUserDetailsRepo.getAthenaId()));
	}

	public String getCoursePercentageCompletionFromDB(String courseId) throws ClassNotFoundException, SQLException {
		DBConnector d = new DBConnector();
		return d.SelectCoursePercentage_UID_CID(u.objUserDetailsRepo.getAthenaId(), courseId);
	}

	public String getCoursePaymentStatusFromDB(String courseId) throws ClassNotFoundException, SQLException {
		DBConnector d = new DBConnector();
		return d.Select_IsPaid_FromCourseEnrolment_UID_CID(u.objUserDetailsRepo.getAthenaId(), courseId);
	}

	public int getPageCount() {
		List<WebElement> ePageCount = u.elements(byPageCounts);
		return ePageCount.size();
	}

	public void validateLessonsLeftValue() {
		// ToDo: Handle using DB: Fetch lessons left query
		List<WebElement> eLessonLearnedValues = u.elements(byList_LessonsLearnedValues);
		List<WebElement> eLessonLearnedCourseNames = u.elements(byList_LessonsLearnedCourseNames);
		for (int i = 0; i < eLessonLearnedValues.size(); i++) {
			String expected = "";// ToDo: Query to fetch expected count
			String actual = eLessonLearnedValues.get(i).getText();
			if (expected.equals(actual))
				u.rep.logInReport("Pass", "Lessons learnt count in cards are displayed as expected for course:<b> "
						+ eLessonLearnedCourseNames.get(i).getText() + "<\b>");
			else
				u.rep.logInReport("Fail",
						"Failed to display Lessons learnt count in cards correctly for couse '"
								+ eLessonLearnedCourseNames.get(i).getText() + "'<br>Expected: " + expected
								+ "<br>Actual: " + actual);
		}
	}

	public void validateIfSharingOptionIsEnabled() throws ClassNotFoundException, SQLException {
		List<WebElement> eTileHeaders = u.elements(byList_TotalTileHeaders);
		List<WebElement> eTileShareFacebook = u.elements(byList_TileShareFacebook);
		List<WebElement> eTileShareTwitter = u.elements(byList_TileShareTwitter);
		List<WebElement> eTileShareLinkedIn = u.elements(byList_TileShareLinkedin);
		List<WebElement> eTileShareWhatsapp = u.elements(byList_TileShareWhatsapp);
		List<WebElement> eTileShareEmail = u.elements(byList_TileShareEmail);

		for (int i = 0; i < eTileHeaders.size(); i++) {
			Boolean bPaymentPending = true;
			u.aClick(byList_TotalTileCourseDetailsHoverButton);
			u.waitForLoading();
			new CourseDetails(u).getCourseCode();
			String isPaid = getCoursePaymentStatusFromDB(u.objUserDetailsRepo.getCourseId());
			new CourseDetails(u).navigateBackToDashboard();
			u.waitForLoading();
			if (isPaid == "0")
				bPaymentPending = false;

			if (bPaymentPending) {
				String courseName = eTileHeaders.get(i).getText();
				u.aHover(eTileHeaders.get(i));
				if (eTileShareFacebook.get(i).isDisplayed() && eTileShareTwitter.get(i).isDisplayed()
						&& eTileShareLinkedIn.get(i).isDisplayed() && eTileShareWhatsapp.get(i).isDisplayed()
						&& eTileShareEmail.get(i).isDisplayed())
					u.rep.logInReport("Pass", "Share icons are displayed as expected for course card: " + courseName);
				else if (!eTileShareFacebook.get(i).isDisplayed())
					u.rep.logInReport("Fail", "Failed to display Facebook share icon in course card: " + courseName);
				else if (!eTileShareTwitter.get(i).isDisplayed())
					u.rep.logInReport("Fail", "Failed to display Twitter share icon in course card: " + courseName);
				else if (!eTileShareLinkedIn.get(i).isDisplayed())
					u.rep.logInReport("Fail", "Failed to display LinkedIn share icon in course card: " + courseName);
				else if (!eTileShareWhatsapp.get(i).isDisplayed())
					u.rep.logInReport("Fail", "Failed to display Whatsapp share icon in course card: " + courseName);
				else if (!eTileShareEmail.get(i).isDisplayed())
					u.rep.logInReport("Fail", "Failed to display Email share icon in course card: " + courseName);
			}
		}

	}

	public void validateNavigationsOfSharingOptions() throws ClassNotFoundException, SQLException {
		List<WebElement> eTileHeaders = u.elements(byList_TotalTileHeaders);
		List<WebElement> eTileShareFacebook = u.elements(byList_TileShareFacebook);
		List<WebElement> eTileShareTwitter = u.elements(byList_TileShareTwitter);
		List<WebElement> eTileShareLinkedIn = u.elements(byList_TileShareLinkedin);
		List<WebElement> eTileShareWhatsapp = u.elements(byList_TileShareWhatsapp);
		List<WebElement> eTileShareEmail = u.elements(byList_TileShareEmail);

		int i = 2;
		// for (int i = 0; i < eTileHeaders.size(); i++) {
		Boolean bPaymentPending = true;
		u.aClick(byList_TotalTileCourseDetailsHoverButton);
		u.waitForLoading();
		new CourseDetails(u).getCourseCode();
		String isPaid = getCoursePaymentStatusFromDB(u.objUserDetailsRepo.getCourseId());
		new CourseDetails(u).navigateBackToDashboard();
		u.waitForLoading();
		if (isPaid == "0")
			bPaymentPending = false;

		if (bPaymentPending) {
			String courseName = eTileHeaders.get(i).getText();
			u.aHover(eTileHeaders.get(i));

			// facebook
			u.aClick(eTileShareFacebook.get(i));
			String oldTab = u.switchToNextTab();
			if (u.getDriver().getCurrentUrl().equalsIgnoreCase("https://www.facebook.com/")) {
				u.rep.logInReport("Pass", "Facebook navigation successful");
				u.getDriver().close();
				u.getDriver().switchTo().window(oldTab);
			} else
				u.rep.logInReport("Fail", "Facebook navigation failed");

			// Twitter
			u.aClick(eTileShareTwitter.get(i));
			oldTab = u.switchToNextTab();
			if (u.getDriver().getCurrentUrl().equalsIgnoreCase("https://twitter.com/")) {
				u.rep.logInReport("Pass", "Twitter navigation successful");
				u.getDriver().close();
				u.getDriver().switchTo().window(oldTab);
			} else
				u.rep.logInReport("Fail", "Twitter navigation failed");

			// Linkedin
			u.aClick(eTileShareLinkedIn.get(i));
			oldTab = u.switchToNextTab();
			if (u.getDriver().getCurrentUrl().equalsIgnoreCase("https://www.linkedin.com/")) {
				u.rep.logInReport("Pass", "Linkedin navigation successful");
				u.getDriver().close();
				u.getDriver().switchTo().window(oldTab);
			} else
				u.rep.logInReport("Fail", "Linkedin navigation failed");

			// Whatsapp
			u.aClick(eTileShareWhatsapp.get(i));
			oldTab = u.switchToNextTab();
			if (u.getDriver().getCurrentUrl().equalsIgnoreCase("https://api.whatsapp.com/")) {
				u.rep.logInReport("Pass", "Whatsapp navigation successful");
				u.getDriver().close();
				u.getDriver().switchTo().window(oldTab);
			} else
				u.rep.logInReport("Fail", "Whatsapp navigation failed");

//					// Email
//					u.aClick(eTileShareFacebook.get(i));
//					String oldTab = u.switchToNextTab();
//					if (u.getDriver().getCurrentUrl().equalsIgnoreCase("https://www.facebook.com/")) {
//						u.rep.logInReport("Pass", "Email navigation successful");
//						u.getDriver().close();
//						u.getDriver().switchTo().window(oldTab);
//					}else
//						u.rep.logInReport("Fail", "Email navigation failed");
		}
	}

	// }

	public void clickClaimCertificate(String courseName) {
		By dynaicXPath_ClaimCertificate = By.xpath("//h3[text()=\"" + courseName + "\"]/ancestor::"
				+ "div[@class='course-item ng-star-inserted']//button[normalize-space(text())='Claim Certificate']");
		if (u.isDisplayed(dynaicXPath_ClaimCertificate))
			u.rep.logInReport("Pass",
					"'Claim Certificate' button displayed in Regidtered courses page<br>Course: " + courseName);
		else
			u.rep.logInReport("Fail",
					"'Claim Certificate' button is NOT displayed in Regidtered courses page <br>Course: " + courseName);
		u.rep.logInReport(true);
		for (int i = 0; i < 3; i++) {
			try {
				if (u.isDisplayed(dynaicXPath_ClaimCertificate)) {
					u.aClick(dynaicXPath_ClaimCertificate);
					u.waitForLoading();
				} else
					break;
			} catch (Exception e) {
				break;
			}
		}
		u.waitForLoading();
		u.rep.logInReport("Info", "Clicking on claim certificate from course card of 'Registered course' screen");

	}

	public void verifyAlreadyClaimedButtonIsDisplayed(String courseName) {
		By dynamicXpath_AlreadyClaimedButton = By.xpath("//h3[normalize-space(text())=\"" + courseName
				+ "\"]/ancestor::div[@class='course-details']//button[normalize-space(text())='Already Claimed']");
		u.waitTime(3);
		u.waitForLoading();
		u.waitTime(3);
		if (u.isDisplayed(dynamicXpath_AlreadyClaimedButton))
			u.rep.logInReport("Pass",
					"Already claimed button displayed in Regidtered courses page <br>Course: " + courseName);
		else
			u.rep.logInReport("Fail",
					"Already claimed button is NOT displayed in Regidtered courses page<br>Course: " + courseName);
		u.rep.logInReport(true);
	}

	public void verifyAddCourseCard() {
		u.waitForLoading();
		if (u.isDisplayed(byAddCourse)) {
			u.click(byAddCourse);
			if (u.isDisplayed(new FindYourCourses(u).byTrendingTab))
				u.rep.logInReport("Pass",
						"'Trending' tab under 'Find your course' page is displayed on clicking 'Add course' button from registered courses page");
			else

				u.rep.logInReport("Fail",
						"'Trending' tab under 'Find your course' page is NOT displayed on clicking 'Add course' button from registered courses page");
		} else
			u.rep.logInReport("Fail", "'Add Course' card is NOT displayed under Registered Courses page");

	}

}
