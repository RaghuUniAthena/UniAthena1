package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class QuizResult {
	GenericUtility u;

	public QuizResult(GenericUtility u) {
		this.u = u;
	}

	By byQuizResultLabel = By.xpath("//label[normalize-space(text())='Quiz Result']");
	By byViewTestDetails = By.xpath("//div[@class='view-test-details']");
	By byBackToCoursePage = By.xpath("//button[@class='back-to-course-page']");
	
	public void verifyQuizResultPageIsDisplayed() {
		u.waitForLoading();
		u.waitToVisible(byQuizResultLabel);
		try{if (u.isDisplayed(byQuizResultLabel))
			u.rep.logInReport("Pass", "Result page is displayed successfully");
		else
			u.rep.logInReport("Fail", "Failed to display Result page");
		}catch(Exception e) {
			u.rep.logInReport("Fail", "Failed to display Result page");
		}
	//	u.waitForLoading();
	}
	
	public void clickBackToCoursePage() {
		for (int i = 0; i < 3; i++) {
			try{if (u.getDriver().findElement(byBackToCoursePage).isDisplayed()) {
				u.aClick(byBackToCoursePage);
			}
			else break;}catch(Exception e) {break;}
		}
		u.waitForLoading();
	}

	
}
