package ru.myblog.project.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Table;

import ru.myblog.project.entities.utils.TemplateName;

@Entity
@javax.persistence.Table(name = "attachments")
@Table(appliesTo = "attachments", indexes = { @Index(name = "index_attachments_name", columnNames = "attachment_name"),
		@Index(name = "index_attachments_id", columnNames = "id"),
		@Index(name = "index_attachments_modified_time", columnNames = "modified_time") })
@TemplateName(name = "attachment")
public class Attachment extends SubObject.Mapped {

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

	public enum AttachmentType {

		MUSIC,

		VIDEO,

		PICTURE;
	}
}
