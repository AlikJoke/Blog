package ru.myblog.project.web.references;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import ru.myblog.project.entities.Attachment;
import ru.myblog.project.web.utils.UriHelper;

@Component("contentReference")
@Configurable
@EnableSpringConfigured
public class ContentReference implements Reference {

	public final static String PATH = "/content/{hash}";
	private final String hash;

	@Override
	public String getHref() {
		if (!StringUtils.hasLength(this.hash))
			return null;
		UriComponents uriComponents = UriComponentsBuilder.fromPath(PATH).build();
		return uriComponents.expand(this.hash).toString();
	}

	public ContentReference(URI uri) {
		UriHelper helper = new UriHelper(uri, PATH);
		this.hash = helper.getPathVariable("hash");
	}

	public ContentReference(Attachment attachment) {
		if (!StringUtils.hasLength(attachment.getID()))
			this.hash = "{hash}";
		else
			this.hash = attachment.getFile().getFileName();
	}

	public ContentReference() {
		this.hash = "_hash_";
	}

	public static String tempDirectory() {
		return System.getProperty("java.io.tmpdir") + "_tmp//";
	}

	public UploadedFileResource getUploadedFile(String name, MultipartFile file) {
		if (!StringUtils.hasLength(name) || file == null)
			throw new RuntimeException("File can't be null");

		try {
			byte[] bytes = file.getBytes();

			File dir = new File(tempDirectory());
			if (!dir.exists())
				dir.mkdirs();
			File serverFile = new File(dir.getAbsolutePath() + "//" + UUID.randomUUID().toString() + "@$"
					+ name.substring(name.lastIndexOf(".") + 1) + "@$" + name);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();
			return new UploadedFileResource(serverFile.getName().substring(0, serverFile.getName().lastIndexOf(".")));
		} catch (Exception e) {
			throw new RuntimeException("Exception while upload file with name " + name + " => " + e.getMessage());
		}
	}

	public File getUploadedFile(String hash) {
		if (!(StringUtils.hasLength(hash)))
			throw new RuntimeException("Hash can't be null");

		try {
			File dir = new File(tempDirectory());
			// Black magic
			File serverFile = new File(dir.getAbsolutePath() + "//" + hash + "."
					+ hash.split("@")[1].substring(hash.split("@")[1].indexOf("$") + 1));
			if (!serverFile.exists())
				throw new RuntimeException("File with requested hash doesn't exist");
			return serverFile;
		} catch (Exception e) {
			throw new RuntimeException("Exception while get uploaded file with hash " + hash + " => " + e.getMessage());
		}
	}

	public class UploadedFileResource implements Serializable {
		private String hash;

		public UploadedFileResource() {
		}

		public UploadedFileResource(String hash) {
			this.hash = hash;
		}

		public String getHash() {
			return hash;
		}

		public void setHash(String hash) {
			this.hash = hash;
		}

	}
}
