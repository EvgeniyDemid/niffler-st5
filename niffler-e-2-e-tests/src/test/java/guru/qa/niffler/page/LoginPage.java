package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {

	private final SelenideElement
			userNameField = $x("//input[@name='username']"),
			passwordField = $x("//input[@name='password']"),
			signIn = $x("//button[contains(text(),'Sign In')]"),
			signUp = $x("//a[contains(text(),'Sign up!')]"),
			title = $x("//h1[contains(text(),'Welcome to Niffler. The coin keeper')]");

	public LoginPage setUsername(String userName) {
		userNameField.setValue(userName);
		return this;
	}

	public LoginPage checkUsername() {
		userNameField.shouldBe(visible);
		return this;
	}

	public LoginPage setPassword(String password) {
		passwordField.setValue(password);
		return this;
	}

	public LoginPage checkPassword() {
		passwordField.shouldBe(visible);
		return this;
	}

	public LoginPage clickSignIn() {
		signIn.click();
		return this;
	}

	public LoginPage checkSignIn() {
		signIn.shouldBe(visible);
		return this;
	}

	public LoginPage checkSignUp() {
		signUp.shouldBe(visible);
		return this;
	}

	@Step("Авторизоваться с пользователем")
	public MainPage login(UserJson userJson) {
		setUsername(userJson.username());
		setPassword(userJson.testData().password());
		clickSignIn();
		return new MainPage();
	}

	@Step("Авторизоваться с пользователем")
	public MainPage login(String username, String password) {
		setUsername(username);
		setPassword(password);
		clickSignIn();
		return new MainPage();
	}

	public LoginPage checkTitle() {
		title.shouldBe(visible);
		return this;
	}
}
