package guru.qa.niffler.test;

import guru.qa.niffler.data.entity.UserEntity;
import guru.qa.niffler.data.repository.UserRepositoryJdbc;
import guru.qa.niffler.data.repository.UserRepositoryStringJdbc;
import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTestJdbc;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.DbCreateUserExtension;
import guru.qa.niffler.jupiter.extension.UserQueueExtension;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.AuthorizationPage;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.PeoplePage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.open;
import static guru.qa.niffler.enums.Alert.*;
import static guru.qa.niffler.jupiter.annotation.User.UserType.*;

@ExtendWith({
		BrowserExtension.class, UserQueueExtension.class, DbCreateUserExtension.class
})
public class PeopleTest {

	MainPage mainPage = new MainPage();

	UserRepositoryStringJdbc userRepositoryStringJdbc = new UserRepositoryStringJdbc();

	@Test
	public void sendInvitationFriend(
			@User(userType = INVITE_SENT) UserJson userInviteSend,
			@User(userType = FRIEND) UserJson userFriend
	) {
		open(PeoplePage.url, AuthorizationPage.class).clickLoginButton().login(userFriend);
		mainPage.
				clickAllPeopleButton().
				checkPageLoader().
				clickActionFromUser(userInviteSend.username()).
				checkAlert(INVITATION_IS_SENT);
	}

	@Test
	public void submitInvitationFriend(
			@User(userType = FRIEND) UserJson userFriend,
			@User(userType = INVITE_RECEIVED) UserJson userInviteReceived
	) {
		open(FriendsPage.url, AuthorizationPage.class).clickLoginButton().login(userFriend);
		mainPage.
				clickFriendsButton().
				checkPageLoader().
				clickSubmit(userInviteReceived.username()).
				checkAlert(INVITATION_IS_ACCEPTED);
	}

	@Test
	public void declineInvitationFriend(
			@User(userType = FRIEND) UserJson userFriend,
			@User(userType = INVITE_RECEIVED) UserJson userInviteReceived
	) {
		open(FriendsPage.url, AuthorizationPage.class).clickLoginButton().login(userFriend);
		mainPage.
				clickFriendsButton().
				checkPageLoader().
				clickDecline(userInviteReceived.username()).
				checkAlert(INVITATION_IS_DECLINED);
	}

	@Test
	public void deleteFriend(
			@User(userType = FRIEND) UserJson userFriend,
			@User(userType = FRIEND) UserJson userFriend1
	) {
		open(FriendsPage.url, AuthorizationPage.class).clickLoginButton().login(userFriend);
		mainPage.
				clickFriendsButton().
				checkPageLoader().
				clickDelete(userFriend1.username()).
				checkAlert(FRIEND_IS_DELETED);
	}

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
