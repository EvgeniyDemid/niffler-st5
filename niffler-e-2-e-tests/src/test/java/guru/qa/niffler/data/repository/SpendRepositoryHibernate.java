package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.jpa.EmProvider;
import guru.qa.niffler.model.CategoryJson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class SpendRepositoryHibernate implements SpendRepository {
	private final EntityManager em = EmProvider.entityManager(DataBase.SPEND);

	@Override
	public CategoryEntity createCategory(CategoryEntity category) {
		em.persist(category);
		return category;
	}

	@Override
	public CategoryEntity editCategory(CategoryEntity category) {
		return em.merge(category);
	}

	@Override
	public void removeCategory(CategoryEntity category) {
		em.remove(em.merge(category));
	}

	@Override
	public SpendEntity createSpend(SpendEntity spend) {
		em.persist(spend);
		return spend;
	}

	@Override
	public SpendEntity editSpend(SpendEntity spend) {
		return em.merge(spend);
	}

	@Override
	public void removeSpend(SpendEntity spend) {
		em.remove(spend);
	}

	@Override
	public void removeSpendById(String spendId) {
		String hql = "DELETE FROM spend WHERE id= :id";
		Query query = em.createQuery(hql, SpendEntity.class);
		query.setParameter("id", spendId).
				executeUpdate();
	}

	@Override
	public void removeSpendByCategoryIdOfUser(CategoryJson category) {
		String hql = "DELETE FROM spend WHERE category_id= :id";
		Query query = em.createQuery(hql, SpendEntity.class);
		query.setParameter("id", category.id());
	}

	@Override
	public List<SpendEntity> findAllByUsername(String username) {
		String hql = "SELECT * FROM spend WHERE username= :username";
		return (List<SpendEntity>) em.createQuery(hql, SpendEntity.class)
				.setParameter("id", username)
				.getResultList();
	}

	@Override
	public List<CategoryEntity> findCategory(String category) {
		String hql = "SELECT * FROM category WHERE category= :category";
		return (List<CategoryEntity>) em.createQuery(hql, CategoryEntity.class)
				.setParameter("category", category)
				.getResultList();
	}

	@Override
	public CategoryEntity findCategoryByNameAndUsername(String username, String category) {
		String hql = "SELECT * FROM spend WHERE username= :username AND category= :category";
		return em.createQuery(hql, CategoryEntity.class)
				.setParameter("username", username)
				.setParameter("category", category)
				.getSingleResult();
	}

}
