package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.AuthApiClient;
import guru.qa.niffler.api.cookie.ThreadSafeCookieStore;
import guru.qa.niffler.jupiter.annotation.ApiLogin;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import static guru.qa.niffler.jupiter.extension.ContextExtension.context;

public class ApiLoginExtension implements BeforeEachCallback, AfterEachCallback {

	public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(ApiLoginExtension.class);
	private final AuthApiClient authApiClient = new AuthApiClient();


	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		UserJson user = context.getStore(CreateUserExtension.NAMESPACE)
				.get(context.getUniqueId(), UserJson.class);

		AnnotationSupport.findAnnotation(
				context.getRequiredTestMethod(),
				ApiLogin.class
		).ifPresent(annotation -> {
			if (user != null) {
				authApiClient.doLogin(user.username(), user.testData().password());
			} else if (!"".equals(annotation.username())) {
				authApiClient.doLogin(annotation.username(), annotation.password());
			}
		});
	}

	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		ThreadSafeCookieStore.INSTANCE.clearCookies();
	}

	public static void setToken(String token) {
		context().getStore(NAMESPACE).put("token", token);
	}

	public static String getToken() {
		return context().getStore(NAMESPACE).get("token", String.class);
	}

	public static void setCodeChallenge(String codeChallenge) {
		context().getStore(NAMESPACE).put("cc", codeChallenge);
	}

	public static String getCodeChallenge() {
		return context().getStore(NAMESPACE).get("cc", String.class);
	}

	public static void setCodeVerifier(String codeVerifier) {
		context().getStore(NAMESPACE).put("cv", codeVerifier);
	}

	public static String getCodeVerifier() {
		return context().getStore(NAMESPACE).get("cv", String.class);
	}

	public static void setCode(String code) {
		context().getStore(NAMESPACE).put("c", code);
	}

	public static String getCode() {
		return context().getStore(NAMESPACE).get("c", String.class);
	}
}
