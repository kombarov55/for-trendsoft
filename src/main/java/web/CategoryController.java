package web;

import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import db.IRepository;
import news.Category;

@RestController
public class CategoryController {

	@Autowired
	@Qualifier("CategoryRepository")
	private IRepository<Category> rep;
	private ObjectMapper om = new ObjectMapper();

	@GetMapping("/categories/")
	public List<Category> getAllCategories() {
		return rep.getAll();
	}

	@GetMapping("/categories/{name}")
	public Category getCategory(@PathVariable String name) throws HibernateException {
		return rep.get(name);
	}
	
	public void createCategory(@RequestBody String json) throws HibernateException, JsonParseException, JsonMappingException, IOException {
		Category category = om.readValue(json, Category.class);
		rep.save(category);
	}

	@PutMapping("categories/")
	public void updateCategory(@RequestBody String json)
			throws HibernateException, JsonParseException, JsonMappingException, IOException {
		Category category = om.readValue(json, Category.class);
		rep.update(category);
	}
	
	@DeleteMapping("/categories/{name}")
	public void deleteCategory(@PathVariable String name) throws HibernateException {
		rep.delete(name);
	}
	
	

}
