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
		String actualUser = null;
		for (UserJson expectedUserForRow : expectUsers) {
			boolean usernameResult = false;
			for (int i = 0; i < elements.size(); i++) {
				WebElement row = elements.get(i);
				List<WebElement> td = row.findElements(By.cssSelector("td"));
				String usernameActual = td.get(1).getText();
				String nameActual = td.get(2).getText();
				actualUser = usernameActual + " " + nameActual;
				usernameResult = usernameActual.contains(expectedUserForRow.username());
				if (usernameResult) {
					boolean nameResult = nameActual.contains(
							expectedUserForRow.firstname() + " " + expectedUserForRow.surname());
					if (!nameResult) {
						return CheckResult.rejected(
								"User table: name mismatch",
								actualUser
						);
					}
					break;
				}
			}
			if (!usernameResult) {
				return CheckResult.rejected(
						"User table: username noFound",
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
