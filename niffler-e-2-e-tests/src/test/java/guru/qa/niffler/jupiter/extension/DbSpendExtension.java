package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.model.SpendJson;

public class DbSpendExtension extends AbstractSpendExtension {

	private final SpendRepository spendRepository = SpendRepository.getInstance();

	@Override
	protected SpendJson createSpend(SpendJson spend) {
		CategoryEntity category = spendRepository.findCategoryByNameAndUsername(spend.username(), spend.category());
		return SpendJson.fromEntity(spendRepository.createSpend(SpendEntity.fromJson(spend, category)));
	}

	@Override
	protected void removeSpend(SpendJson spend) {
		spendRepository.removeSpendById(String.valueOf(spend.id()));
	}
}
