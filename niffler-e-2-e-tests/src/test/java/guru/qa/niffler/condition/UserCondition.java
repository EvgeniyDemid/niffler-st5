package guru.qa.niffler.condition;

import com.codeborne.selenide.WebElementsCondition;
import guru.qa.niffler.model.UserJson;

public class UserCondition {
	public static WebElementsCondition userInTable(UserJson... expectedUsers) {
		return new UserInTableCondition(expectedUsers);
	}
}
