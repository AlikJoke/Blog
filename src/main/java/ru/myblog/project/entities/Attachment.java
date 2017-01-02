package ru.myblog.project.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ru.myblog.project.dao.mongo.EntityMongoDao;
import ru.myblog.project.dao.mongo.EntityMongoDaoImpl;
import ru.myblog.project.entities.mongo.SubMongoEntity;
import ru.myblog.project.entities.utils.AutowiredField;
import ru.myblog.project.entities.utils.TemplateName;

@Entity
@javax.persistence.Table(name = "attachments")
@Table(appliesTo = "attachments", indexes = { @Index(name = "index_attachments_name", columnNames = "attachment_name"),
		@Index(name = "index_attachments_id", columnNames = "id"),
		@Index(name = "index_attachments_modified_time", columnNames = "modified_time") })
@TemplateName(name = "attachment")
public class Attachment extends SubObject.Mapped {

	private static final long serialVersionUID = 718963853579497734L;

	@Transient
	private SubMongoEntity file;

	public SubMongoEntity getFile() {
		return file;
	}

	public void setFile(SubMongoEntity file) {
		this.file = file;
	}

	@Column(name = "size", updatable = false, nullable = false)
	private Long size;

	public AttachmentType getType() {
		return type;
	}

	public void setType(AttachmentType type) {
		this.type = type;
	}

	@Column(name = "type", updatable = false, nullable = false)
	@Enumerated(EnumType.STRING)
	private AttachmentType type;

	@Column(name = "attachment_name", updatable = true, nullable = false)
	private String attachmentName;

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@Cascade({ org.hibernate.annotations.CascadeType.ALL })
	@JoinColumn(name = "article_id", nullable = false)
	@ForeignKey(name = "article_id")
	@NotFound(action = NotFoundAction.EXCEPTION)
	private Article article;

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	@PostPersist
	public void postPersist() throws NoSuchFieldException, SecurityException {
		if (this.file == null)
			return;
		this.file.setId(this.getID());

		mongoDao().saveFile(this.file);
	}

	private static EntityMongoDao mongoDao() {

		@Configurable
		@AutowiredField(value = "mongoDao", className = EntityMongoDao.class)
		class MongoEntityHolder {
			@Autowired
			public EntityMongoDao mongoDao;
		}

		return new MongoEntityHolder().mongoDao == null ? new EntityMongoDaoImpl() : new MongoEntityHolder().mongoDao;
	}

	/**
	 * Перечисление типов вложений (приложений).
	 * {@linkplain AttachmentType#MUSIC} - файл расширения .mp3, .mpeg, .mp4.
	 * {@linkplain AttachmentType#VIDEO} - видео-файлы.
	 * {@linkplain AttachmentType#PICTURE} - файл-картинка (.jpg, .jpeg, .png).
	 * {@linkplain AttachmentType#DOCUMENT} - файл-документ.
	 * 
	 * @author Alimurad A. Ramazanov
	 * @since 01.01.2017
	 */
	public enum AttachmentType {

		MUSIC,

		VIDEO,

		DOCUMENT,

		PICTURE;
	}
}
