package guru.qa.niffler.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage extends BasePage<MainPage> {


	public static final String url = CONFIG.frontUrl();

	private final ElementsCollection listSpending = $$x("//tbody/tr");
	private final SelenideElement
			deleteSelectButton = $x("//button[contains(text(),'Delete selected')]"),
			allPeopleButton = $x("//*[@href='/people']"),
			friendsButton = $x("//*[@href='/friends']"),
			profileButton = $x("//*[@href='/profile']"),
			title = $x("//h1[contains(text(),'Niffler. The coin keeper.')]"),
			logout = $x("//button[contains(@class,'logout')]"),
			titleSpend = $x("//h2[contains(text(),'Add new spending')]"),
			spendDateInput = $x("//div[@class='calendar-wrapper ']//input");

	@Step("Проставить чек-бокс напротив трат ")
	public MainPage selectSpending(String spending) {
		listSpending.find(text(spending)).$$("td").first().scrollTo().click();
		return this;
	}

	@Step("Нажать кнопку 'Удалить Выделенное'")
	public MainPage clickDeleteSelectButton() {
		deleteSelectButton.click();
		return this;
	}

	@Step("Проверить, что список трат пустой")
	public void checkListSpendingIsEmpty() {
		listSpending.shouldHave(size(0));
	}

	@Step("Перейти на страницу 'Все Люди'")
	public PeoplePage clickAllPeopleButton() {
		allPeopleButton.should(visible).click();
		return new PeoplePage();
	}

	@Step("Перейти на страницу 'Друзья'")
	public FriendsPage clickFriendsButton() {
		friendsButton.should(visible).click();
		return new FriendsPage();
	}

	@Step("Перейти на страницу 'Профиль'")
	public ProfilePage clickProfileButton() {
		profileButton.should(visible).click();
		return new ProfilePage();
	}

	@Step("Проставить, что трата {spending} существует ")
	public MainPage checkSpendingIsVisible(String spending) {
		listSpending.shouldHave(CollectionCondition.texts(spending));
		return this;
	}

	@Step("Проверить, что заголовок виден  ")
	public MainPage checkTitleIsVisible() {
		title.shouldBe(visible);
		return this;
	}

	@Step("Нажать на кнопку logout")
	public AuthorizationPage clickLogout() {
		logout.click();
		return new AuthorizationPage();
	}

	@Override
	public MainPage checkPageLoader() {
		titleSpend.shouldBe(visible);
		return this;
	}

	public MainPage setSpendDate(Date date) {
		spendDateInput.click();
		new ReactCalendar().selectDate(date);
		spendDateInput.pressEnter();
		return this;
	}

	public MainPage checkSpendDate(Date date) {
		spendDateInput.shouldHave(
				attribute("value", new SimpleDateFormat("dd/MM/yyyy").format(date)
				));
		return this;
	}
}
