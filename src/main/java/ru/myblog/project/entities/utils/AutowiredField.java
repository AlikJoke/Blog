package ru.myblog.project.entities.utils;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

/**
 * Аннотация для класса, у которого есть аннотированное аннотацией
 * {@code @Autowired} поле. Нужно при использовании Reflection API.
 * 
 * @author Alimurad A. Ramazanov
 * @since 02.01.2017
 * @version 1.0
 */
@Documented
@Retention(RUNTIME)
public @interface AutowiredField {

	/**
	 * Имя поля, которое должно быть injected.
	 * <p>
	 * 
	 * @return имя поля, не может быть {@code null}.
	 */
	String value();

	/**
	 * Класс, типа которого инжектируемое поле.
	 * <p>
	 * 
	 * @return указанный класс, если он указан в аннотации, либо {@link Object},
	 *         если не указан.
	 */
	Class<?> className() default Object.class;
}
