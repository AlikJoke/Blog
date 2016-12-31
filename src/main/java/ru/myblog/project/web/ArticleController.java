package ru.myblog.project.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.myblog.project.web.references.ArticleReference;
import ru.myblog.project.web.resources.ArticleResource;

@Controller
public class ArticleController {

	@RequestMapping(value = ArticleReference.PATH, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ArticleResource doGet(String id, ArticleReference ref) {
		return ref.doGet();
	}
	
	@RequestMapping(value = ArticleReference.PATH, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void doPost(@RequestBody ArticleResource resource, ArticleReference ref) {
		ref.doPost(resource);
	}
	
	@RequestMapping(value = ArticleReference.PATH, method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void doPut(@RequestBody ArticleResource resource, ArticleReference ref) {
		ref.doPut(resource);
	}
	
	@RequestMapping(value = ArticleReference.PATH, method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void doOptions(HttpServletRequest request, HttpServletResponse response) {
		// TODO Options request
	}
}
