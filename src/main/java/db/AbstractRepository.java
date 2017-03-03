package db;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import news.News;

public abstract class AbstractRepository<T> implements IRepository<T> {

	@Autowired
	protected SessionFactory sf;
	private Class<T> type;

	public AbstractRepository(Class<T> type) {
		this.type = type;
	}

	@Override
	public void save(T t) throws HibernateException {
		performDbUpdate(session -> session.save(t));
	}

	@Override
	public void update(T t) throws HibernateException {
		performDbUpdate(session -> session.update(t));
	}

	public abstract T get(String slug) throws HibernateException;

	@Override
	public List<T> getAll() throws HibernateException {
		return performLookup(criteria -> criteria.list());
	}

	public abstract List<T> lookup(T prototype) throws HibernateException;

	@Override
	public void delete(News news) throws HibernateException {
		performDbUpdate(session -> session.delete(news));
	}

	@Override
	public void delete(String slug) throws HibernateException {
		performDbUpdate(session -> {
			News news = (News) session.createCriteria(News.class).add(Restrictions.eq("heading", slug)).uniqueResult();
			session.delete(news);
		});
	}

	protected void performDbUpdate(Consumer<Session> c) throws HibernateException {
		try (Session session = sf.openSession()) {
			session.beginTransaction();
			c.accept(session);
			session.getTransaction().commit();
		}
	}

	protected T performGet(Function<Criteria, T> f) throws HibernateException {
		try (Session session = sf.openSession()) {
			session.beginTransaction();
			T ret = f.apply(session.createCriteria(type));
			session.getTransaction().commit();
			return ret;
		}
	}

	protected List<T> performLookup(Function<Criteria, List<T>> f) throws HibernateException {
		try (Session session = sf.openSession()) {
			session.beginTransaction();
			List<T> ret = f.apply(session.createCriteria(type));
			session.getTransaction().commit();
			return ret;
		}
	}

}
