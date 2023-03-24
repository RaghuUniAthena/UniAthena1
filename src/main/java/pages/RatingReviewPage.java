package pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import utility.GenericUtility;

public class RatingReviewPage {
	GenericUtility u;

	public RatingReviewPage(GenericUtility u) {
		this.u = u;
	}

	By byRatingStar_5 = By.xpath("(//span[@class='ng-star-inserted'])[5]");
	By byRatingStar_4 = By.xpath("(//span[@class='ng-star-inserted'])[4]");
	By byRatingStar_3 = By.xpath("(//span[@class='ng-star-inserted'])[3]");
	By byRatingStar_2 = By.xpath("(//span[@class='ng-star-inserted'])[2]");
	By byRatingStar_1 = By.xpath("(//span[@class='ng-star-inserted'])[1]");
	By byComment = By.xpath("//textarea[@ng-reflect-name='comment']");
	By byRadio_ProfessionalProfile_Yes = By.xpath("//input[@ng-reflect-name='professional_profile']/following-sibling::span[text()='Yes']");
	By byRadio_ProfessionalProfile_No = By.xpath("//input[@ng-reflect-name='professional_profile']/following-sibling::span[text()='No']");
	By byRadio_RecommendCourse_Yes = By.xpath("//input[@ng-reflect-name='recommended_course']/following-sibling::span[text()='Yes']");
	By byRadio_RecommendCourse_No = By.xpath("//input[@ng-reflect-name='recommended_course']/following-sibling::span[text()='No']");
	By byRadio_SubmitButton = By.xpath("//button[@class='sub-btn-active']");
	
public void provideFeedback(int rating, String comment, String professionalProfile_YesNo, String recommendCourse_YesNo) {
	u.waitForLoading();
	selectRating(rating);
	u.enterTextbox(byComment, comment);
	selectProfessionalProfile(professionalProfile_YesNo);
	selectRecommendCourse(recommendCourse_YesNo);
	u.aClick(byRadio_SubmitButton);
}
	
	public void selectRating(int rating) {
		switch(rating) {
		case 5: 
			u.aClick(byRatingStar_5);
			break;
		case 4: 
			u.aClick(byRatingStar_4);
			break;
		case 3: 
			u.aClick(byRatingStar_3);
			break;
		case 2: 
			u.aClick(byRatingStar_2);
			break;
		case 1: 
			u.aClick(byRatingStar_1);
			break;
			default: Assert.fail("Incorrect rating provided: "+rating);
		}
		u.waitForLoading();
	}
	public void selectProfessionalProfile(String yesOrNo) {
		switch(yesOrNo.toUpperCase()) {
		case "YES": 
			u.aClick(byRadio_ProfessionalProfile_Yes);
			break;
		case "NO": 
			u.aClick(byRadio_ProfessionalProfile_No);
			break;
		
		}
	}
	public void selectRecommendCourse(String yesOrNo) {
		switch(yesOrNo.toUpperCase()) {
		case "YES": 
			u.aClick(byRadio_RecommendCourse_Yes);
			break;
		case "NO": 
			u.aClick(byRadio_RecommendCourse_No);
			break;
		
		}
	}
}
