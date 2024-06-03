package guru.qa.niffler.api;

import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import retrofit2.Call;

public class SpendApiClient extends ApiClient {

	private final SpendApi spendApi;

	public SpendApiClient() {
		super(CFG.frontUrl());
		this.spendApi = retrofit.create(SpendApi.class);
	}

	public SpendJson createSpend(SpendJson spendJson) throws Exception {
		return spendApi.createSpend(spendJson).execute().body();
	}

	public CategoryJson createCategory(CategoryJson categoryJson) throws Exception {
		return spendApi.createCategory(categoryJson).execute().body();
	}
}
