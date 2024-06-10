package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.OutputType;

import java.io.ByteArrayInputStream;
import java.util.Objects;

public class BaseTest {
	static {
		Configuration.browserSize = "1920x1080";
	}

	@AfterEach
	void doScreenshot() {
		Allure.addAttachment(
				"Screen on test end",
				new ByteArrayInputStream(
						Objects.requireNonNull(
								Selenide.screenshot(OutputType.BYTES)
						)
				)
		);
	}
}
