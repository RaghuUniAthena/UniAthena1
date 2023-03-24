package pages;

import java.io.IOException;

import org.openqa.selenium.By;
import utility.GenericUtility;

public class Admin_SettlementDetailsPage {
	GenericUtility u;

	public Admin_SettlementDetailsPage(GenericUtility u) {
		this.u = u;
	}

	By bySettlementNote = By.xpath("//textarea[@ng-reflect-name='settlement_note']");
	By byPAymentMode = By.xpath("//div[@class='mat-form-field-flex']");
	By byPAymentMode_BankTransfer = By.xpath("//span[@class='mat-option-text' and normalize-space(text())='Bank Transfer']");
	By byWriteOff_No = By.xpath("//input[@type='radio' and @value='0']/parent::div");
	By byUpload = By.id("upload");
	By bySubmit = By.xpath("//input[@type='submit']");

	public void enterDetailsOfSettlementAndSubmit(String settlementNote) throws IOException {
		u.waitForLoading_Admin();
		u.enterTextbox(bySettlementNote,settlementNote);
		u.aClick(byPAymentMode);
		u.aClick(byPAymentMode_BankTransfer);
		u.aClick(byWriteOff_No);
		u.aClick(byUpload);
		u.waitTime(2);
		Runtime.getRuntime().exec(u.currentDirectory+"\\AutoIT\\FileUpload.exe "+u.currentDirectory+"\\AutoIT\\SampleFile.png");	
		u.waitTime(3);
		u.waitForLoading_Admin();
		u.click(bySubmit);
		u.waitTime(2);
		u.waitForLoading_Admin();
		try{if(u.isDisplayed(new Admin_Settlement(u).bySearchbyDropdown))
			u.rep.logInReport("Pass","Submitted Settlement details");
		else
			u.rep.logInReport("Fail","Unable to submit Settlement details");}catch(Exception e) {
				u.rep.logInReport("Fail","Unable to submit Settlement details");
			}
	}


}
