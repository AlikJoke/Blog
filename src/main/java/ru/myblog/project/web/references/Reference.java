package ru.myblog.project.web.references;

import java.io.Serializable;

import ru.myblog.project.entities.SubObject;
import ru.myblog.project.operations.RestOperations;
import ru.myblog.project.web.resources.ArticleResource;
import ru.myblog.project.web.resources.AttachmentResource;
import ru.myblog.project.web.resources.Resource;

/**
 * Интерфейс для работы на уровне контроллеров REST-сервиса. Имеет
 * {@code default} методы для GET, POST, PUT запросов для
 * {@link ArticleResource}, {@link AttachmentResource}.
 * 
 * @author Alimurad A. Ramazanov
 * @since 01.01.2017
 * @version 1.0
 *
 */
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
