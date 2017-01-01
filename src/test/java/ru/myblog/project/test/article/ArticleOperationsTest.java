package ru.myblog.project.test.article;

import java.util.Calendar;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ru.myblog.project.dao.EntityDao;
import ru.myblog.project.entities.Article;

@RunWith(MockitoJUnitRunner.class)
public class ArticleOperationsTest extends Assert {

	private Article article;
	
	@SuppressWarnings("deprecation")
	@Mock
	private EntityDao entityDao;
	
	@Before
	public void createArticle() {
		article = new Article();
		article.setCreatedTime(Calendar.getInstance());
		article.setText("It's simple test for articles operations");
	}
	
	@Test
	public void saveArticle() {
		entityDao.saveEntity(article);
	}
	
	@Test
	public void updateArticle() {
		article.setText("Two");
		entityDao.updateEntity(article);
	}
	
	@Test
	public void getArticle() {
		Assert.assertNotNull(this.article);
		this.article.setID("0");
		entityDao.saveEntity(article);
		Article article = (Article) entityDao.findEntity("0", Article.class);
		//Assert.assertNotNull(article);
	}
	
	@After
	public void deleteArticle() {
		entityDao.deleteEntity(article);
	}
}
