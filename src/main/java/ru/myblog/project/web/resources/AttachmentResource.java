package ru.myblog.project.web.resources;

import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.stereotype.Component;

import ru.myblog.project.entities.Attachment;
import ru.myblog.project.web.references.AttachmentReference;
import ru.myblog.project.web.utils.ResourceLink;

public class AttachmentResource extends Resource {

	public String attachmentName;
	public Long size;
	public String type;

	public AttachmentResource(Attachment entity) {
		super(entity);
		this.attachmentName = entity.getAttachmentName();
		this.size = entity.getSize();
		this.type = entity.getType().name();
		addLinks(entity);
	}

	@JsonCreator
	public AttachmentResource(@JsonProperty("id") String id, @JsonProperty("links") List<ResourceLink> links,
			@JsonProperty("attachmentName") String attachmentName, @JsonProperty("size") Long size,
			@JsonProperty("type") String type) {
		super(id, links);
		this.attachmentName = attachmentName;
		this.size = size;
		this.type = type;
	}

	public AttachmentResource() {
		super();
		this.attachmentName = null;
		this.size = null;
		this.type = null;
	}

	void addLinks(Attachment entity) {
		this.links.add(new ResourceLink("self", new AttachmentReference(entity).getHref()));
		this.links.add(new ResourceLink("save", new AttachmentReference(entity).getHref()));
	}

	public Attachment getAttachmentFromResource() {
		Attachment attachment = new Attachment();
		attachment.setID(this.id);
		attachment.setSize(this.size);
		attachment.setAttachmentName(this.attachmentName);
		attachment.setType(Attachment.AttachmentType.valueOf(this.type));
		return attachment;
	}
}
