package guru.qa.niffler.test;

import guru.qa.niffler.data.entity.AuthorityEntity;
import guru.qa.niffler.data.entity.UserAuthEntity;
import guru.qa.niffler.data.entity.UserEntity;
import guru.qa.niffler.data.repository.UserRepositoryJdbc;
import guru.qa.niffler.data.repository.UserRepositoryStringJdbc;
import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.DbCreateUserExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.AuthorizationPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.ProfilePage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static guru.qa.niffler.enums.Authority.write;
import static org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD;

@Execution(SAME_THREAD)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Nested
@Order(4)
@ExtendWith({BrowserExtension.class, DbCreateUserExtension.class})
public class LoginTest extends BaseTest {
	AuthorizationPage authorizationPage = new AuthorizationPage();

	MainPage mainPage = new MainPage();

	ProfilePage profilePage = new ProfilePage();

	UserRepositoryJdbc userRepositoryJdbc = new UserRepositoryJdbc();

	UserRepositoryStringJdbc userRpStringJdbc = new UserRepositoryStringJdbc();

	@BeforeEach
	void openPage() {
		open(MainPage.url);
	}

	private void doLogin(UserJson userJson) {
		authorizationPage.
				clickLoginButton().
				login(userJson);
	}

	@Test
	@TestUser
	public void loginNewUser(UserJson userJson) {
		doLogin(userJson);
		mainPage.checkTitleIsVisible();
	}

	@Test
	public void checkRegisterButtonOnAuthorizationPage() {
		authorizationPage.checkRegisterButton();
	}

	@Test
	public void checkLoginButtonOnAuthorizationPage() {
		authorizationPage.loginButtonIsVisible();
	}

	@Test
	public void checkTitleOnAuthorizationPage() {
		authorizationPage.checkTitle();
	}

	@Test
	public void checkTitleOnLoginPage() {
		authorizationPage.
				clickLoginButton().
				checkTitle();
	}

	@Test
	public void checkUserNameFieldOnLoginPage() {
		authorizationPage.
				clickLoginButton().
				checkUsername();
	}

	@Test
	public void checkPasswordOnLoginPage() {
		authorizationPage.
				clickLoginButton().
				checkPassword();
	}

	@Test
	public void checkSignInOnLoginPage() {
		authorizationPage.
				clickLoginButton().
				checkSignIn();
	}

	@Test
	public void checkSignUpOnLoginPage() {
		authorizationPage.
				clickLoginButton().
				checkSignUp();
	}

	@Test
	public void checkTitleOnRegisterPage() {
		authorizationPage.
				clickRegisterButton().
				checkTitle();
	}

	@Test
	public void checkUserNameOnRegisterPage() {
		authorizationPage.
				clickRegisterButton().
				checkUserName();
	}

	@Test
	public void checkPasswordOnRegisterPage() {
		authorizationPage.
				clickRegisterButton().
				checkPassword();
	}

	@Test
	@TestUser
	public void loginUserAfterEditByJdbc(UserJson userJson) {
		UserJson editUser = UserJson.dataFromUser(userJson.username());
		UserAuthEntity newAuthEntity = new UserAuthEntity().fromJson(editUser);
		AuthorityEntity authority = new AuthorityEntity();
		authority.setAuthority(write);
		newAuthEntity.setAuthorities(List.of(authority));
		userRepositoryJdbc.updateUserInAuth(newAuthEntity);
		userRepositoryJdbc.updateUserInUserdata(new UserEntity().fromJson(editUser));
		doLogin(editUser);
		mainPage.clickProfileButton();
		profilePage.checkUsername(editUser.username()).
				checkFirstname(editUser.firstname()).
				checkSurname(editUser.surname()).
				checkCurrency(editUser.currency().name());
	}

	@Test
	@TestUser
	public void loginUserAfterEditBySpringJdbc(UserJson userJson) {
		UserJson editUser = UserJson.dataFromUser(userJson.username());
		UserAuthEntity newAuthEntity = new UserAuthEntity().fromJson(editUser);
		AuthorityEntity authority = new AuthorityEntity();
		authority.setAuthority(write);
		newAuthEntity.setAuthorities(List.of(authority));
		userRpStringJdbc.updateUserInAuth(newAuthEntity);
		userRpStringJdbc.updateUserInUserdata(new UserEntity().fromJson(editUser));
		doLogin(editUser);
		mainPage.clickProfileButton();
		profilePage.checkUsername(editUser.username()).
				checkFirstname(editUser.firstname()).
				checkSurname(editUser.surname()).
				checkCurrency(editUser.currency().name());
	}

	@Test
	@TestUser
	public void checkUserAfterCreationInDatabase(UserJson userJson) {
		UserEntity createUser = userRepositoryJdbc.findUserInUserDataByID(userJson.id()).get();
		Assertions.assertEquals(userJson.id(), createUser.getId());
		Assertions.assertEquals(userJson.username(), createUser.getUsername());
		Assertions.assertEquals(userJson.currency(), createUser.getCurrency());
		Assertions.assertEquals(userJson.firstname(), createUser.getFirstname());
		Assertions.assertEquals(userJson.surname(), createUser.getSurname());
	}
}
