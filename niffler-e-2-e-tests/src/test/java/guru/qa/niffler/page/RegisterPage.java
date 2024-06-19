package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class RegisterPage {
	private final SelenideElement
			userNameField = $x("//input[@id='username']"),
			passwordField = $x("//input[@id='password']"),
			passwordSubmitField = $x("//input[@id='passwordSubmit']"),
			signUp = $x("//button[contains(text(),'Sign Up')]"),
			messageSuccessfulRegistration = $x("//*[contains(text(),'registered!')]"),
			signIn = $x("//a[contains(text(),'Sign in!')]"),
			title = $x("//h1[contains(text(),'Welcome to Niffler. The coin keeper')]");

	public RegisterPage userNameFieldSetValue(String userName) {
		userNameField.setValue(userName);
		return this;
	}

	public RegisterPage checkUserName() {
		userNameField.shouldBe(visible);
		return this;
	}

	public RegisterPage passwordFieldSetValue(String password) {
		passwordField.setValue(password);
		passwordSubmitField.setValue(password);
		return this;
	}

	public RegisterPage checkPassword() {
		passwordField.shouldBe(visible);
		passwordSubmitField.shouldBe(visible);
		return this;
	}

	public RegisterPage signUpClick() {
		signUp.click();
		return this;
	}

	@Step("Проверить сообщение об успешной регистрации")
	public RegisterPage checkMessageSuccessfulRegistration() {
		messageSuccessfulRegistration.shouldBe(visible);
		return this;
	}

	@Step("Нажать на кнопку signIn")
	public LoginPage clickSignUp() {
		signIn.click();
		return new LoginPage();
	}

	@Step("Проверить заголовок ")
	public RegisterPage checkTitle() {
		title.shouldBe(visible);
		return this;
	}
}
