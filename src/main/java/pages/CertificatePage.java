package pages;

import org.openqa.selenium.By;
import utility.GenericUtility;

public class CertificatePage {
	GenericUtility u;

	public CertificatePage(GenericUtility u) {
		this.u = u;
	}

	By byCertificateLoading = By.xpath("//img[@alt='Certificate generating']");
	By byCertificate = By.xpath("//div[@class='certificate-area']");


	public void verifyIfCertificateIsClaimed() {
		u.waitUntilInvisible(byCertificateLoading,130);
		u.waitTime(3);
		if(u.isDisplayed(byCertificate)) {
			u.rep.logInReport("Pass", "Certificate claimed successfully");
			u.rep.logInReport(true);
		}
		else
			u.rep.logInReport("Fail", "Failed to claim Certificate");
		
	}
}
