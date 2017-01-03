package ru.myblog.project.web.utils;

import com.google.common.net.MediaType;

import ru.myblog.project.dao.mongo.EntityMongoDaoImpl;
import ru.myblog.project.entities.mongo.SubMongoEntity;

/**
 * Класс-helper для работы с content-type приложений. P.S. Не красивая простыня
 * if-else if.
 * 
 * @author Alimurad A. Ramazanov
 * @version 1.0
 * @since 02.01.2017
 *
 */
public class MIMEHelper {

	public static final String CONTENT_TYPE = "Content-Type";

	public static String getMIMEType(String extension) {
		if ("pdf".equalsIgnoreCase(extension))
			return MediaType.PDF.toString();
		else if ("txt".equalsIgnoreCase(extension))
			return MediaType.PLAIN_TEXT_UTF_8.toString();
		else if ("doc".equalsIgnoreCase(extension) || "docx".equalsIgnoreCase(extension))
			return MediaType.MICROSOFT_WORD.toString();
		else if ("rtf".equalsIgnoreCase(extension))
			return MediaType.RTF_UTF_8.toString();
		else if ("odt".equalsIgnoreCase(extension))
			return MediaType.OPENDOCUMENT_TEXT.toString();
		else if ("jpeg".equalsIgnoreCase(extension) || "jpg".equalsIgnoreCase(extension))
			return MediaType.JPEG.toString();
		else if ("png".equalsIgnoreCase(extension))
			return MediaType.PNG.toString();
		else if ("mp3".equalsIgnoreCase(extension))
			return MediaType.ANY_AUDIO_TYPE.toString();
		else if ("mp4".equalsIgnoreCase(extension))
			return MediaType.MP4_AUDIO.toString();
		else
			return getMIMEType(new EntityMongoDaoImpl().getFile(extension, SubMongoEntity.class).getExtension());
	}
}
