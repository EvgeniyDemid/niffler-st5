package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.enums.Alert;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public abstract class BasePage<T extends BasePage<?>> {

	protected static final Config CONFIG = Config.getInstance();

	private final SelenideElement actionAlert = $x("//div[@class='Toastify']");

	public abstract T checkPageLoader();

	@SuppressWarnings("unchecked")
	@Step("Проверить алерт после нажатия действия")
	public T checkAlert(Alert alert) {
		actionAlert.
				shouldBe(visible).
				shouldHave(exactText(alert.getValue()));
		return (T) this;
	}
}
