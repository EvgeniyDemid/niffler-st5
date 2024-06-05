package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.repository.SpendRepository;

public class DbSpendExtension extends AbstractSpendExtension {

	private final SpendRepository spendRepository = SpendRepository.getInstance();

	@Override
	protected Object createSpend(SpendEntity spendEntity) {
		return spendRepository.createSpend(spendEntity);
	}

	@Override
	protected void removeSpend(SpendEntity spend) {
		spendRepository.removeSpend(spend);
	}


}
