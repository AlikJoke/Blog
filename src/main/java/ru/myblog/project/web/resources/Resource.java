package ru.myblog.project.web.resources;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ru.myblog.project.entities.Article;
import ru.myblog.project.entities.Attachment;
import ru.myblog.project.entities.SubObject;
import ru.myblog.project.entities.utils.TemplateName;
import ru.myblog.project.operations.RestOperations;
import ru.myblog.project.web.references.ArticleReference;
import ru.myblog.project.web.utils.ResourceLink;

@Configurable
public class Resource {

	@Autowired
	private RestOperations restOperations;

	public String id;
	public List<ResourceLink> links = Collections.emptyList();

	public static Function<Resource, SubObject.Mapped> resourceToEntity = new Function<Resource, SubObject.Mapped>() {

		@SuppressWarnings("unchecked")
		@Override
		public SubObject.Mapped apply(Resource resource) {
			if (resource instanceof ArticleResource) {
				Article entity = new Article();
				entity.setCreatedTime(Calendar.getInstance());
				entity.setText(((ArticleResource) resource).text);
				entity.setID(resource.id);
				entity.setAttachments(((ArticleResource) resource).attachments.stream()
						.map(attResource -> attResource.getAttachmentFromResource()).collect(Collectors.toSet()));
				return entity;
			} else if (resource instanceof AttachmentResource) {
				Attachment entity = new Attachment();
				if (((AttachmentResource) resource).type == null)
					throw new RuntimeException("Attachment type isn't valid");
				entity.setID(resource.id);
				entity.setType(Attachment.AttachmentType.valueOf(((AttachmentResource) resource).type));
				entity.setAttachmentName(((AttachmentResource) resource).attachmentName);
				return entity;
			}
			return null;
		}

	};

	public static Resource getTemplate(String entity) {
		if (Article.class.getAnnotation(TemplateName.class).name().equalsIgnoreCase(entity))
			return new ArticleResource();
		else if (Attachment.class.getAnnotation(TemplateName.class).name().equalsIgnoreCase(entity))
			return new AttachmentResource();
		return null;
	}

	public void createEntityByTemplate(String entity) {
		if (Article.class.getAnnotation(TemplateName.class).name().equalsIgnoreCase(entity))
			new ArticleReference().doPost(restOperations, this);
	}

	public Resource(@JsonProperty("id") String id, @JsonProperty("links") List<ResourceLink> links) {
		this.id = id;
		this.links = links;
	}

	public Resource() {
		this.id = null;
	}

	public Resource(SubObject.Mapped entity) {
		this.id = entity.getID();
	}
}
