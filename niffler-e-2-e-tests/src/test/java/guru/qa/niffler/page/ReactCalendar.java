package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$x;

public class ReactCalendar extends BaseComponent<ReactCalendar> {

	protected ReactCalendar(SelenideElement self) {
		super(self);
	}

	public ReactCalendar() {
		super($x("//*[@class='react-datepicker']"));
	}

	public ReactCalendar selectDate(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
		if (date.getMonth() == new Date().getMonth()) {
			self.$(byText(String.valueOf(date.getDate()))).click();
		}
		if (date.before(new Date())) {
			do {
				self.$(byAttribute("aria-label", "Previous Month")).click();
			} while (!self.$(byText((formater.format(date)))).isDisplayed());
			self.$(byText(String.valueOf(date.getDate()))).click();

		}
		if (date.after(new Date())) {
			do {
				self.$(byAttribute("aria-label", "Next Month")).click();
			} while (!self.$(byText((formater.format(date)))).isDisplayed());
			self.$(byText(String.valueOf(date.getDate()))).click();
		}
		return this;
	}
}
