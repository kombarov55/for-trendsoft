package db;

import java.util.List;

import org.hibernate.HibernateException;

import news.News;

public interface IRepository<T> {
	
	void save(T t) throws HibernateException;

	public void update(T t) throws HibernateException;

	T get(String slug) throws HibernateException;
	
	List<T> getAll() throws HibernateException;

	List<T> lookup(T prototype) throws HibernateException;

	void delete(News news) throws HibernateException;

	void delete(String slug) throws HibernateException;
	
}
