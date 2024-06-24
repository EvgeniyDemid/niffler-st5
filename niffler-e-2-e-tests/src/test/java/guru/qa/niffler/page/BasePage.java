package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.enums.Alert;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public abstract class BasePage<T extends BasePage<?>> {

	protected static final Config CONFIG = Config.getInstance();

	private final

	SelenideElement actionAlert = $x("//div[contains(@class,'Toastify')]");

	public abstract T checkPageLoader();

	@SuppressWarnings("unchecked")
	@Step("Проверить алерт после нажатия действия")
	public T checkAlert(Alert alert) {
		actionAlert.$(byText(alert.getValue())).shouldBe(visible);
		return (T) this;
	}
}
