package ru.myblog.project.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@javax.persistence.Table(name = "audios")
@Table(appliesTo = "audios", indexes = { @Index(name = "index_audios_name", columnNames = "audio_name"),
		@Index(name = "index_audios_id", columnNames = "id"),
		@Index(name = "index_audios_modified_time", columnNames = "modified_time") })
public class Audio extends SubObject.Mapped {

	@Column(name = "size", updatable = false, nullable = false)
	private Long size;

	@Column(name = "audio_name", updatable = true, nullable = false)
	private String audioName;

	public String getAudioName() {
		return audioName;
	}

	public void setAudioName(String audioName) {
		this.audioName = audioName;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

}
