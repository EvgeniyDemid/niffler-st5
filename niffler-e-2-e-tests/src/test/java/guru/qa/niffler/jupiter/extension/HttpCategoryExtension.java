package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.model.CategoryJson;

public class HttpCategoryExtension extends AbstractCategoryExtension {

	final private SpendApiClient spendApiClient = new SpendApiClient();

	@Override
	protected CategoryJson createCategory(CategoryJson category) throws Exception {
		return spendApiClient.createCategory(category);
	}

	@Override
	protected void removeCategory(CategoryJson category) {

	}
}
