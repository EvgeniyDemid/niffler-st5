package guru.qa.niffler.test;

import com.github.javafaker.Faker;
import guru.qa.niffler.jupiter.annotation.AddCategoryWithoutDelete;
import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.DbCategoryExtension;
import guru.qa.niffler.jupiter.extension.DbCreateUserExtension;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.AuthorizationPage;
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
@ExtendWith({BrowserExtension.class, DbCreateUserExtension.class, DbCategoryExtension.class})
public class UiSpendingTest extends BaseTest {
	MainPage mainPage = new MainPage();
	Faker faker = new Faker();

	@BeforeEach
	public void login(UserJson userJson) {
		open(MainPage.url, AuthorizationPage.class).clickLoginButton().login(userJson);
	}

	@Test
	@TestUser
	public void selectDataSpend() {
		Date date = new GregorianCalendar(2024, Calendar.FEBRUARY, 1).getTime();
		mainPage.setSpendDate(date).checkSpendDate(date);
	}

	@Test
	@TestUser
	public void createNewCategory() {
		String category = faker.job().field();
		mainPage.
				clickProfileButton().
				checkPageLoader().
				setCategory(category).
				clickCreate().
				checkAlert(NEW_CAN_NOT_ADD_NEW_CATEGORY_ADDED).
				checkCategoriesInList(category);
	}

	@Test
	@TestUser
	public void addDuplicateCategory() {
		String category = faker.job().field();
		mainPage.
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
	public void checkAddCategoryInfo() {
		mainPage.
				clickProfileButton().
				checkPageLoader().
				checkAddCategoryInfo();
	}

	@Test
	@TestUser
	public void createSevenNewCategory() {
		mainPage.
				clickProfileButton().
				checkPageLoader().
				createNCategory(7).
				checkSizeCategoriesInList(7);
	}

	@Test
	@TestUser
	public void createEightNewCategory() {
		mainPage.
				clickProfileButton().
				checkPageLoader().
				createNCategory(8).
				checkSizeCategoriesInList(8);
	}

	@Test
	@TestUser
	public void createNineNewCategory() {
		mainPage.
				clickProfileButton().
				checkPageLoader().
				createNCategory(9).
				checkAlert(CAN_NOT_ADD_NEW_CATEGORY);
	}

	@Test
	@TestUser
	@AddCategoryWithoutDelete
	public void checkCategoryOnAfterCreate(CategoryJson categoryJson) {
		mainPage.
				clickChooseSpendingField().
				checkCategoryDropdownList(categoryJson.category());

	}

	@Test
	@TestUser
	@AddCategoryWithoutDelete
	public void createSendWithoutDescription(CategoryJson categoryJson) {
		mainPage.
				clickChooseSpendingField().
				selectCategoryDropdownList(categoryJson.category()).
				setAmount("6500").
				clickAddSpend().
				checkAlert(SPEND_SUCCESSFULLY_ADD);
	}

	@Test
	@TestUser
	@AddCategoryWithoutDelete
	public void createSendWithDescription(CategoryJson categoryJson) {
		mainPage.
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
	public void createSendLastDate(CategoryJson categoryJson) {
		Date date = new GregorianCalendar(2024, Calendar.SEPTEMBER, 1).getTime();
		mainPage.
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
	public void createSendBeforeDate(CategoryJson categoryJson) {
		Date date = new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
		mainPage.
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
	public void deleteSend(CategoryJson categoryJson) {
		String description = "test";
		mainPage.
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
	public void checkFilterToday(CategoryJson categoryJson) {
		String description = "test";
		mainPage.
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
	public void editAmountSpendAfterCreate(CategoryJson categoryJson) {
		String description = "test";
		mainPage.
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
	public void editEditSpendDescriptionSpendAfterCreate(CategoryJson categoryJson) {
		String description = "test";
		mainPage.
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
