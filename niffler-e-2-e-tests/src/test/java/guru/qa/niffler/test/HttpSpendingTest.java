package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.jupiter.annotation.meta.WebTestHttp;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.AuthorizationPage;
import guru.qa.niffler.page.MainPage;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.open;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Nested
@Order(3)
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
	@GenerateSpend
	@Test
	void spendingShouldBeDeletedAfterTableAction(SpendJson spendJson) {
		mainPage
				.selectSpending(spendJson.description())
				.clickDeleteSelectButton()
				.checkListSpendingIsEmpty();
	}
}
