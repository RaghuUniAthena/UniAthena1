package utility;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import smokeTest.BaseClass;
public class ListenersTestNG implements ITestListener {
	GenericUtility u;
	ExtentReport rep;
//	private static ThreadLocal<ExtentReport> rep = new ThreadLocal<ExtentReport>();
//	private static ThreadLocal<GenericUtility> u = new ThreadLocal<GenericUtility>();
//	private static ThreadLocal<InstanceRepo> instanceRepo = new ThreadLocal<InstanceRepo>();
	public void onStart(ITestContext context) {	
		System.out.println("onStart method started");
		//rep=((BaseClass)result.getInstance()).rep;
	}


	public void onFinish(ITestContext context) {
		System.out.println("onFinish method started");
		rep.logInReport("Info","");
	}
	
		public void onTestStart(ITestResult result) {
			u=InstanceRepo.getGenericUtilityInstance();
			rep=InstanceRepo.getExtentReportInstance();
			System.out.println("New Test Started" +result.getName());
			rep.logInReport("Info","Test Started");
		}
		
		public void onTestSuccess(ITestResult result) {
			System.out.println("onTestSuccess Method" +result.getName());
			rep.logInReport("Pass","Test completed");
		}

		public void onTestFailure(ITestResult result) {
			System.out.println("onTestFailure Method" +result.getName()+result.getThrowable().getMessage());
			rep.logInReport("Fail","Test Failed<br>"+result.getThrowable().getMessage());
		}

		public void onTestSkipped(ITestResult result) {
			System.out.println("onTestSkipped Method" +result.getName());
			rep.logInReport("Skip","Test skipped");
		}

		public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
			System.out.println("onTestFailedButWithinSuccessPercentage" +result.getName());
			rep.logInReport("Fail","Test Failed<br>"+result.getThrowable().getMessage());
		}
}
