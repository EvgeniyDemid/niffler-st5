package guru.qa.niffler.test;

import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.repository.SpendRepositoryJdbc;
import guru.qa.niffler.data.repository.SpendRepositoryStringJdbc;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.jupiter.annotation.meta.WebTestJdbc;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.AuthorizationPage;
import guru.qa.niffler.page.MainPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;

@WebTestJdbc
public class DbSpendingTest extends BaseTest {

	MainPage mainPage = new MainPage();

	SpendRepositoryJdbc spendRepositoryJdbc = new SpendRepositoryJdbc();
	SpendRepositoryStringJdbc spendRepositoryStringJdbc = new SpendRepositoryStringJdbc();

	@BeforeEach
	void doLogin(UserJson userJson) {
		open(MainPage.url, AuthorizationPage.class).
				clickLoginButton().
				login(userJson);
	}

	@TestUser
	@GenerateCategory
	@GenerateSpend(1)
	@Test
	void spendingShouldBeVisibleAfterCreate(SpendJson spendJson) {
		mainPage.checkSpendingIsVisible(spendJson.description());
	}

	@TestUser
	@GenerateCategory
	@GenerateSpend(1)
	@Test
	void checkSpendingAfterCreateJdbc(SpendJson spendJson) {
		List<SpendEntity> listSpend = spendRepositoryJdbc.findAllByUsername(spendJson.username());
		for (SpendEntity spend : listSpend) {
			mainPage.checkSpendingIsVisible(spend.getDescription());
		}
	}

	@TestUser
	@GenerateCategory
	@GenerateSpend(1)
	@Test
	void checkSpendingAfterCreateStringJdbc(SpendJson spendJson) {
		List<SpendEntity> listSpend = spendRepositoryStringJdbc.findAllByUsername(spendJson.username());
		for (SpendEntity spend : listSpend) {
			mainPage.checkSpendingIsVisible(spend.getDescription());
		}
	}
}
