package ru.myblog.project.web.references;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import ru.myblog.project.entities.Article;
import ru.myblog.project.operations.RestOperations;
import ru.myblog.project.web.resources.ArticleResource;
import ru.myblog.project.web.resources.Resource;
import ru.myblog.project.web.utils.UriHelper;

public class ArticleReference implements Reference {

	public final static String PATH = "/article/{id}";
	private final String id;

	@Autowired
	RestOperations restOperations;
	
	@Override
	public String getHref() {
		UriComponents uriComponents = UriComponentsBuilder.fromPath(PATH).queryParam(this.id).build();
		return uriComponents.expand(this.id).encode().toString();
	}

	public ArticleReference(URI uri) {
		UriHelper helper = new UriHelper(uri, PATH);
		this.id = helper.getPathVariable("id");
	}

	public ArticleReference(Article article) {
		this.id = article.getID();
	}

	public ArticleResource doGet() {
		return (ArticleResource) restOperations.get(this.id, Article.class);
	}
	
	public void doPost(Resource resource) {
		restOperations.create(resource);
	}
	
	public void doPut(Resource resource) {
		restOperations.update(resource);
	}
}
