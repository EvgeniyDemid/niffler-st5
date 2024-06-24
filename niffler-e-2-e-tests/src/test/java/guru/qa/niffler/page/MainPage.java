package guru.qa.niffler.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class MainPage extends BasePage<MainPage> {


	public static final String url = CONFIG.frontUrl();

	private final ReactCalendar reactCalendar = new ReactCalendar();

	private final ElementsCollection listSpending = $$x("//tbody/tr");
	private final SelenideElement
			deleteSelectButton = $x("//button[contains(text(),'Delete selected')]"),
			allPeopleButton = $x("//*[@href='/people']"),
			friendsButton = $x("//*[@href='/friends']"),
			profileButton = $x("//*[@href='/profile']"),
			mainButton = $x("//*[@href='/main']"),
			title = $x("//h1[contains(text(),'Niffler. The coin keeper.')]"),
			logout = $x("//button[contains(@class,'logout')]"),
			titleSpend = $x("//h2[contains(text(),'Add new spending')]"),
			spendDateInput = $x("//div[@class='calendar-wrapper ']//input"),
			chooseCategoryField = $x("//form[@class='add-spending__form']//*[contains(@class,'indicatorContainer')]"),
			amountInput = $x("//input[@name='amount']"),
			descriptionInput = $x("//input[@name='description']"),
			addSpendButton = $x("//button[contains(text(),'Add new spending')]"),
			filterToday = $x("//button[contains(text(),'Today')]"),
			editSpendButton = $x("//button[contains(@class,'edit')]"),
			spendEditAmount = $x("//table[contains(@class,'table spendings-table')]//input[@name='amount']"),
			spendEditDescription = $x("//table[contains(@class,'table spendings-table')]//input[@name='description']"),
			spendEditSubmitButton = $x("//button[contains(@class,'submit')]"),
			errorFutureDate = $x("//span[contains(text(),'You can not pick future date')]");

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

	@Step("Проверить, что трата {spending} существует ")
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
		reactCalendar.selectDate(date);
		spendDateInput.pressEnter();
		return this;
	}

	public MainPage checkSpendDate(Date date) {
		spendDateInput.shouldHave(
				attribute("value", new SimpleDateFormat("dd/MM/yyyy").format(date)
				));
		return this;
	}

	@Step("Перейти на страницу главную страницу")
	public MainPage clickMainButton() {
		mainButton.click();
		return this;
	}

	@Step("Раскрыть список категорий")
	public MainPage clickChooseSpendingField() {
		chooseCategoryField.click();
		return this;
	}

	@Step("Проверить, что категория присутствует в выпадающем списке")
	public MainPage checkCategoryDropdownList(String category) {
		$(byText(category)).shouldBe(visible);
		return this;
	}

	@Step("Выбрать категорию в выпадающем списке")
	public MainPage selectCategoryDropdownList(String category) {
		$(byText(category)).click();
		return this;
	}

	@Step("Указать сумму")
	public MainPage setAmount(String amount) {
		amountInput.setValue(amount);
		return this;
	}

	@Step("Нажать на кнопку добавть трату")
	public MainPage clickAddSpend() {
		addSpendButton.click();
		return this;
	}

	@Step("Указать описание")
	public MainPage setDescription(String description) {
		descriptionInput.setValue(description);
		return this;
	}

	@Step("Выбрать фильтр сегодня")
	public MainPage clickFilterToday() {
		filterToday.scrollTo().click();
		return this;
	}

	@Step("Нажать кнопку редактирования траты")
	public MainPage clickEditSpend() {
		editSpendButton.scrollTo().click();
		return this;
	}

	@Step("Изменить сумму траты")
	public MainPage editAmount(String amount) {
		spendEditAmount.clear();
		spendEditAmount.setValue(amount);
		return this;
	}

	@Step("Изменить описание  траты")
	public MainPage editSpendDescription(String description) {
		spendEditDescription.clear();
		spendEditDescription.setValue(description);
		return this;
	}

	@Step("Нажать кнопку сохранить при редактировании траты")
	public MainPage clickSpendEditSubmit() {
		spendEditSubmitButton.click();
		return this;
	}

	@Step("Проверить, что отображается ошибка о создании страты в будущем")
	public MainPage checkErrorFutureDate() {
		errorFutureDate.shouldBe(visible);
		return this;
	}
}
