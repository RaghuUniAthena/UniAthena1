package pages;

import org.openqa.selenium.By;
import utility.GenericUtility;

public class EligibleForCertifications {
	GenericUtility u;

	public EligibleForCertifications(GenericUtility u) {
		this.u = u;
	}

	By byEligibleCertificatesTitle = By.xpath("//h2[normalize-space(text())='Eligible for Certification']");
	By byList_PayNow = By.xpath("//div[@class='course-details']//button/span[normalize-space(text())='Pay Now']");
	By byList_ClaimNow = By.xpath("//div[@class='course-details']//button/span[normalize-space(text())='Claim Now']");

	public void clickPayNow(String courseName) {
		By dynamicXPath_PayNow = By.xpath("//h3[normalize-space(text())=\""+courseName+"\"]/ancestor::div[@class='course-details']//button/span[normalize-space(text())='Pay Now']");
		u.aClick(dynamicXPath_PayNow);
		u.waitForLoading();
		u.rep.logInReport("Pass","Clicking on 'Pay now' option in 'Eligible for Certification' page"
				+ "<br>Course Name: "+ courseName);
	}
	public void clickClaimNow(String courseName) {
		u.waitForLoading();
		By dynamicXPath_ClaimNow = By.xpath("//h3[normalize-space(text())=\""+courseName+"\"]/ancestor::div[@class='course-details']//button/span[normalize-space(text())='Claim Now']");
//		u.aClick(dynamicXPath_ClaimNow);
		for (int i = 0; i < 3; i++) {
			try{if (u.getDriver().findElement(dynamicXPath_ClaimNow).isDisplayed()) {
				u.aClick(dynamicXPath_ClaimNow);
				u.waitForLoading();
			}
			else break;}catch(Exception e) {break;}
		}
		u.waitForLoading();
		u.rep.logInReport("Pass","Clicking on 'Claim Now' option in 'Eligible for Certification' page"
				+ "<br>Course Name: "+ courseName);
	}
}
