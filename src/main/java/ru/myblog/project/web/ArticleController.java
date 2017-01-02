package ru.myblog.project.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.myblog.project.operations.RestOperations;
import ru.myblog.project.web.references.ArticleReference;
import ru.myblog.project.web.references.Reference;
import ru.myblog.project.web.resources.ArticleResource;

@Controller
public class ArticleController {

	@Autowired
	private RestOperations restOperations;
	
	@RequestMapping(value = ArticleReference.PATH, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ArticleResource doGet(@PathVariable("id") String id, ArticleReference ref) {
		return (ArticleResource) ref.doGet(restOperations, id);
	}
	
	@RequestMapping(value = ArticleReference.PATH, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void doPost(@RequestBody ArticleResource resource, ArticleReference ref) {
		ref.doPost(restOperations, resource);
	}
	
	@RequestMapping(value = ArticleReference.PATH, method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void doPut(@RequestBody ArticleResource resource, ArticleReference ref) {
		ref.doPut(restOperations, resource);
	}
	
	@RequestMapping(value = ArticleReference.PATH, method = RequestMethod.OPTIONS)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void doOptions(HttpServletRequest request, HttpServletResponse response) {
		// TODO Options request
	}
}
