package smokeTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.cert.Certificate;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import pages.Admin_HomePage;
import pages.Admin_Login;
import pages.Admin_SettledPayments;
import pages.Admin_Settlement;
import pages.Admin_SettlementDetailsPage;
import pages.CertificatePage;
import pages.ChangePassword;
import pages.CommomPage;
import pages.ContactUs;
import pages.CourseDetails;
import pages.Dashboard;
import pages.EligibleForCertifications;
import pages.FAQ;
import pages.FindYourCourses;
import pages.MyProfile;
import pages.PaymentPage;
import pages.QuizInstruction;
import pages.QuizQuestions;
import pages.QuizResult;
import pages.RatingReviewPage;
import pages.RegisteredCourses;
import pages.SignIn;
import pages.SignIn_Prod;
import pages.SignUp;
import pages.Tutorial;
import utility.ExtentReport;
import utility.GenericUtility;
import utility.ReadPropFile;

/*
 * BaseClass is inherited by the TestNG class in src/test/java
 *
 */
public class BaseClass {
	private WebDriver driver;
	public ReadPropFile prop;
	public GenericUtility u;
	public ExtentReport rep = new ExtentReport();

	SignIn objSignIn;
	SignIn_Prod objSignIn_Prod;
	SignUp objSignUp;
	Dashboard objDashboard;
	Tutorial objTutorial;
	MyProfile objMyProfile;
	ChangePassword objChangePassword;
	ContactUs objContactUs;
	RegisteredCourses objRegisteredCourses;
	FindYourCourses objFindYourCourses;
	CourseDetails objCourseDetails;
	QuizInstruction objQuizInstruction;
	QuizQuestions objQuizQuestions;
	QuizResult objQuizResult;
	EligibleForCertifications objEligibleForCertifications;
	PaymentPage objPaymentPage;
	CommomPage objCommomPage;
	FAQ objFAQ;
	RatingReviewPage objRatingReviewPage;
	CertificatePage objCertificatePage;
	Admin_Login objAdmin_Login;
	Admin_HomePage objAdmin_HomePage;
	Admin_Settlement objAdmin_Settlement;
	Admin_SettlementDetailsPage objAdmin_SettlementDetailsPage;
	Admin_SettledPayments objAdmin_SettledPayments;

	public String email;
	public String password = "1234";
	public String mobileNumber = "";
	public String signInUrl;
	public String adminUrl;
	public String adminDashboard;
	public String adminEmail;
	public String adminPassword;


	@BeforeTest
	public void InitiateReport() {
////		rep = new ExtentReport();
		rep.initiateExtentReport(); 
	}

	@AfterTest
	public void terminateReport() {
		rep.terminateExtentReport();
	}

	@BeforeClass
	@Parameters("browser")
	public void beforeClass(String browser) throws Exception {
		rep.startTest(this.getClass().getName());
		try {
			u = new GenericUtility(browser, rep);
			driver = u.getDriver();
		} catch (Exception e) {
			System.out.println(e);
		}

		pageInitializations();
		variableInitializations();
	}

	@AfterClass
	public void closeDriver() {
		userlog();
		u.rep.logInReport(true);
		if (driver != null) {
			driver.quit();
		}
	}

	public void pageInitializations() throws ClassNotFoundException, SQLException {
		prop = new ReadPropFile();
		objSignIn = new SignIn(u);
		objSignIn_Prod = new SignIn_Prod(u);
		objSignUp = new SignUp(u);
		objDashboard = new Dashboard(u);
		objTutorial = new Tutorial(u);
		objMyProfile = new MyProfile(u);
		objRegisteredCourses = new RegisteredCourses(u);
		objFindYourCourses = new FindYourCourses(u);
		objCourseDetails = new CourseDetails(u);
		objQuizInstruction = new QuizInstruction(u);
		objQuizQuestions = new QuizQuestions(u);
		objQuizResult = new QuizResult(u);
		objEligibleForCertifications = new EligibleForCertifications(u);
		objPaymentPage = new PaymentPage(u);

		objCommomPage = new CommomPage(u);
		objChangePassword = new ChangePassword(u);
		objContactUs = new ContactUs(u);
		objFAQ = new FAQ(u);
		objRatingReviewPage = new RatingReviewPage(u);
		objCertificatePage = new CertificatePage(u);
		objAdmin_Login = new Admin_Login(u);
		objAdmin_HomePage = new Admin_HomePage(u);
		objAdmin_Settlement= new Admin_Settlement(u);
		objAdmin_SettlementDetailsPage= new Admin_SettlementDetailsPage(u);
		objAdmin_SettledPayments = new Admin_SettledPayments(u);
		
	}

	public void variableInitializations() {
		signInUrl = prop.getPropData().getProperty("SignInUrl");
		adminUrl = prop.getPropData().getProperty("AdminUrl");
		adminDashboard = prop.getPropData().getProperty("AdminDashboard");
		adminEmail = prop.getPropData().getProperty("AdminEmail");
		adminPassword = prop.getPropData().getProperty("AdminPassword");

		
	}

	public void userlog() {
		try (FileWriter f = new FileWriter(u.currentDirectory + "\\UserLog.txt", true);
				BufferedWriter b = new BufferedWriter(f);
				PrintWriter p = new PrintWriter(b);) {
			p.println(email + " || " + password);
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

}






















