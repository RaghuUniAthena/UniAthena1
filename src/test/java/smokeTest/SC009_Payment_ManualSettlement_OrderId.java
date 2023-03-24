package smokeTest;

import java.io.IOException;
import java.time.LocalDate;

import org.testng.annotations.Test;

import utility.DBConnector;
import utility.ExcelUtility;
import utility.GenericUtility;

public class SC009_Payment_ManualSettlement_OrderId extends BaseClass {
	ExcelUtility x = new ExcelUtility("SC006");
	String courseName;

	@Test(enabled = true, priority = 0)
	public void SC009_Payment_ManualSettlement_OrderId() throws Exception {
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
		objPaymentPage.enterPaymentScreenDetailsAndPay(x.readData("DOB"), x.readData("Qualification"),
				x.readData("QualificationName"));
		String orderId = objPaymentPage.fetchOrderId();

		String oldTab = u.openNewTab();
		u.launchUrl(adminUrl);
		objAdmin_Login.login(adminEmail, adminPassword);
		objAdmin_HomePage.clickSettlementCard();
		objAdmin_Settlement.searchByOrderId(orderId);
		objAdmin_Settlement.applySettlementByOrderId(orderId);
		objAdmin_SettlementDetailsPage.enterDetailsOfSettlementAndSubmit("test");
		u.launchUrl(adminDashboard);
		objAdmin_HomePage.clickSettledPaymentsCard();
		objAdmin_SettledPayments.searchByOrderId(orderId);
		objAdmin_SettledPayments.verifyIfOrderIdIsSettled(orderId, true);

		u.launchUrl(adminDashboard);
		objAdmin_HomePage.clickSettlementCard();
		objAdmin_Settlement.searchByOrderId(orderId);
		objAdmin_Settlement.verifyIfOrderIdIsSettled(orderId, false);
		u.getDriver().switchTo().window(oldTab);
		
		u.getDriver().navigate().back();
		objPaymentPage.verifyIfAlreadyPaidPopupDisplayed(true);
		objRegisteredCourses.clickClaimCertificate(courseName);
		objEligibleForCertifications.clickClaimNow(courseName);
		objRatingReviewPage.provideFeedback(5, "test", "Yes", "Yes");
		objCertificatePage.verifyIfCertificateIsClaimed();
		u.getDriver().navigate().back();
		objDashboard.verifyIfDashboardDisplayed();
		objRegisteredCourses.verifyAlreadyClaimedButtonIsDisplayed(courseName);
		
	}
}
