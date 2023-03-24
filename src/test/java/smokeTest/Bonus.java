package smokeTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import org.testng.annotations.Test;

public class Bonus extends BaseClass{
	// @Test
	public void temp() throws IOException, URISyntaxException, InterruptedException {
		objPaymentPage.payment_API("439695");
	}
	
	 @Test
	public void completeQuiz() throws Exception, SQLException {
		 String courseName = "Basics in Machine Learning";
		 email="testuniathena_1676350366066@mailinator.com";
		 password ="1234";
		u.launchUrl(prop.getPropData().getProperty("URL"));
		objSignIn.loginToApplication(email, password);
		objDashboard.verifyIfDashboardDisplayed();
		//objTutorial.clickOnSkipTutorial();
		objFindYourCourses.searchCourseAndStart(courseName);
		objTutorial.clickOnSkipTutorial();
		objCourseDetails.verifyCourseDetailsPageIsDisplayed();
		objCourseDetails.getCourseCode();
		int modulesCount = objCourseDetails.getModuleCounts();
		for (int iMod = 0; iMod < modulesCount; iMod++) {
			if (iMod != 0) {
				objCourseDetails.expandModule(iMod);
			}
			int startQuizBtnCount = objCourseDetails.getStartQuizButtonCounts();
			for (int i = 0; i < startQuizBtnCount; i++) {
				u.rep.logInReport("Info", "------ CourseName:" + courseName 
						+"<br>Module: "+(iMod+1)
						+ "<br>QuizNo.: " + (i + 1));
				objCourseDetails.clickOnStartQuizButton();
				objQuizInstruction.verifyQuizInstructionPageIsDisplayed();
				objQuizInstruction.clickOnStartTestButton();
				objQuizQuestions.verifyQuizQuestionsPageIsDisplayed();
				int questionCount = objQuizQuestions.getQuestionCount();
				for (int j = 0; j < questionCount; j++) {
					objQuizQuestions.selectCorrectAnswer();
					if (j == questionCount - 1)
						objQuizQuestions.clickSubmit();
					else
						objQuizQuestions.clickNext();
				}
				objQuizResult.verifyQuizResultPageIsDisplayed();
				objQuizResult.clickBackToCoursePage();
				if (i != (startQuizBtnCount-1) && iMod != 0) {
					objCourseDetails.expandModule(iMod);
				}
			}
		}
	 }
}
	
