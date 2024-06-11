package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.DbCreateUserExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.AuthorizationPage;
import guru.qa.niffler.page.MainPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Selenide.open;

@ExtendWith({BrowserExtension.class, DbCreateUserExtension.class})
public class UiSpendingTest {
	MainPage mainPage = new MainPage();

	@BeforeEach
	public void login(UserJson userJson) {
		open(MainPage.url, AuthorizationPage.class).clickLoginButton().login(userJson);
	}

	@Test
	@TestUser
	public void selectDataSpend(UserJson userJson) {
		Date date = new GregorianCalendar(2024, Calendar.FEBRUARY, 1).getTime();
		mainPage.setSpendDate(date).checkSpendDate(date);
	}

	@Test
	@TestUser
	public void addNewCategory() {
		String category = "Обучение";
		mainPage.
	}
}
