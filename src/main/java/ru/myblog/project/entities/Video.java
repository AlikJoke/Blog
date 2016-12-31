package ru.myblog.project.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@javax.persistence.Table(name = "videos")
@Table(appliesTo = "videos", indexes = { @Index(name = "index_videos_name", columnNames = "video_name"),
		@Index(name = "index_videos_id", columnNames = "id"),
		@Index(name = "index_videos_modified_time", columnNames = "modified_time") })
public class Video extends SubObject.Mapped {

	@Column(name = "size", updatable = false, nullable = false)
	private Long size;

	@Column(name = "video_name", updatable = true, nullable = false)
	private String videoName;

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

}