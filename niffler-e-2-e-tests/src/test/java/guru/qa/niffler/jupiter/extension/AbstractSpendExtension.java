package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSpendExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

	public static final ExtensionContext.Namespace NAMESPACE
			= ExtensionContext.Namespace.create(AbstractSpendExtension.class);

	protected abstract Object createSpend(SpendJson spendJson) throws Exception;

	protected abstract void removeSpend(SpendJson spendJson);

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {

		CategoryJson category = context.getStore(AbstractCategoryExtension.NAMESPACE)
				.get(context.getUniqueId(), CategoryJson.class);

		AnnotationSupport.findAnnotation(
				context.getRequiredTestMethod(),
				GenerateSpend.class
		).ifPresent(
				generateSpend -> {
					ArrayList<SpendJson> arrayListSpend = new ArrayList<>();
					for (int i = 0; i < generateSpend.value(); i++) {
						SpendJson randomSpend = SpendJson.fromEntity(SpendEntity.randomByCategory(category));
						try {
							arrayListSpend.add((SpendJson) createSpend(randomSpend));
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
					context.getStore(NAMESPACE).put(
							context.getUniqueId(),
							arrayListSpend
					);
				}
		);
	}

	@Override
	public void afterEach(ExtensionContext context) {
		for (Object spendJson : context.getStore(NAMESPACE).get(context.getUniqueId(), List.class)) {
			removeSpend((SpendJson) spendJson);
		}
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
		Class<?> type = parameterContext
				.getParameter()
				.getType();
		return type.isAssignableFrom(SpendJson.class) || type.isAssignableFrom(SpendJson[].class);
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
		List<SpendJson> spendJsonList =
				extensionContext.
						getStore(NAMESPACE).
						get(extensionContext.getUniqueId(), List.class);
		Class<?> type = parameterContext
				.getParameter()
				.getType();
		return type.isAssignableFrom(SpendJson.class) ?
				spendJsonList.getFirst() : spendJsonList.toArray(SpendJson[]::new);
	}
}
