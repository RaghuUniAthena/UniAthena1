package pages;

import org.openqa.selenium.By;
import utility.GenericUtility;

public class Admin_Login {
	GenericUtility u;

	public Admin_Login(GenericUtility u) {
		this.u = u;
	}

	By byEmail = By.xpath("//div[@class='username']/input");
	By byPassword = By.xpath("//div[@class='password']/input");
	By byLoginBtn = By.xpath("//button[text()='Login']");

	public void login(String email, String password) {
		u.enterTextbox(byEmail, email);
		u.enterTextbox(byPassword,password);
		u.click(byLoginBtn);
		u.rep.logInReport("Info", "Logging into the admin portal");
	}

}
