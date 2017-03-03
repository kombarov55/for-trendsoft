package db;

import java.util.Collections;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import news.Category;

@Component
@Qualifier("CategoryRepository")
public class CategoryRepository extends AbstractRepository<Category> {
	
	public CategoryRepository() {
		super(Category.class);
	}

	@Override
	public Category get(String slug) throws HibernateException {
		return performGet(criteria -> (Category) criteria.add(Restrictions.eq("name", slug)).uniqueResult());
	}

	@Override
	public List<Category> lookup(Category prototype) throws HibernateException {
		// TODO Auto-generated method stub
		return Collections.EMPTY_LIST;
	}

}
