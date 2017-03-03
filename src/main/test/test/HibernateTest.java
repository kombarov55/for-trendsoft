package test;
import static org.junit.Assert.*;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import db.AbstractRepository;
import news.Category;
import news.News;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { config.RootConfig.class })
public class HibernateTest {

	@Autowired
	private SessionFactory sf;

	@Autowired
	@Qualifier("NewsRepository")
	private AbstractRepository<News> newsRepository;

	@Autowired
	@Qualifier("CategoryRepository")
	private AbstractRepository<Category> categoryRepository;

	@Test
	public void test() throws Exception {
		Category SPORT = categoryRepository.get("Sport");
		News news = new News("test heading", "test content", SPORT);
		
		newsRepository.save(news);
		assertNotNull(newsRepository.get(news.getHeading()));
		
		news.setHeading("another heading");
		newsRepository.update(news);
		assertNotNull(newsRepository.get(news.getHeading()));
		
		News p = new News();
		p.setContent("b");
		System.out.println(newsRepository.lookup(p));
		
		newsRepository.delete(news.getHeading());
		assertNull(newsRepository.get(news.getHeading()));
	}

}
