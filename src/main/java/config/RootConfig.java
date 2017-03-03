package config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import db.AbstractRepository;
import news.Category;

@Configuration
@ComponentScan(basePackageClasses = { news.News.class, db.NewsRepository.class })
public class RootConfig {

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUsername("postgres");
		ds.setPassword("root");
		ds.setUrl("jdbc:postgresql://127.0.0.1:5432/news");
		return ds;
	}

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean sf = new LocalSessionFactoryBean();

		sf.setDataSource(getDataSource());
		Properties props = new Properties();
		props.setProperty("dialect", "org.hibernate.dialect.PostgreSQLDialect");
		props.setProperty("show_sql", "true");
		sf.setHibernateProperties(props);
		sf.setAnnotatedClasses(news.News.class, news.Category.class);

		return sf;
	}
	
}
