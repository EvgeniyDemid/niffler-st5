package guru.qa.niffler.condition;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebElementsCondition;
import com.codeborne.selenide.impl.CollectionSource;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.utils.DateUtils;
import guru.qa.niffler.utils.SpendsUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SpendsInTableCondition extends WebElementsCondition {

	private final SpendJson[] expectedSpends;

	public SpendsInTableCondition(SpendJson[] expectedSpends) {
		this.expectedSpends = expectedSpends;
	}

	@Nonnull
	@Override
	public CheckResult check(Driver driver, List<WebElement> elements) {
		if (elements.size() != expectedSpends.length) {
			return CheckResult.rejected(
					"Spending table size mismatch",
					elements.size()
			);
		}

		for (int i = 0; i < elements.size(); i++) {
			WebElement row = elements.get(i);
			SpendJson expectedSpendForRow = expectedSpends[i];

			List<WebElement> td = row.findElements(By.cssSelector("td"));

			String dateActual = td.get(1).getText();
			String amountActual = td.get(2).getText();
			String currencyActual = td.get(3).getText();
			String categoryActual = td.get(4).getText();
			String descriptionActual = td.get(5).getText();

			String actualSpend = "- " + dateActual +
					" | " + amountActual +
					" | " + currencyActual +
					" | " + categoryActual +
					" | " + descriptionActual;

			boolean dateResult = dateActual.contains(
					DateUtils.formatDate(
							expectedSpendForRow.spendDate(),
							"dd MMM yy"
					)
			);

			if (!dateResult) {
				return CheckResult.rejected(
						"Spending table: date mismatch",
						actualSpend
				);
			}
			String amountExp =
					String.valueOf(expectedSpendForRow.amount()).endsWith(".0") ?
							String.valueOf(expectedSpendForRow.amount()).replace(".0", "") :
							String.valueOf(expectedSpendForRow.amount());
			boolean amountResult = amountActual.contains(amountExp);

			if (!amountResult) {
				return CheckResult.rejected(
						"Spending table: amount mismatch",
						actualSpend
				);
			}

			boolean currencyResult = currencyActual.contains(
					String.valueOf(expectedSpendForRow.currency())
			);

			if (!currencyResult) {
				return CheckResult.rejected(
						"Spending table: currency mismatch",
						actualSpend
				);
			}

			boolean categoryResult = categoryActual.contains(
					expectedSpendForRow.category());

			if (!categoryResult) {
				return CheckResult.rejected(
						"Spending table: category mismatch",
						actualSpend
				);
			}

			boolean descriptionResult = descriptionActual.contains(
					expectedSpendForRow.description());

			if (!descriptionResult) {
				return CheckResult.rejected(
						"Spending table: description mismatch",
						actualSpend
				);
			}

		}
		return CheckResult.accepted();
	}

	@Override
	public void fail(CollectionSource collection, CheckResult lastCheckResult, @Nullable Exception cause, long timeoutMs) {
		String actualElementText = lastCheckResult.getActualValue();

		String message = lastCheckResult.getMessageOrElse(() -> "Spending mismatch");
		throw new SpendMismatchException(message, collection, SpendsUtils.spendFormat(expectedSpends), actualElementText, explanation, timeoutMs, cause);
	}

	@Override
	public String toString() {
		return "";
	}
}