package guru.qa.niffler.test;

import com.github.javafaker.Faker;
import guru.qa.niffler.jupiter.annotation.AddCategoryWithoutDelete;
import guru.qa.niffler.jupiter.annotation.ApiLogin;
import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.jupiter.extension.ApiLoginExtension;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.DbCategoryExtension;
import guru.qa.niffler.jupiter.extension.DbCreateUserExtension;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.page.MainPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Isolated;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Selenide.open;
import static guru.qa.niffler.enums.Alert.*;

@Isolated
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Nested
@Order(1)
@ExtendWith({BrowserExtension.class,
		DbCreateUserExtension.class,
		DbCategoryExtension.class,
		ApiLoginExtension.class})

public class UiSpendingTest extends BaseTest {
	MainPage mainPage = new MainPage();
	Faker faker = new Faker();

	@Test
	@ApiLogin(
			username = "user123111111",
			password = "12345"
	)
	public void selectDataSpend() {
		Date date = new GregorianCalendar(2024, Calendar.FEBRUARY, 1).getTime();
		open(MainPage.url, MainPage.class).
				setSpendDate(date).
				checkSpendDate(date);
	}

	@Test
	@ApiLogin(
			randomUser = true
	)
	public void createNewCategory() {
		String category = faker.job().field();
		open(MainPage.url, MainPage.class).
				clickProfileButton().
				checkPageLoader().
				setCategory(category).
				clickCreate().
				checkAlert(NEW_CAN_NOT_ADD_NEW_CATEGORY_ADDED).
				checkCategoriesInList(category);
	}

	@Test
	@TestUser
	@ApiLogin()
	public void addDuplicateCategory() {
		String category = faker.job().field();
		open(MainPage.url, MainPage.class).
				clickProfileButton().
				checkPageLoader().
				setCategory(category).
				clickCreate().
				setCategory(category).
				clickCreate().
				checkAlert(CAN_NOT_ADD_NEW_CATEGORY);
	}

	@Test
	@TestUser
	@ApiLogin()
	public void checkAddCategoryInfo() {
		open(MainPage.url, MainPage.class).
				clickProfileButton().
				checkPageLoader().
				checkAddCategoryInfo();
	}

	@Test
	@TestUser
	@ApiLogin()
	public void createSevenNewCategory() {
		open(MainPage.url, MainPage.class).
				clickProfileButton().
				checkPageLoader().
				createNCategory(7).
				checkSizeCategoriesInList(7);
	}

	@Test
	@TestUser
	@ApiLogin()
	public void createEightNewCategory() {
		open(MainPage.url, MainPage.class).
				clickProfileButton().
				checkPageLoader().
				createNCategory(8).
				checkSizeCategoriesInList(8);
	}

	@Test
	@TestUser
	@ApiLogin()
	public void createNineNewCategory() {
		open(MainPage.url, MainPage.class).
				clickProfileButton().
				checkPageLoader().
				createNCategory(9).
				checkAlert(CAN_NOT_ADD_NEW_CATEGORY);
	}

	@Test
	@TestUser
	@AddCategoryWithoutDelete
	@ApiLogin()
	public void checkCategoryOnAfterCreate(CategoryJson categoryJson) {
		open(MainPage.url, MainPage.class).
				clickChooseSpendingField().
				checkCategoryDropdownList(categoryJson.category());

	}

	@Test
	@TestUser
	@AddCategoryWithoutDelete
	@ApiLogin()
	public void createSendWithoutDescription(CategoryJson categoryJson) {
		open(MainPage.url, MainPage.class).
				clickChooseSpendingField().
				selectCategoryDropdownList(categoryJson.category()).
				setAmount("6500").
				clickAddSpend().
				checkAlert(SPEND_SUCCESSFULLY_ADD);
	}

	@Test
	@TestUser
	@AddCategoryWithoutDelete
	@ApiLogin()
	public void createSendWithDescription(CategoryJson categoryJson) {
		open(MainPage.url, MainPage.class).
				clickChooseSpendingField().
				selectCategoryDropdownList(categoryJson.category()).
				setAmount("6500").
				setDescription("test").
				clickAddSpend().
				checkAlert(SPEND_SUCCESSFULLY_ADD);
	}

	@Test
	@TestUser
	@AddCategoryWithoutDelete
	@ApiLogin()
	public void createSendLastDate(CategoryJson categoryJson) {
		Date date = new GregorianCalendar(2024, Calendar.SEPTEMBER, 1).getTime();
		open(MainPage.url, MainPage.class).
				clickChooseSpendingField().
				selectCategoryDropdownList(categoryJson.category()).
				setSpendDate(date).
				setAmount("6500").
				setDescription("test").
				clickAddSpend().
				checkErrorFutureDate();
	}

	@Test
	@TestUser
	@AddCategoryWithoutDelete
	@ApiLogin()
	public void createSendBeforeDate(CategoryJson categoryJson) {
		Date date = new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
		open(MainPage.url, MainPage.class).
				clickChooseSpendingField().
				selectCategoryDropdownList(categoryJson.category()).
				setSpendDate(date).
				setAmount("6500").
				setDescription("test").
				clickAddSpend().
				checkAlert(SPEND_SUCCESSFULLY_ADD);
	}

	@Test
	@TestUser
	@AddCategoryWithoutDelete
	@ApiLogin()
	public void deleteSend(CategoryJson categoryJson) {
		String description = "test";
		open(MainPage.url, MainPage.class).
				clickChooseSpendingField().
				selectCategoryDropdownList(categoryJson.category()).
				setAmount("6500").
				setDescription(description).
				clickAddSpend().
				selectSpending(description).
				clickDeleteSelectButton().
				checkListSpendingIsEmpty();
	}

	@Test
	@TestUser
	@AddCategoryWithoutDelete
	@ApiLogin()
	public void checkFilterToday(CategoryJson categoryJson) {
		String description = "test";
		open(MainPage.url, MainPage.class).
				clickChooseSpendingField().
				selectCategoryDropdownList(categoryJson.category()).
				setAmount("6500").
				setDescription(description).
				clickAddSpend().
				checkAlert(SPEND_SUCCESSFULLY_ADD).
				clickFilterToday().
				checkSpendingIsVisible(description);
	}

	@Test
	@TestUser
	@AddCategoryWithoutDelete
	@ApiLogin()
	public void editAmountSpendAfterCreate(CategoryJson categoryJson) {
		String description = "test";
		open(MainPage.url, MainPage.class).
				clickChooseSpendingField().
				selectCategoryDropdownList(categoryJson.category()).
				setAmount("6500").
				setDescription(description).
				clickAddSpend().
				checkAlert(SPEND_SUCCESSFULLY_ADD).
				clickEditSpend().
				editAmount("5000").
				clickSpendEditSubmit().
				checkAlert(SPEND_SUCCESSFULLY_UPDATED);
	}

	@Test
	@TestUser
	@AddCategoryWithoutDelete
	@ApiLogin()
	public void editEditSpendDescriptionSpendAfterCreate(CategoryJson categoryJson) {
		String description = "test";
		open(MainPage.url, MainPage.class).
				clickChooseSpendingField().
				selectCategoryDropdownList(categoryJson.category()).
				setAmount("6500").
				setDescription(description).
				clickAddSpend().
				checkAlert(SPEND_SUCCESSFULLY_ADD).
				clickEditSpend().
				editSpendDescription("testNew").
				clickSpendEditSubmit().
				checkAlert(SPEND_SUCCESSFULLY_UPDATED);
	}
}
