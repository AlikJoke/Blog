package ru.myblog.project.web.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Класс для получения root url сервиса.
 * 
 * @author Alimurad A. Ramazanov
 * @since 02.01.2017
 * @version 1.0
 *
 */
public class ServletConfig {

	public static String getServerRootUrl() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
				.getContextPath();
	}
}
