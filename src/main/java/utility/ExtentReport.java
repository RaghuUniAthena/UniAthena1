package utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReport {

	static ExtentTest test;
	static ExtentReports report;
	WebDriver driver;
	WebElement e;
	String currentDirectory = System.getProperty("user.dir");
	String reportScreenshotFileName;
	GenericUtility u;

	public ExtentReport(GenericUtility u) {
		this.u = u;
	}

	public ExtentReport() {

	}

	public void setGenericUtilityInstance(GenericUtility u) {
		this.u = u;
		u.reportScreenshotFileName = this.reportScreenshotFileName;
		u.reportPath = u.currentDirectory + "\\ExtentReport\\" + reportScreenshotFileName;
		InstanceRepo.setExtentReportInstance(this);
	}

	public void initiateExtentReport() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		reportScreenshotFileName = LocalDate.now().format(formatter) + "_" + System.currentTimeMillis();

		String reportPath = currentDirectory + "\\ExtentReport\\" + reportScreenshotFileName + "\\Results.html";
		if (System.getProperty("os.name").toLowerCase().contains("mac"))
			reportPath = reportPath.replace("\\", "/");
		report = new ExtentReports(reportPath);
//	        test = report.startTest(className);
	}

	public void startTest(String className) {
		test = report.startTest(className);
	}

	public void logInReport(String status, String details) {
		switch (status.toUpperCase()) {
		case "PASS":
			test.log(LogStatus.PASS,  test.addScreenCapture(u.takeScreenshot())+ details);
			break;
		case "FAIL":
			test.log(LogStatus.FAIL, test.addScreenCapture(u.takeScreenshot()) + details);
			break;
		case "SKIP":
			test.log(LogStatus.SKIP, /* test.addScreenCapture(u.takeScreenshot())+ */details);
			break;
		default:
		case "INFO":
			test.log(LogStatus.INFO, details);
			break;
		}
	}

	public void logInReport(Boolean takeScreenshot) {
		test.log(LogStatus.INFO, test.addScreenCapture(u.takeScreenshot()));
	}

	public void terminateExtentReport() {
		report.endTest(test);
		report.flush();
	}

}
