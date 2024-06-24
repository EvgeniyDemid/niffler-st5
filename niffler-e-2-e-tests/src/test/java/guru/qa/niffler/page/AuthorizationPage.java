package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class AuthorizationPage {

	private final SelenideElement
			loginButton = $x("//a[contains(text(),'Login')]"),
			registerButton = $x("//a[contains(text(),'Register')]"),
			title = $x("//h1[contains(text(),'Welcome to magic journey with Niffler. The coin ke')]");

	public LoginPage clickLoginButton() {
		loginButton.click();
		return new LoginPage();
	}

	public void loginButtonIsVisible() {
		loginButton.should(visible);
	}

	public RegisterPage clickRegisterButton() {
		registerButton.click();
		return new RegisterPage();
	}

	public AuthorizationPage checkRegisterButton() {
		registerButton.shouldBe(visible);
		return this;
	}

	public AuthorizationPage checkTitle() {
		title.shouldBe(visible);
		return this;
	}
}
