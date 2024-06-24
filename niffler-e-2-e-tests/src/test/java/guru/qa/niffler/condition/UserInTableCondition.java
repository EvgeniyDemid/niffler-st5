package guru.qa.niffler.condition;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebElementsCondition;
import com.codeborne.selenide.impl.CollectionSource;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.utils.UserUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class UserInTableCondition extends WebElementsCondition {

	private final UserJson[] expectUsers;

	public UserInTableCondition(UserJson[] expectUsers) {
		this.expectUsers = expectUsers;
	}

	@Nonnull
	@Override
	public CheckResult check(Driver driver, List<WebElement> elements) {

		for (int i = 0; i < elements.size(); i++) {
			WebElement row = elements.get(i);
			UserJson expectedUserForRow = expectUsers[i];

			List<WebElement> td = row.findElements(By.cssSelector("td"));

			String usernameActual = td.get(1).getText();
			String nameActual = td.get(2).getText();

			String actualUser = "- " + usernameActual + " | " + nameActual;

			boolean dateResult = usernameActual.contains(
					expectedUserForRow.username()
			);

			if (!dateResult) {
				return CheckResult.rejected(
						"User table: username mismatch",
						actualUser
				);
			}

			boolean nameResult = nameActual.contains(
					expectedUserForRow.firstname() + " " + expectedUserForRow.surname()
			);

			if (!nameResult) {
				return CheckResult.rejected(
						"User table: username mismatch",
						actualUser
				);
			}
		}
		return CheckResult.accepted();
	}

	@Override
	public void fail(CollectionSource collection, CheckResult lastCheckResult, @Nullable Exception cause, long timeoutMs) {
		String actualElementText = lastCheckResult.getActualValue();

		String message = lastCheckResult.getMessageOrElse(() -> "User mismatch");
		throw new UserMismatchException(message, collection, UserUtils.userFormat(expectUsers), actualElementText, explanation, timeoutMs, cause);
	}

	@Override
	public String toString() {
		return "";
	}
}
