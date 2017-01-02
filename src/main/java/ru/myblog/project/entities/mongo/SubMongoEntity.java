package ru.myblog.project.entities.mongo;

import java.io.File;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "files")
public class SubMongoEntity {

	@Id
	private String id;
	private Long size;
	private String fileName;

	public SubMongoEntity() {

	}

	public SubMongoEntity(String id, Long size, String fileName, File file) {
		this.id = id;
		this.size = size;
		this.fileName = fileName;
		this.file = file;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
