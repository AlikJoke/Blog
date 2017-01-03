package ru.myblog.project.entities.mongo;

import java.io.File;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

@Document(collection = "files")
public class SubMongoEntity {

	@Id
	@Field(value = "id")
	private String id;

	@Field(value = "size")
	private Long size;

	@Field(value = "extension")
	private String extension;
	
	@Field(value = "fileName")
	private String fileName;

	public SubMongoEntity() {
	}

	public SubMongoEntity(String id, Long size, String fileName, File file, String extension) {
		this.id = id;
		this.size = size;
		this.fileName = fileName;
		this.file = file;
		this.extension = extension;
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

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Field(value = "file")
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

	public DBObject convertTo() {
		BasicDBObjectBuilder documentBuilder = BasicDBObjectBuilder.start();
		java.lang.reflect.Field[] fields = this.getClass().getDeclaredFields();
		Lists.newArrayList(fields).stream().filter(field -> Optional.of(field.getAnnotation(Field.class)).isPresent())
				.forEach(field -> {
					try {
						documentBuilder.add(field.getAnnotation(Field.class).value(), field.get(this));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				});

		return documentBuilder.get();
	}

	public static SubMongoEntity convertFrom(DBObject object) {
		SubMongoEntity entity = new SubMongoEntity();
		java.lang.reflect.Field[] fields = SubMongoEntity.class.getDeclaredFields();
		Lists.newArrayList(fields).stream()
				.filter(field -> object.containsField(field.getAnnotation(Field.class).value())).forEach(field -> {
					try {
						field.set(entity, object.get(field.getAnnotation(Field.class).value()));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				});
		return entity;
	}
}
