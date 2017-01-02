package ru.myblog.project.operations;

import java.util.List;

import javax.validation.constraints.NotNull;

import ru.myblog.project.entities.Article;
import ru.myblog.project.entities.SubObject;
import ru.myblog.project.web.resources.Resource;

/**
 * Интерфейс для слоя REST для работы с сущностями DAO.
 * Реализация: {@linkplain RestOperationsImpl}.
 * 
 * @author Alimurad A. Ramazanov
 * @since 31.12.2016
 * @version 1.1
 */
public interface RestOperations {

	/**
	 * Операция создания из сущности rest-слоя сущности dao-слоя.
	 * <p>
	 * 
	 * @see {@linkplain Resource}
	 * @param obj
	 *            - rest-сущность для создания.
	 * @return {@code true}, если сущность успешно создана, {@code false} -
	 *         сущность не была создана.
	 */
	boolean create(@NotNull Resource obj);

	/**
	 * Операция удаления сущности из контекста по сущности rest-слоя.
	 * <p>
	 * 
	 * @see {@linkplain Resource}
	 * @param obj
	 *            - сущность для удаления.
	 */
	void delete(@NotNull Resource obj);

	/**
	 * Операция удаления сущности из контекста по идентификатору сущности из
	 * rest-слоя.
	 * <p>
	 * 
	 * @see {@linkplain Resource}
	 * @param id
	 *            - идентификатор сущности для удаления.
	 * @param resource
	 *            - rest-ресурс сущности.
	 */
	void delete(@NotNull String id, @NotNull Resource resource);

	/**
	 * Операция обновления сущности в РСУБД по сущности с rest-слоя.
	 * <p>
	 * 
	 * @see {@linkplain Resource}
	 * @param obj
	 *            - сущность для обновления.
	 */
	void update(@NotNull Resource obj);

	/**
	 * Операция получения rest-сущности по идентификатору.
	 * <p>
	 * 
	 * @see {@link Resource}
	 * @param id
	 *            идентификатор.
	 * @param clazz
	 *            - класс сущности.
	 * @return REST-ресурс сущности.
	 */
	Resource get(@NotNull String id, @NotNull Class<?> clazz);

	/**
	 * Операция получения всех сущностей доменного уровня типа
	 * {@linkplain Article} в виде REST-ресурса.
	 * <p>
	 * 
	 * @see {@link Resource}, {@link SubObject.Mapped}
	 * @return не может быть {@code null}.
	 */
	List<Resource> findAll();
}
