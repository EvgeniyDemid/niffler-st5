package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.TestClassOrder;
import org.openqa.selenium.OutputType;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Objects;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class BaseTest {
	static {
		Configuration.browserSize = "1920x1080";
	}

	@BeforeEach
	void set() {
		Allure.getLifecycle().updateTestCase(testResult -> {
			testResult.setStart(new Date().getTime());
		});
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
