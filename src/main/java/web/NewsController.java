package web;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import db.IRepository;
import news.News;

@RestController
public class NewsController {

	@Autowired
	@Qualifier("NewsRepository")
	private IRepository<News> rep;
	
	private ObjectMapper om = new ObjectMapper();
	
	@GetMapping("/news/{heading}")
	public News getNews(@PathVariable String heading) throws HibernateException {
		return rep.get(heading);
	}
	
	@GetMapping("/news/")
	public List<News> getAllNews() throws HibernateException {
		return rep.getAll();
	}
	
	@PostMapping("/news/")
	public void addNews(@RequestBody String json) throws Exception {
		System.out.println(json);
		News news = om.readValue(json, News.class);
		rep.save(news);
	}
	
	@PutMapping("/news/")
	public void updateNews(@RequestBody String json) throws Exception {
		News news = om.readValue(json, News.class);
		rep.update(news);
	}
	
	@DeleteMapping("/news/{heading}")
	public void deleteNews(@PathVariable String heading) throws Exception {
		rep.delete(heading);
	}
}
