package ru.myblog.project.dao;

import java.util.List;

import ru.myblog.project.entities.SubObject;

/**
 * Интерфейс для слоя DAO обращений к РСУБД.
 * 
 * @author Alimurad A. Ramazanov
 * @since 31.12.2016
 * @version 1.0
 */
public interface EntityDao {

	/**
	 * Операция сохранения сущности в контекст.
	 * <p>
	 * 
	 * @see {@linkplain SubObject.Mapped}
	 * @param obj
	 *            - сущность для сохранения.
	 * @return {@code true}, если сущность успешно соблюдена, {@code false} -
	 *         сущность не была сохранена.
	 */
	boolean saveEntity(SubObject obj);

	/**
	 * Операция удаления сущности из контекста.
	 * <p>
	 * 
	 * @see {@linkplain SubObject.Mapped}
	 * @param obj
	 *            - сущность для удаления.
	 */
	void deleteEntity(SubObject obj);

	/**
	 * Операция удаления сущности из контекста.
	 * <p>
	 * 
	 * @see {@linkplain SubObject.Mapped}
	 * @param id
	 *            - идентификатор сущности для удаления.
	 * @param clazz
	 *            - класс сущности, идентификатор которой передан для удаления
	 */
	void deleteEntity(String id, Class<?> clazz);

	/**
	 * Операция обновления сущности в РСУБД.
	 * <p>
	 * 
	 * @see {@linkplain SubObject.Mapped}
	 * @param obj
	 *            - сущность для обновления.
	 */
	void updateEntity(SubObject obj);

	/**
	 * Метод для поиска сущности по ключу в контексте.
	 * <p>
	 * 
	 * @see {@linkplain SubObject.Mapped}
	 * @param id
	 *            - идентификатор сущности.
	 * @param clazz
	 *            - класс сущности.
	 * @return экземпляр {@linkplain SubObject}, может быть {@code null}, если
	 *         сущность не найдена в контексте.
	 */
	SubObject.Mapped findEntity(String id, Class<?> clazz);
	
	List<? extends SubObject.Mapped> findAllEntities();
}
