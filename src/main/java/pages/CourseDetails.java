package pages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class CourseDetails {
	GenericUtility u;

	public CourseDetails(GenericUtility u) {
		this.u = u;
	}

	By byCourseName = By.xpath(
			"//div[@class= 'page-content']//div[@class='ath-back-icon ng-star-inserted']/following-sibling::span");
	By byTab_WhyThisCourse = By.xpath("//h1[text()='Why this Course']");
	By byGoToDashboardBtn = By.xpath("//span[normalize-space(text())='Go To Dashboard']");
	By byStartQuizBtn = By.xpath("//button[normalize-space(text())='Start Quiz']");
	By bylist_StartQuizBtn = By.xpath("//button[normalize-space(text())='Start Quiz']");
	By bylist_Modules = By.xpath("//div[contains(@class,'ath-accordian-header')]//img[@alt='up-arrow']");
	By byCertificateLink = By.xpath("//span[@class='pointer' and normalize-space(text())='1 certificate' or normalize-space(text())='Download Now!']");
	By byCloseWebinarPopup = By.xpath("//div[@class='webinar-container']/button[@class='close-button']/img");

	public void verifyCourseDetailsPageIsDisplayed() { 
		try {
			closeWebinarPopup();
		} catch (Exception e) {
		}
		u.waitToVisible(byTab_WhyThisCourse);
		try {
			if (u.isDisplayed(byTab_WhyThisCourse))
				u.rep.logInReport("Pass", "Course details page is displayed successfully");
			else
				u.rep.logInReport("Fail", "Failed to display Course details page");
		} catch (Exception e) {
			u.rep.logInReport("Fail", "Failed to display Course details page");
		}
	}

	public void closeWebinarPopup() {
		u.waitForLoading();
		u.rep.logInReport("info","Closing the webinar popup");
		u.click(byCloseWebinarPopup);
		
	}
	public String getCourseName() {
		String courseName = u.getText(byCourseName).trim();
		u.rep.logInReport("Info", "Fetching course name: "+courseName);
		return courseName;
	}

	public String getCourseCode() {
		String url = u.getDriver().getCurrentUrl();
		Pattern p = Pattern.compile("\\/course\\/(.*?)\\/");
		Matcher m = p.matcher(url);
		m.find();
		String courseId = m.group(1);

		u.objUserDetailsRepo.setCourseId(courseId);
		u.rep.logInReport("Info", "Course code fetched from url"
				+ "<br>URL: "+url
				+ "<br>Course code: "+courseId);
		return courseId;
	}

	public int getModuleCounts() {
		int modulesCount = u.elements(bylist_Modules).size();
		u.rep.logInReport("Info","Total Modules: "+modulesCount);
		return modulesCount;
	}
	public void expandModule(int i) {
		u.waitForLoading();
		//u.elements(bylist_StartQuizBtn).get(i).click();
		u.aClick(u.elements(bylist_Modules).get(i));
		u.waitForLoading();
	}
	public void navigateBackToDashboard() {
		u.click(byGoToDashboardBtn);
		u.waitForLoading();
	}
	
	public int getStartQuizButtonCounts() {
		int quizCount = u.elements(bylist_StartQuizBtn).size();
		u.rep.logInReport("Info","Total Quiz numbers: "+quizCount);
		return quizCount;
	}

	public void clickOnStartQuizButton(int i) {
		u.waitForLoading();
		//u.elements(bylist_StartQuizBtn).get(i).click();
		u.aClick(u.elements(bylist_StartQuizBtn).get(i));
	}
	public void clickOnStartQuizButton() {
		
		clickOnStartQuizButton(0);
	}
	
	public void clickClaimCertificateLink() {
		u.aClick(byCertificateLink);
		u.waitForLoading();
	}
}
