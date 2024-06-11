package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class FriendsPage extends BasePage<FriendsPage> {

	public static final String url = CONFIG.frontUrl() + "friends";

	private final SelenideElement peopleTable = $x("//div[contains(@class,'people-content')]");

	private SelenideElement declineButton(String userName) {
		return $x(String.format("(//td[contains(text(),'%s')]/..//button[1])[2]", userName));
	}

	private SelenideElement deleteButton(String userName) {
		return $x(String.format("//td[contains(text(),'%s')]/..//button[1]", userName));
	}

	private SelenideElement submitButton(String userName) {
		return $x(String.format("//td[contains(text(),'%s')]/..//button[contains(@class,'submit')]", userName));
	}

	@Step("Принять приглашение")
	public FriendsPage clickSubmit(String userName) {
		submitButton(userName).click();
		return this;
	}

	@Step("Отклонить приглашение")
	public FriendsPage clickDecline(String userName) {
		declineButton(userName).click();
		return this;
	}

	@Step("Удалить друга")
	public FriendsPage clickDelete(String userName) {
		deleteButton(userName).click();
		return this;
	}

	@Override
	public FriendsPage checkPageLoader() {
		peopleTable.shouldBe(visible);
		return this;
	}
}
