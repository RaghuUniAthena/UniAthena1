package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class QuizInstruction {
	GenericUtility u;

	public QuizInstruction(GenericUtility u) {
		this.u = u;
	}

	By byStartTestButton = By.xpath("//button/span[normalize-space(text())='Start the Test']");
	
	
	public void verifyQuizInstructionPageIsDisplayed() {
		u.waitForLoading();
		u.waitToVisible(byStartTestButton);
		try {
			if (u.isDisplayed(byStartTestButton))
				u.rep.logInReport("Pass", "Quiz Instruction page is displayed successfully");
			else
				u.rep.logInReport("Fail", "Failed to display Quiz Instruction page");
		} catch (Exception e) {
			u.rep.logInReport("Fail", "Failed to display Quiz Instruction page");
		}
	}
	public void clickOnStartTestButton() {
		u.click(byStartTestButton);
		u.waitForLoading();
	}
}
