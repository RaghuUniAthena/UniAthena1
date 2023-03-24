package pages;

import org.openqa.selenium.By;
import utility.GenericUtility;

public class Admin_HomePage {
	GenericUtility u;

	public Admin_HomePage(GenericUtility u) {
		this.u = u;
	}

	By bySettlement = By.xpath("//a//span[normalize-space(text())='Settlement']");
	By bySettledPayments = By.xpath("//a//span[normalize-space(text())='Settled Payments']");

	public void clickSettlementCard() {
		u.click(bySettlement);
		u.rep.logInReport("Info","Navigating to Settlement page");
	}
	public void clickSettledPaymentsCard() {
		u.click(bySettledPayments);
		u.rep.logInReport("Info","Navigating to Settled Payments page");
	}

}
