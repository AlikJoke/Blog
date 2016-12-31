package ru.myblog.project.web.resources;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import ru.myblog.project.entities.Article;

public class ArticleResource extends Resource {

	public String text;
	public Date createdDate;

	@JsonCreator
	public ArticleResource(@JsonProperty("id") String id, @JsonProperty("text") String text,
			@JsonProperty("createdDate") Date createdDate) {
		super(id);
		this.text = text;
		this.createdDate = createdDate;
	}

	public ArticleResource(Article article) {
		super(article);
		this.text = article.getText();
		this.createdDate = article.getCreatedTime().getTime();
	}
}
