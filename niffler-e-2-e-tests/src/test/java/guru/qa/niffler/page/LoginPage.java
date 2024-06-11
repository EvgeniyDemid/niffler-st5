package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {

	private final SelenideElement
			userNameField = $x("//input[@name='username']"),
			passwordField = $x("//input[@name='password']"),
			signUp = $x("//button[contains(text(),'Sign In')]");

	public LoginPage setUsername(String userName) {
		userNameField.setValue(userName);
		return this;
	}

	public LoginPage setPassword(String password) {
		passwordField.setValue(password);
		return this;
	}

	public void clickSignUp() {
		signUp.click();
	}

	@Step("Авторизоваться с пользователем")
	public void login(UserJson userJson) {
		setUsername(userJson.username());
		setPassword(userJson.testData().password());
		clickSignUp();
	}
}
