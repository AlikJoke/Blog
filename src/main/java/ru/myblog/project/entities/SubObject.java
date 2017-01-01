package ru.myblog.project.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

public interface SubObject extends Serializable {

	@NotNull
	String getID();

	@NotNull
	Calendar getModifiedTime();

	@MappedSuperclass
	@Access(AccessType.FIELD)
	abstract class Mapped implements SubObject {

		@Transient
		private String id;

		@Column(name = "modified_time", updatable = true, nullable = false)
		@Version
		private Calendar modifiedTime;

		@Override
		@Id
		@GeneratedValue(generator = "system-uuid")
		@GenericGenerator(name = "system-uuid", strategy = "uuid")
		@Column(name = "id", nullable = false, updatable = false, length = 64)
		@Access(AccessType.PROPERTY)
		public String getID() {
			return id;
		}

		public void setID(String id) {
			this.id = id;
		}

		@Override
		public Calendar getModifiedTime() {
			return modifiedTime;
		}

	}
}
