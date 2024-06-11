package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class PeoplePage extends BasePage<PeoplePage> {

	public static final String url = CONFIG.frontUrl() + "people";

	private SelenideElement actionUser(String userName) {
		return $x(String.format("//td[contains(text(),'%s')]/..//button[1]", userName));
	}

	private final SelenideElement peopleTable =
			$x("//*[contains(@class,'people-content')]");

	@Step("Нажать действие у пользователя {userName}")
	public PeoplePage clickActionFromUser(String userName) {
		actionUser(userName).click();
		return this;
	}

	@Override
	public PeoplePage checkPageLoader() {
		peopleTable.shouldBe(visible);
		return this;
	}
}
