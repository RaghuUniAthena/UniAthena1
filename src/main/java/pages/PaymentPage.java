package pages;

import static org.testng.Assert.assertFalse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.testng.Assert;

import utility.GenericUtility;

public class PaymentPage {
	GenericUtility u;
//	public static final String path_bash = "C:\\Program Files\\Git\\git-bash.exe";
	public static final String path_bash = "C:/Program Files/Git/bin/bash.exe";
	
	public PaymentPage(GenericUtility u) {
		this.u = u;

	}

	By byDOB = By.id("mat-input-4");
	By byHighestQualificationDropdown = By.xpath("//mat-select[@aria-label='Select the Level']");
	By byHighestQualificationName = By.xpath("//input[@id='mat-input-5']");
	By byApplyCouponDropdown = By.xpath("//input[@name='couponCode']");
	By byApplyCouponApply_UARewards = By.xpath("//h2[normalize-space(text())='UA Rewards']/ancestor::div[contains(@class,'coupon-detail')]//h2[normalize-space(text())=\"Apply\"]");
	By byPayNowButton = By.xpath("//button[@type='submit']");
	By byContinueButton = byPayNowButton;
	By byBirthDayOffer_ApplyNowButton = By.xpath("//button[normalize-space(text())='Apply Now']");
	
	By byAlreadyPaidPopup = By.xpath("//h3[normalize-space(text())='Already Paid For This Course']");
	By byAlreadyPaidPopup_OK = By.xpath("//button/span[normalize-space(text())='Ok']");
	

	public void enterPaymentScreenDetailsAndPay(String DOB, String qualification, String qualificationName) {
		u.waitForLoading();
		u.enterTextbox(byDOB, DOB);
		u.waitForLoading();
		u.aClick(byHighestQualificationDropdown);
		By byHighestQualificationDropdownOption = By
				.xpath("//span[@class='mat-option-text' and normalize-space(text())=\"" + qualification.trim() + "\"]");
		u.click(byHighestQualificationDropdownOption);

		u.enterTextbox(byHighestQualificationName, qualificationName);
		u.aClick(byPayNowButton);
		u.rep.logInReport("Info","Payment screen details entered and clicked PayNow button");
		u.rep.logInReport(true);
	}
	public void enterPaymentScreenDetailsAndPay(int month,int day, String qualification, String qualificationName) {
		String DOB = month+"/"+day+"/1997";
		enterPaymentScreenDetailsAndPay(DOB, qualification, qualificationName);
	}
	public void applyCoupon_UARewards() {
			u.aClick(byApplyCouponDropdown);
			u.waitTime(2);
			u.aClick(byApplyCouponApply_UARewards);
			u.rep.logInReport("Info","UA Coupon Applied");
	}
	public void clickPayNow() {
		u.waitForLoading();
		u.aClick(byPayNowButton);
		u.waitForLoading();
		u.rep.logInReport("Info","Clicking PayNow button from payment details page");
	}
	public void clickContinue() {
		u.waitForLoading();
		u.aClick(byContinueButton);
		u.waitForLoading();
		u.rep.logInReport("Info","Clicking PayNow button from payment details page");
	}
	public String fetchOrderId() {
//		u.aClick(byPayNowButton);
		u.waitForLoading();
		String url = u.getDriver().getCurrentUrl();
		Pattern p = Pattern.compile("\\?OrderId=(.*\\d)");
		Matcher m = p.matcher(url);
		m.find();
		String orderId="---";
		try{orderId = m.group(1);
		u.rep.logInReport("Info","Order id fetched: "+orderId);
		}catch(
				Exception e) {
			System.out.println(e);
			u.rep.logInReport("FAIL","Unable to find match for Order ID from url"+e);
			assertFalse(true, "Unable to find match for Order ID from url"+e);
		}
		return orderId;
	}

	public void payment_Curl(String orderId) throws IOException {
		String curlCommand = "curl -d '{\"paymentStatus\": 1}' " + "-H 'Content-Type: application/json' "
				+ "'https://learnstaging.uniathena.com/athenastg/api/updatePaymentStatusByOrderId/" + orderId + "'";
	
		try {
			ProcessBuilder processBuilder = new ProcessBuilder();
			processBuilder.command(path_bash, "-c", curlCommand);
			Process process = processBuilder.start();
			   StringBuilder output = new StringBuilder();
		        BufferedReader reader = new BufferedReader(
		          new InputStreamReader(process.getInputStream()));

		        String line;
		        while ((line = reader.readLine()) != null) {
		            output.append(line + "\n");
		        }

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				System.out.println(" --- Command run successfully");
				System.out.println(" --- Output = " + output);

			} else {
				System.out.println(" --- Command run unsuccessfully");
			}
		} catch (IOException | InterruptedException e) {
			System.out.println(" --- Interruption in RunCommand: " + e);
			// Restore interrupted state
			Thread.currentThread().interrupt();
		}

	}

	public void payment_API(String orderId) throws URISyntaxException, IOException, InterruptedException {
HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				  .uri(new URI("https://learnstaging.uniathena.com/athenastg/api/updatePaymentStatusByOrderId/" + orderId))
				  .headers("Content-Type", "application/json")
				  .POST(HttpRequest.BodyPublishers.ofString("{\"paymentStatus\": 1}"))
				  .build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		Assert.assertEquals(response.statusCode(),200,"Failed to complete payment for order id:"+orderId);
		u.rep.logInReport("Pass","Payment completed");
	}
	

//	public String readFileTxt() {
//		String data = null;
//		String path_file_output_git_bash= u.reportPath+"\\gitBashOut.txt";
//		try {
//			File myObj = new File(path_file_output_git_bash);
//			try{myObj.createNewFile();}catch (IOException e) {
//				e.printStackTrace();
//			}
//			Scanner myReader = new Scanner(myObj);
//			while (myReader.hasNextLine()) {
//				data = myReader.nextLine();
//			}
//			myReader.close();
//		} catch (FileNotFoundException e) {
//			System.out.println(" --- An error occurred");
//			e.printStackTrace();
//		} 
//		return data;
//	}
	
	public void applyBirthdayOffer() {
		u.waitForLoading();
		u.aClick(byBirthDayOffer_ApplyNowButton);
		
	}
	
	public void verifyIfAlreadyPaidPopupDisplayed(Boolean bClickOK) {
		u.waitForLoading();
		u.waitTime(2);
		try{if (u.isDisplayed(byAlreadyPaidPopup))
			u.rep.logInReport("Pass", "Already paid popup is displayed successfully");
		else
			u.rep.logInReport("Fail", "Failed to display Already paid popup");
		}catch(Exception e) {
			u.rep.logInReport("Fail", "Failed to display Already paid popup");
		}
		
		if(bClickOK) {
			u.click(byAlreadyPaidPopup_OK);
			u.waitForLoading();
		}
	}

}
