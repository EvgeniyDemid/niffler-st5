package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
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
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;

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
	@GenerateSpend
	@Test
	void spendingShouldBeVisibleAfterCreate(SpendJson spendJson) {
		mainPage.checkSpendingIsVisible(spendJson.description());
	}

	@TestUser
	@GenerateCategory
	@GenerateSpend
	@Test
	void checkSpendingAfterCreateJdbc(SpendJson spendJson) {
		List<SpendEntity> listSpend = spendRepositoryJdbc.findAllByUsername(spendJson.username());
		for (SpendEntity spend : listSpend) {
			mainPage.checkSpendingIsVisible(spend.getDescription());
		}
	}

	@TestUser
	@GenerateCategory
	@GenerateSpend
	@Test
	void checkSpendingAfterCreateStringJdbc(SpendJson spendJson) {
		List<SpendEntity> listSpend = spendRepositoryStringJdbc.findAllByUsername(spendJson.username());
		for (SpendEntity spend : listSpend) {
			mainPage.checkSpendingIsVisible(spend.getDescription());
		}
	}
}
