package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.model.SpendJson;

public class HttpSpendExtension extends AbstractSpendExtension {

	final private SpendApiClient spendApiClient = new SpendApiClient();

	@Override
	protected SpendJson createSpend(SpendJson spendJson) throws Exception {
		return spendApiClient.createSpend(spendJson);
	}

	@Override
	protected void removeSpend(SpendJson spend) {

	}
}
