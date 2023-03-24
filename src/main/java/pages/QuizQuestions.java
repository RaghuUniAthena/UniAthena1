package pages;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.DBConnector;
import utility.GenericUtility;

public class QuizQuestions {
	GenericUtility u;
	DBConnector db;

	public QuizQuestions(GenericUtility u) throws ClassNotFoundException, SQLException {
		this.u = u;
		db = u.objDB;
	}

	By byQuestionCount = By.xpath("//div[@class='question-count']/span");
	By byQuestionTxt = By.xpath("(//div[@role='textbox']/p)[1]");
	By byNextBtn = By.xpath("//button/span[text()='Next' and @class='btn-text']");
	By bySubmitBtn = By.xpath("//button/span[text()='Submit' and @class='btn-text']");

	public void verifyQuizQuestionsPageIsDisplayed() {
		u.waitToVisible(byQuestionCount);
		try {
			if (u.isDisplayed(byQuestionCount))
				u.rep.logInReport("Pass", "Questions page is displayed successfully");
			else
				u.rep.logInReport("Fail", "Failed to display Questions page");
		} catch (Exception e) {
			u.rep.logInReport("Fail", "Failed to display Questions page");
		}
	}

	public void selectCorrectAnswer() throws ClassNotFoundException, SQLException {
		try {
			String question = u.getText(byQuestionTxt).replace("  ", "");
			String answer = db.Select_QuizCorrectAnswer(u.objUserDetailsRepo.getCourseId(), question);
			if(answer!="") {
				By dynamicXpath_correctanswer = By
						.xpath("//div[@role='textbox']/p[normalize-space(text())=\"" + answer + "\"]");
				u.getDriver().findElement(dynamicXpath_correctanswer).click();
			}else {
				By dynamicXpath_correctanswer = By
						.xpath("(//div[@role='textbox']/p)[2]");
				u.click(dynamicXpath_correctanswer);
			}
			} catch (Exception e) {
			try {
				String question = u.getText(byQuestionTxt).replace("  ", "").replace("__", "__ ");
				String answer = db.Select_QuizCorrectAnswer(u.objUserDetailsRepo.getCourseId(), question);
				By dynamicXpath_correctanswer = By
						.xpath("//div[@role='textbox']/p[normalize-space(text())='" + answer + "']");
				u.getDriver().findElement(dynamicXpath_correctanswer).click();
			} catch (Exception e1) {
				By dynamicXpath_correctanswer = By
						.xpath("(//div[@role='textbox']/p)[2]");
				u.click(dynamicXpath_correctanswer);
			}
		}
	}

	public void clickNext() {
			u.click(byNextBtn);
	}
	public void clickSubmit() {
			u.click(bySubmitBtn); 
			u.rep.logInReport("Info","Submiting the quiz answers");
	}

	public int getQuestionCount() {
		return Integer.parseInt(u.getText(byQuestionCount).split("/")[1]);

	}

}
