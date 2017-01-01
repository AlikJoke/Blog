package ru.myblog.project.web.references;

import java.io.Serializable;

import ru.myblog.project.entities.SubObject;
import ru.myblog.project.operations.RestOperations;
import ru.myblog.project.web.resources.Resource;

public interface Reference extends Serializable {

	public static final String PATH = "/template/{entity}";
	String getHref();

	public default Resource doGet(RestOperations restOperations, String id) {
		return (Resource) restOperations.get(id, SubObject.Mapped.class);
	}
	
	public default void doPost(RestOperations restOperations, Resource resource) {
		restOperations.create(resource);
	}

	public default void doPut(RestOperations restOperations, Resource resource) {
		restOperations.update(resource);
	}
}
