package ru.myblog.project.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ru.myblog.project.operations.RestOperations;

/**
 * Абстрактный контроллер верхнего уровня для REST-сервиса с обработкой ошибок.
 * 
 * @author Alimurad A. Ramazanov
 * @since 02.01.2017
 * @version 1.0
 *
 */
@Controller
public abstract class ExceptionHandlerController {

	@Autowired
	RestOperations restOperations;

	public void doOptions(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Allow", "GET, POST, OPTIONS, DELETE, PUT, TRACE");
		if (request.getHeader("Origin") != null)
			response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, TRACE, DELETE");
	}

	private static String exceptionToString(Throwable e) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss:S");
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Exception: %s\r\n", e.getClass().getName()));
		sb.append(String.format("Message: %s\r\n", e.getMessage() == null ? "" : e.getMessage()));
		sb.append(String.format("Current date: %s\r\n", formatter.format(new Date())));

		final Writer stackTrace = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stackTrace);
		e.printStackTrace(printWriter);
		sb.append(String.format("Stack trace: %s\r\n", stackTrace.toString()));
		return sb.toString();
	}

	private static String requestToString(HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaderNames();

		Map<String, String> hh = new HashMap<String, String>();
		if (headers != null) {
			while (headers.hasMoreElements()) {
				String name = headers.nextElement();
				String value = request.getHeader(name);
				hh.put(name, value);

			}
		}
		return "URI=[" + request.getRequestURI() + "],queryString=[" + request.getQueryString() + "],headers=[" + hh
				+ "]";
	}

	private void writeErrorToResponse(HttpServletRequest request, HttpServletResponse response, HttpStatus status,
			Throwable ex) {
		StringBuilder sb = new StringBuilder();
		sb.append("*** Message: ").append(ex.getMessage()).append('\n');
		sb.append("*** Request: \n").append(requestToString(request)).append("\n\n");
		sb.append("*** Exception: \n").append(exceptionToString(ex)).append("\n\n");
		sb.append("***\n\n");
		response.setStatus(status.value());
		response.setContentType(MediaType.TEXT_PLAIN.toString());
		try {
			byte[] message_ = ex.getMessage().getBytes("UTF-8");
			response.setContentLength(message_.length);
			response.getOutputStream().write(message_);
			response.getOutputStream().flush();
		} catch (Exception e) {
			throw new IllegalStateException("Unable to write text response", e);
		}
	}

	@ExceptionHandler(value = { Exception.class, RuntimeException.class })
	public void handle(RuntimeException e, HttpServletResponse response, HttpServletRequest request) {
		writeErrorToResponse(request, response, HttpStatus.INTERNAL_SERVER_ERROR, e);
	}
}
