package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import java.util.Date;

import static com.codeborne.selenide.Selenide.$x;

public class ReactCalendar extends BaseComponent<ReactCalendar> {

	protected ReactCalendar(SelenideElement self) {
		super(self);
	}

	public ReactCalendar() {
		super($x("//*[contains(@class,'react-datepicker')]"));
	}

	public ReactCalendar selectDate(Date date) {
		self.$x("//*[contains(@class,'month')]")
	}
}
