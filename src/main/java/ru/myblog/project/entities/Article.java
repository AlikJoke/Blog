package ru.myblog.project.entities;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Table;

import ru.myblog.project.entities.utils.TemplateName;

@Entity
@javax.persistence.Table(name = "articles")
@Table(appliesTo = "articles", indexes = { @Index(name = "index_articles_created_time", columnNames = "created_time"),
		@Index(name = "index_articles_id", columnNames = "id"),
		@Index(name = "index_articles_modified_time", columnNames = "modified_time") })
@TemplateName(name = "article")
public class Article extends SubObject.Mapped {

	@Column(name = "text", length = 2048, nullable = true)
	private String text;
	
	@OneToMany(targetEntity = Attachment.class, mappedBy = "article")
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<Attachment> attachments;

	public Set<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}

	@Column(name = "created_time", updatable = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdTime;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

}
