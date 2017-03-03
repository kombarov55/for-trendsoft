package db;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import news.Category;
import news.News;

@Component
@Qualifier("NewsRepository")
public class NewsRepository extends AbstractRepository<News> {

	public NewsRepository() {
		super(News.class);
	}

	@Override
	public List<News> lookup(News prototype) throws HibernateException {
		 Criterion c = Restrictions.or(
		 Restrictions.eq("heading", prototype.getHeading()),
		 Restrictions.like("content", getLikeSearchString(prototype.getContent())),
		 Restrictions.eqOrIsNull("category.name", tryToGetCategoryName(prototype.getCategory()))
		 );
		 return performLookup(crit -> crit.createAlias("category", "category").add(c).list());
	}

	@Override
	public News get(String slug) throws HibernateException {
		return performGet(crit -> (News) crit.add(Restrictions.eq("heading", slug)).uniqueResult());

	}
	
	private String tryToGetCategoryName(Category category) {
		return category == null ? "is null" : category.getName();
	}
	
	private String getLikeSearchString(String str) {
		return str == null || str.isEmpty() ? "" : "%" + str + "%"; 
	}
}
