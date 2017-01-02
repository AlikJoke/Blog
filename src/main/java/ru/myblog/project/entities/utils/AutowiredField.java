package ru.myblog.project.entities.utils;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

@Documented
@Retention(RUNTIME)
public @interface AutowiredField {

	String value();
	
	Class<?> className();
}
