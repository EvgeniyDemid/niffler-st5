package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.model.CategoryJson;

import java.util.List;

public interface SpendRepository {

	static SpendRepository getInstance() {
		String repo = System.getProperty("repo");

		if ("hibernate".equals(repo)) {
			return new SpendRepositoryHibernate();
		}
		if ("sjdbc".equals(repo)) {
			return new SpendRepositoryStringJdbc();
		}
		return new SpendRepositoryHibernate();
	}

	CategoryEntity createCategory(CategoryEntity category);

	CategoryEntity editCategory(CategoryEntity category);

	void removeCategory(CategoryEntity category);

	SpendEntity createSpend(SpendEntity spend);

	SpendEntity editSpend(SpendEntity spend);

	void removeSpend(SpendEntity spend);

	void removeSpendByCategoryIdOfUser(CategoryJson category);

	List<SpendEntity> findAllByUsername(String username);

	List<CategoryEntity> findCategory(String category);

}
