package guru.qa.niffler.test;

import com.github.javafaker.Faker;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.DbCreateUserExtension;
import guru.qa.niffler.page.AuthorizationPage;
import guru.qa.niffler.page.MainPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.open;
import static guru.qa.niffler.enums.Alert.PROFILE_SUCCESSFULLY_UPDATE;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Nested
@Order(2)
@ExtendWith({BrowserExtension.class, DbCreateUserExtension.class})
public class UserTest extends BaseTest {

	AuthorizationPage authorizationPage = new AuthorizationPage();
	Faker faker = new Faker();

	@BeforeEach
	public void login() {
		open(MainPage.url);
	}

	@Test
	public void registrationNewUser() {
		String username = faker.name().username();
		String password = "12345";
		authorizationPage.
				clickRegisterButton().
				userNameFieldSetValue(username).
				passwordFieldSetValue(password).
				signUpClick().
				checkMessageSuccessfulRegistration();
	}

	@Test
	public void checkUserNameAfterRegistration() {
		String username = faker.name().username();
		String password = "12345";
		authorizationPage.
				clickRegisterButton().
				userNameFieldSetValue(username).
				passwordFieldSetValue(password).
				signUpClick().
				checkMessageSuccessfulRegistration().
				clickSignUp().
				login(username, password).
				clickProfileButton().
				checkUsername(username);
	}

	@Test
	public void addNameAfterRegistration() {
		String username = faker.name().username();
		String password = "12345";
		String firstname = faker.name().firstName();
		authorizationPage.
				clickRegisterButton().
				userNameFieldSetValue(username).
				passwordFieldSetValue(password).
				signUpClick().
				checkMessageSuccessfulRegistration().
				clickSignUp().
				login(username, password).
				clickProfileButton().
				setFirstname(firstname).
				clickSubmit().
				checkAlert(PROFILE_SUCCESSFULLY_UPDATE);
	}

	@Test
	public void addSurnameAfterRegistration() {
		String username = faker.name().username();
		String password = "12345";
		String firstname = faker.name().name();
		authorizationPage.
				clickRegisterButton().
				userNameFieldSetValue(username).
				passwordFieldSetValue(password).
				signUpClick().
				checkMessageSuccessfulRegistration().
				clickSignUp().
				login(username, password).
				clickProfileButton().
				setSurname(firstname).
				clickSubmit().
				checkAlert(PROFILE_SUCCESSFULLY_UPDATE);
	}

	@Test
	public void userLoginAfterRegister() {
		String username = faker.name().username();
		String password = "12345";
		authorizationPage.
				clickRegisterButton().
				userNameFieldSetValue(username).
				passwordFieldSetValue(password).
				signUpClick().
				checkMessageSuccessfulRegistration().
				clickSignUp().
				login(username, password).
				checkPageLoader();
	}
}
