package guru.qa.niffler.test;

import com.codeborne.selenide.ElementsCollection;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.jupiter.annotation.meta.WebTestHttp;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.AuthorizationPage;
import guru.qa.niffler.page.MainPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static guru.qa.niffler.condition.SpendCondition.spendsInTable;


@WebTestHttp
public class HttpSpendingTest extends BaseTest {

	MainPage mainPage = new MainPage();

	@BeforeEach
	void doLogin(UserJson userJson) {
		open(MainPage.url, AuthorizationPage.class).
				clickLoginButton().login(userJson);
	}

	@TestUser
	@Test
	void anotherTest() {
		open(MainPage.url, AuthorizationPage.class).
				loginButtonIsVisible();
	}

	@TestUser
	@GenerateCategory
	@GenerateSpend(1)
	@Test
	void spendingShouldBeDeletedAfterTableAction(SpendJson spendJson) {
		mainPage
				.selectSpending(spendJson.description())
				.clickDeleteSelectButton()
				.checkListSpendingIsEmpty();
	}

	@TestUser
	@GenerateCategory
	@GenerateSpend(2)
	@Test
	void createTwoSpend(SpendJson[] spends) {
		mainPage.checkFullSpend(spends);
	}
}
