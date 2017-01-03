package ru.myblog.project.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.myblog.project.web.references.ArticleReference;
import ru.myblog.project.web.resources.Resource;

@Controller
public class ArticlesListController extends ExceptionHandlerController {

	@RequestMapping(value = "articles", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Resource> doGet(ArticleReference ref) {
		return ref.getAllArticles(restOperations);
	}
	
	@RequestMapping(value = "articles", method = RequestMethod.OPTIONS)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void doOptions(HttpServletRequest request, HttpServletResponse response) {
		super.doOptions(request, response);
	}
}
