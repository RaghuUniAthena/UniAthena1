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

public class FindYourCourses {
	GenericUtility u;

	public FindYourCourses(GenericUtility u) {
		this.u = u;
	}

	By byFindYourCourseTitle = By.xpath("//div[@class='row reg-courses']//h2");

	By bySearchInput = By.xpath("//input[@placeholder='What do you want to learn']");
	By bySearchIcon = By.xpath(
			"//input[@placeholder='What do you want to learn']/following-sibling::*//span[contains(@class,'find')]");
	By bySearchClose = By.xpath("//span[normalize-space(text())='close']']");

	By byList_TotalTiles = By.xpath("//div[@class='flip-card ng-star-inserted']");
	By byList_TotalTileHeaders = By.xpath("//div[@class='flip-card ng-star-inserted']//h3");
	By byList_TotalTilePercentage = By.xpath("//div[@class='course-area text-center']");
	By byList_TotalTileCourseDetailsHoverButton = By.xpath(
			"//div[contains(@class,'course-item-hover')]//button[@class='goto-course' and normalize-space(text())='Go to Course Details']");

	By byCourseTile_1_StartNow = By
			.xpath("(//div[@class='flip-card ng-star-inserted'] //button[text()='Start Now'])[1]");

	By byList_TileShareFacebook = By
			.xpath("//div[@class='flip-card ng-star-inserted']//a/img[contains(@src,'facebook')]");
	By byList_TileShareTwitter = By
			.xpath("//div[@class='flip-card ng-star-inserted']//a/img[contains(@src,'twitter')]");
	By byList_TileShareLinkedin = By
			.xpath("//div[@class='flip-card ng-star-inserted']//a/img[contains(@src,'linkedin')]");
	By byList_TileShareWhatsapp = By
			.xpath("//div[@class='flip-card ng-star-inserted']//a/img[contains(@src,'whatsapp')]");
	By byList_TileShareEmail = By.xpath("//div[@class='flip-card ng-star-inserted']//a/img[contains(@src,'email')]");

	By byLoading_FindYourCouses = By.xpath("//img[@alt='FindYour Courses']");
	By byPageCounts = By.xpath("//ul[@class='pagination']/li");

	By byTrendingTab = By.xpath("//a[@class='certification-types-items active ng-star-inserted']");
	By byList_Categories = By.xpath("//div[@id='findCourse']/following-sibling::*//a//span");
	
	
	public void searchCourseAndValidateResults(String courseName) {
		u.aClick(bySearchInput);
		u.enterTextbox(bySearchInput, courseName);
		u.aClick(bySearchIcon);
		u.waitUntilInvisible(byLoading_FindYourCouses);

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

	}

	public void searchCourseAndStart(String courseName) {
		u.aClick(bySearchInput);
		u.enterTextbox(bySearchInput, courseName);
		u.rep.logInReport("Info", "Course Name searched: " + courseName);
		u.aClick(bySearchIcon);
		u.waitForLoading();
		u.waitTime(5);
		By dynamicXpath_StartNow = By.xpath("(//h3[text()=\"" + courseName
				+ "\"]/ancestor::div[@class='flip-card ng-star-inserted']//button[text()='Start Now'])[1]");
		for (int i = 0; i < 3; i++) {
			try{if (u.isDisplayed(dynamicXpath_StartNow)) {
				u.aClick(dynamicXpath_StartNow);
				u.waitForLoading();
			}
			else break;}catch(Exception e) {break;}
		}
		new CourseDetails(u).verifyCourseDetailsPageIsDisplayed();
		u.rep.logInReport("Info", "Searching course name:" + courseName);
	}

	public void exitSearch() {
		u.getDriver().navigate().refresh();
	}

	public void verifyIfCategoriesAreDisplayed(String[] categories_FindYourCourse) {
		u.waitForLoading();
		String[] actualCategories=new String[categories_FindYourCourse.length];
		List<WebElement> categories = u.elements(byList_Categories);
		for (int i=0;i<categories.size();i++) {
			actualCategories[i] = categories.get(i).getText();
		}
	//	if()
		
	}

}
