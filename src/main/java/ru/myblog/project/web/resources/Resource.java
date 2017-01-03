package ru.myblog.project.web.resources;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.annotation.ThreadSafe;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.StringUtils;

import ru.myblog.project.entities.Article;
import ru.myblog.project.entities.Attachment;
import ru.myblog.project.entities.SubObject;
import ru.myblog.project.entities.mongo.SubMongoEntity;
import ru.myblog.project.entities.utils.TemplateName;
import ru.myblog.project.web.references.ContentReference;
import ru.myblog.project.web.utils.ResourceLink;

@Configurable
@ThreadSafe
@JsonSerialize(include = Inclusion.NON_NULL)
public class Resource implements Serializable {

	private static final long serialVersionUID = 8193511648494865658L;

	public String id;
	public List<ResourceLink> links = new ArrayList<ResourceLink>();

	public static Function<Resource, SubObject.Mapped> resourceToEntity = new Function<Resource, SubObject.Mapped>() {

		@Override
		public SubObject.Mapped apply(Resource resource) {
			if (resource instanceof ArticleResource) {
				Article entity = new Article();
				entity.setCreatedTime(Calendar.getInstance());
				entity.setText(((ArticleResource) resource).text);
				entity.setID(resource.id);
				entity.setAttachments(((ArticleResource) resource).attachments.stream()
						.map(attResource -> attResource.attachmentFromResource()).collect(Collectors.toSet()));
				return entity;
			} else if (resource instanceof AttachmentResource) {
				AttachmentResource attachmentResource = (AttachmentResource) resource;
				Attachment entity = new Attachment();
				if (attachmentResource.type == null)
					throw new RuntimeException("Attachment type isn't valid");
				entity.setID(resource.id);
				entity.setType(Attachment.AttachmentType.valueOf(attachmentResource.type));
				entity.setAttachmentName(attachmentResource.attachmentName);
				SubMongoEntity mongoEntity = null;

				if (StringUtils.hasLength(attachmentResource.contentURL)
						&& !attachmentResource.contentURL.endsWith("_hash_")) {
					String hash = attachmentResource.contentURL
							.substring(attachmentResource.contentURL.lastIndexOf("/") + 1);
					File file = new File(ContentReference.tempDirectory() + "//" + hash + "."
							+ hash.split("@")[1].substring(hash.split("@")[1].indexOf("$") + 1));
					if (!file.exists())
						throw new IllegalArgumentException("Can't find file");
					mongoEntity = new SubMongoEntity(resource.id, file.length(), hash, file,
							FilenameUtils.getExtension(file.getName()));
					entity.setFile(mongoEntity);
				}
				return entity;
			}
			return null;
		}

	};

	public static Resource getTemplate(String entity) {
		if (Article.class.getAnnotation(TemplateName.class).name().equalsIgnoreCase(entity))
			return new ArticleResource(new Article());
		else if (Attachment.class.getAnnotation(TemplateName.class).name().equalsIgnoreCase(entity))
			return new AttachmentResource(new Attachment());
		return null;
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
