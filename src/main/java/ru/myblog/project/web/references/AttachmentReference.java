package ru.myblog.project.web.references;

import java.net.URI;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import ru.myblog.project.entities.Attachment;
import ru.myblog.project.web.utils.UriHelper;

@Service("attachmentReference")
@Configurable
@EnableSpringConfigured
public class AttachmentReference implements Reference {

	public final static String PATH = "/attachment/{id}";
	private final String id;
	
	@Override
	public String getHref() {
		UriComponents uriComponents = UriComponentsBuilder.fromPath(PATH).queryParam(this.id).build();
		return uriComponents.expand(this.id).encode().toString();
	}
	
	public AttachmentReference(URI uri) {
		UriHelper helper = new UriHelper(uri, PATH);
		this.id = helper.getPathVariable("id");
	}

	public AttachmentReference(Attachment attachment) {
		this.id = attachment.getID();
	}

	public AttachmentReference() {
		this.id = null;
	}
}
