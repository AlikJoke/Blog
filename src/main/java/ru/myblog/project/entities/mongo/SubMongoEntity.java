package ru.myblog.project.entities.mongo;

import java.io.File;

import org.springframework.data.annotation.Id;

public abstract class SubMongoEntity {

	@Id
	private String id;

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
