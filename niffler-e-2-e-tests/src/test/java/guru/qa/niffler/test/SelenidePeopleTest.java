package guru.qa.niffler.test;

import guru.qa.niffler.data.entity.UserEntity;
import guru.qa.niffler.data.repository.UserRepositoryStringJdbc;
import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.DbCreateUserExtension;
import guru.qa.niffler.jupiter.extension.UserQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.AuthorizationPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.PeoplePage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.open;

@ExtendWith({
		DbCreateUserExtension.class
})
public class SelenidePeopleTest {

	UserRepositoryStringJdbc userRepositoryStringJdbc = new UserRepositoryStringJdbc();
	MainPage mainPage = new MainPage();

	@Test
	@TestUser
	public void checkAnyPeople(UserJson userJson) {
		ArrayList<UserJson> newListUsers = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			newListUsers.add(UserJson.fromEntity(userRepositoryStringJdbc.createUserInUserdata(new UserEntity().fromJson(UserJson.randomUser()))));
		}
		open(PeoplePage.url, AuthorizationPage.class).clickLoginButton().login(userJson);
		mainPage.
				clickAllPeopleButton().
				checkListUser(newListUsers.toArray(UserJson[]::new));
	}
}
