package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class SignIn_Prod {
	GenericUtility u;

	public SignIn_Prod(GenericUtility u) {
		this.u = u;
	}


	By login = By.xpath("//a[@class='loginBtn']");

	public void verifyProdSigninPageIsDisplayed() {
		u.waitToVisible(login);
		try{if (u.isDisplayed(login))
			u.rep.logInReport("Pass", "Prod Sign In page is displayed successfully");
		else
			u.rep.logInReport("Fail", "Failed to display Prod Sign In page");
		}catch(Exception e) {
			u.rep.logInReport("Fail", "Failed to display Prod Sign In page");
		}
	}

}
