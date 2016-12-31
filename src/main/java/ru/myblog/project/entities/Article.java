package ru.myblog.project.entities;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@javax.persistence.Table(name = "articles")
@Table(appliesTo = "articles", indexes = { @Index(name = "index_articles_created_time", columnNames = "created_time"),
		@Index(name = "index_articles_id", columnNames = "id"),
		@Index(name = "index_articles_modified_time", columnNames = "modified_time") })
public class Article extends SubObject.Mapped {

	@Column(name = "text", length = 2048, nullable = true)
	private String text;

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
