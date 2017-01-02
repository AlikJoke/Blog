package ru.myblog.project.dao.mongo;

import ru.myblog.project.entities.mongo.SubMongoEntity;

/**
 * Интерфейс для работы с сущностями MongoDB.
 * Реализация: {@linkplain EntityMongoDaoImpl}
 * 
 * @author Alimurad A. Ramazanov
 * @since 31.12.2016
 * @version 1.0
 *
 */
public interface EntityMongoDao {

	/**
	 * Метод для сохранения файла в NoSQL-хранилище.
	 * <p>
	 * 
	 * @see {@link SubMongoEntity}
	 * @param file
	 *            - файл для сохранения.
	 */
	void saveFile(SubMongoEntity file);

	/**
	 * Метод для удаления файла из NoSQL-хранилища по идентификатору.
	 * <p>
	 * 
	 * @see {@link SubMongoEntity}
	 * @param id
	 *            - идентификатор сущности.
	 * @param clazz
	 *            - класс сущности.
	 */
	void deleteFile(String id, Class<?> clazz);

	/**
	 * Метод для поиска файла в NoSQL-хранилище по идентификатору.
	 * <p>
	 * 
	 * @see {@link SubMongoEntity}
	 * @param id
	 *            - идентификатор сущности.
	 * @param clazz
	 *            - класс сущности.
	 * @return файл, может быть {@code null}.
	 */
	<T> T getFile(String id, Class<?> clazz);
}
