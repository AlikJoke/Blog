package ru.myblog.project.entities.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Данной аннотацией помечается класс сущности доменного уровня для определения
 * по REST-запросу шаблона для создания REST-ресурса.
 * 
 * @author Alimurad A. Ramazanov
 * @since 01.01.2017
 * @version 1.0
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface TemplateName {

	/**
	 * Имя шаблона для создания сущности.
	 * 
	 * @return не может быть {@code null}.
	 */
	String name();
}
