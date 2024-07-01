package guru.qa.niffler.jupiter.extension;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import guru.qa.niffler.api.cookie.ThreadSafeCookieStore;
import guru.qa.niffler.config.Config;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Cookie;

import java.io.ByteArrayInputStream;
import java.util.Objects;

public class BrowserExtension implements TestExecutionExceptionHandler,
		AfterEachCallback,
		LifecycleMethodExecutionExceptionHandler,
		BeforeTestExecutionCallback {

	@Override
	public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
		doScreenShot();
		throw throwable;
	}

	@Override
	public void handleBeforeEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
		doScreenShot();
		throw throwable;
	}

	@Override
	public void handleAfterEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
		doScreenShot();
		throw throwable;
	}

	private void doScreenShot() {
		if (WebDriverRunner.hasWebDriverStarted()) {
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

	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		if (WebDriverRunner.hasWebDriverStarted()) {
			Selenide.closeWebDriver();
		}
	}

	@Override
	public void beforeTestExecution(ExtensionContext context) throws Exception {
		if (ApiLoginExtension.getToken() != null) {
			Selenide.open(Config.getInstance().frontUrl());
			Selenide.sessionStorage().setItem("id_token", ApiLoginExtension.getToken());
			WebDriverRunner.getWebDriver().manage()
					.addCookie(
							new Cookie(
									"JSESSIONID",
									ThreadSafeCookieStore.INSTANCE.getCookieValue("JSESSIONID")
							)
					);
		}
	}
}
