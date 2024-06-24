package guru.qa.niffler.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage extends BasePage<ProfilePage> {

	public static final String url = CONFIG.frontUrl() + "profile";

	Faker faker = new Faker();

	private final ElementsCollection categoriesList = $$x("//*[@class='categories__list']/li");

	private final SelenideElement
			firstnameInput = $x("//input[@name='firstname']"),
			usernameInput = $x("//*[@class='avatar-container']/figcaption"),
			surnameInput = $x("//input[@name='surname']"),
			currencyInput = $x("//*[@class='select-wrapper']"),
			categoryInput = $x("//input[@name='category']"),
			createButton = $x("//button[text()='Create']"),
			avatar = $x("//*[@class='profile__avatar']"),
			addCategoryInfo = $x("//p[contains(text(),'You can add not more than 8 different categories')]"),
			submitButton = $x("//button[contains(text(),'Submit')]");


	@Step("Проверить, что {category} присутствует в списке категорий ")
	public ProfilePage checkCategoriesInList(String category) {
		categoriesList.shouldHave(texts(category));
		return this;
	}

	@Step("Проверить логин пользователя ")
	public ProfilePage checkUsername(String username) {
		usernameInput.shouldHave(text(username));
		return this;
	}

	@Step("Проверить имя пользователя ")
	public ProfilePage checkFirstname(String firstname) {
		firstnameInput.shouldHave(attribute("value", firstname));
		return this;
	}

	@Step("Ввести  имя пользователя ")
	public ProfilePage setFirstname(String firstname) {
		firstnameInput.setValue(firstname);
		return this;
	}

	@Step("Проверить фамилию пользователя ")
	public ProfilePage checkSurname(String surname) {
		surnameInput.shouldHave(attribute("value", surname));
		return this;
	}

	@Step("Ввести фамилию пользователя ")
	public ProfilePage setSurname(String surname) {
		surnameInput.setValue(surname);
		return this;
	}

	@Step("Проверить валюту пользователя ")
	public ProfilePage checkCurrency(String currency) {
		currencyInput.shouldHave(text(currency));
		return this;
	}

	@Step("Ввести значение {category} в поле Category name")
	public ProfilePage setCategory(String category) {
		categoryInput.setValue(category);
		return this;
	}

	@Step("Нажать на кнопку Create")
	public ProfilePage clickCreate() {
		createButton.click();
		return this;
	}

	@Override
	public ProfilePage checkPageLoader() {
		avatar.shouldBe(visible);
		return this;
	}

	@Step("Проверить отображение описания к созданию категорий")
	public ProfilePage checkAddCategoryInfo() {
		addCategoryInfo.shouldBe(visible);
		return this;
	}

	@Step("Создать {n} категорий")
	public ProfilePage createNCategory(int n) {
		for (int i = 0; i < n; i++) {
			setCategory(faker.job().field() + (int) ((Math.random() * (100))));
			clickCreate();
		}
		return this;
	}

	@Step("Проверить количество {n}  категорий ")
	public ProfilePage checkSizeCategoriesInList(int size) {
		categoriesList.shouldHave(CollectionCondition.size(size));
		return this;
	}

	@Step("Нажать на кнопку submit")
	public ProfilePage clickSubmit() {
		submitButton.
				scrollTo().
				click();
		return this;
	}
}
