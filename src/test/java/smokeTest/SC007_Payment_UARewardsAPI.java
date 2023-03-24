package smokeTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;

import org.testng.annotations.Test;

import utility.DBConnector;
import utility.ExcelUtility;
import utility.GenericUtility;

public class SC007_Payment_UARewardsAPI extends BaseClass {
	ExcelUtility x = new ExcelUtility("SC006");
	String courseName;

	@Test(enabled = true, priority = 0)
	public void SC007_Payment_UARewardsAPI() throws Exception {
		u.launchUrl(signInUrl);
		email = x.readData("Email");
		password = x.readData("Password");
		courseName = x.readData("CourseName");

		objSignIn.clickOnSignUp();
		objSignUp.clickShortCourses();
		mobileNumber = GenericUtility.getRandomPhoneNumber(x.readData("Country"));
		password = x.readData("Password");
		email = objSignUp.signUpForNewUser(x.readData("FirstName"), x.readData("LastName"), x.readData("Country"),
				mobileNumber, email, password);
		objDashboard.verifyIfDashboardDisplayed();
		objTutorial.clickOnSkipTutorial();
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
		objCourseDetails.verifyCourseDetailsPageIsDisplayed();
		objCourseDetails.clickClaimCertificateLink();
		objEligibleForCertifications.clickPayNow(courseName);
		LocalDate currentdate = LocalDate.now();
		int bdayofferCurrentMonth = currentdate.getMonthValue();
		int bdayofferCurrentDay = currentdate.getDayOfMonth();
		objPaymentPage.enterPaymentScreenDetailsAndPay(bdayofferCurrentMonth,bdayofferCurrentDay, x.readData("Qualification"),
				x.readData("QualificationName"));
		objPaymentPage.applyBirthdayOffer();
		objPaymentPage.clickPayNow();
		String orderId = objPaymentPage.fetchOrderId();
		objPaymentPage.payment_API(orderId);
		u.getDriver().navigate().back();
		objPaymentPage.verifyIfAlreadyPaidPopupDisplayed(true);
		objRegisteredCourses.clickClaimCertificate(courseName);
		objEligibleForCertifications.clickClaimNow(courseName);
		objRatingReviewPage.provideFeedback(5, "test", "Yes", "Yes");
		objCertificatePage.verifyIfCertificateIsClaimed();
		u.getDriver().navigate().back();
		objDashboard.verifyIfDashboardDisplayed();
		objDashboard.verifyUARewardsValue(true);
		
		courseName="Basics of Python";
		objFindYourCourses.searchCourseAndStart(courseName);
		objTutorial.clickOnSkipTutorial();
		objCourseDetails.verifyCourseDetailsPageIsDisplayed();
		objCourseDetails.getCourseCode();
		modulesCount = objCourseDetails.getModuleCounts();
		for (int iMod = 0; iMod < modulesCount; iMod++) {
			if (iMod != 0) {
				objCourseDetails.expandModule(iMod);
			}
			int startQuizBtnCount = objCourseDetails.getStartQuizButtonCounts();
		for (int i = 0; i < startQuizBtnCount; i++) {
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
		}}
		objCourseDetails.verifyCourseDetailsPageIsDisplayed();
		objCourseDetails.clickClaimCertificateLink();
		objEligibleForCertifications.clickPayNow(courseName);
		objPaymentPage.applyCoupon_UARewards();
		objPaymentPage.clickPayNow();
		orderId = objPaymentPage.fetchOrderId();
		objPaymentPage.payment_API(orderId);
		u.getDriver().navigate().back();
		objPaymentPage.verifyIfAlreadyPaidPopupDisplayed(true);
		objRegisteredCourses.clickClaimCertificate(courseName);
		objEligibleForCertifications.clickClaimNow(courseName);
		objRatingReviewPage.provideFeedback(5, "test", "Yes", "Yes");
		objCertificatePage.verifyIfCertificateIsClaimed();
		u.getDriver().navigate().back();
		objDashboard.verifyIfDashboardDisplayed();
	}
}
