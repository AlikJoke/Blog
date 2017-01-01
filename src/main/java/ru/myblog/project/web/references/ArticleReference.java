package ru.myblog.project.web.references;

import java.net.URI;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.aspectj.EnableSpringConfigured;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import ru.myblog.project.entities.Article;
import ru.myblog.project.web.utils.UriHelper;

@Service("articleReference")
@Configurable
@EnableSpringConfigured
public class ArticleReference implements Reference {

	public final static String PATH = "/article/{id}";
	private final String id;

	@Override
	public String getHref() {
		String id = this.id;
		if (!StringUtils.hasLength(id))
			id = "template";
		UriComponents uriComponents = UriComponentsBuilder.fromPath(PATH).queryParam(id).build();
		return uriComponents.expand(id).encode().toString();
	}

	public ArticleReference(URI uri) {
		UriHelper helper = new UriHelper(uri, PATH);
		this.id = helper.getPathVariable("id");
	}

	public ArticleReference(Article article) {
		this.id = article.getID();
	}

	public ArticleReference() {
		this.id = null;
	}
}
