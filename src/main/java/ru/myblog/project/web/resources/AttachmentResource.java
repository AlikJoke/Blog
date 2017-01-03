package ru.myblog.project.web.resources;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.annotation.ThreadSafe;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.util.StringUtils;

import ru.myblog.project.entities.Attachment;
import ru.myblog.project.entities.mongo.SubMongoEntity;
import ru.myblog.project.web.references.AttachmentReference;
import ru.myblog.project.web.references.ContentReference;
import ru.myblog.project.web.utils.ResourceLink;

@ThreadSafe
@JsonSerialize(include = Inclusion.NON_NULL)
public class AttachmentResource extends Resource {

	private static final long serialVersionUID = 3298451764764471150L;

	public String attachmentName;
	public Long size;
	public String type;
	public String contentURL;

	public AttachmentResource(Attachment entity) {
		super(entity);
		this.attachmentName = entity.getAttachmentName();
		this.size = entity.getSize();
		this.type = Optional.ofNullable(entity.getType()).isPresent() ? entity.getType().name() : null;
		this.contentURL = Optional.ofNullable(entity.getFile()).isPresent() ? new ContentReference(entity).getHref()
				: null;
		addLinks(entity);
	}

	@JsonCreator
	public AttachmentResource(@JsonProperty("id") String id, @JsonProperty("attachmentName") String attachmentName,
			@JsonProperty("size") Long size, @JsonProperty("type") String type,
			@JsonProperty("contentURL") String contentURL, @JsonProperty("links") List<ResourceLink> links) {
		super(id, links);
		this.attachmentName = attachmentName;
		this.size = size;
		this.contentURL = contentURL;
		this.type = type;
	}

	public AttachmentResource() {
		super();
		this.attachmentName = null;
		this.contentURL = null;
		this.size = new Long(0);
		this.type = null;
	}

	void addLinks(Attachment entity) {
		this.links.add(new ResourceLink(new AttachmentReference(entity)));
		this.links.add(new ResourceLink("save", new AttachmentReference(entity).getHref()));
	}

	@JsonIgnore
	public Attachment attachmentFromResource() {
		Attachment attachment = new Attachment();
		attachment.setID(this.id);
		attachment.setAttachmentName(this.attachmentName);
		attachment.setType(Attachment.AttachmentType.valueOf(this.type));
		if (StringUtils.hasLength(this.contentURL) && !contentURL.endsWith("_hash_")) {
			String hash = this.contentURL.substring(this.contentURL.lastIndexOf("/") + 1);
			File file = new File(ContentReference.tempDirectory() + "//" + hash + "."
					+ hash.split("@")[1].substring(hash.split("@")[1].indexOf("$") + 1));
			if (!file.exists())
				throw new IllegalArgumentException("Can't find file");
			attachment.setSize(file.length());
			attachment.setFile(new SubMongoEntity(this.id, file.length(), hash.substring(hash.lastIndexOf("$") + 1),
					file, FilenameUtils.getExtension(file.getName())));

		}
		return attachment;
	}
}
