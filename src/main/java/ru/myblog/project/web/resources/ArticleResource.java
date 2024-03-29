package ru.myblog.project.web.resources;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.annotation.ThreadSafe;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.util.StringUtils;

import ru.myblog.project.entities.Article;
import ru.myblog.project.web.references.ArticleReference;
import ru.myblog.project.web.references.ContentReference;
import ru.myblog.project.web.utils.ResourceLink;

@ThreadSafe
@JsonSerialize(include = Inclusion.NON_NULL)
public class ArticleResource extends Resource {

	private static final long serialVersionUID = -3922661241182261413L;

	public String text;
	public Date createdDate;
	public List<AttachmentResource> attachments;

	@JsonCreator
	public ArticleResource(@JsonProperty("id") String id, @JsonProperty("text") String text,
			@JsonProperty("createdDate") Date createdDate, @JsonProperty("links") List<ResourceLink> links,
			@JsonProperty("attachments") List<AttachmentResource> attachments) {
		super(id, links);
		this.text = text;
		this.createdDate = createdDate;
		this.attachments = attachments;
	}

	public ArticleResource() {
		super();
		this.createdDate = new Date();
		this.text = null;
		this.attachments = null;
	}

	public ArticleResource(Article article) {
		super(article);
		this.text = article.getText();
		this.createdDate = article.getCreatedTime() == null ? null : article.getCreatedTime().getTime();
		this.attachments = article.getAttachments().stream()
				.filter(attachment -> StringUtils.hasLength(attachment.getAttachmentName()))
				.map(attachment -> new AttachmentResource(attachment)).collect(Collectors.toList());
		addLinks(article);
	}

	void addLinks(Article article) {
		if (this.id != null)
			this.links.add(new ResourceLink("self", new ArticleReference(article).getHref()));
		this.links.add(new ResourceLink("save", new ArticleReference(article).getHref()));
		this.links.add(new ResourceLink("content", new ContentReference().getHref()));
	}
}
