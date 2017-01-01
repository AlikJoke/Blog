package ru.myblog.project.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.myblog.project.web.references.Reference;
import ru.myblog.project.web.resources.Resource;

@Controller
public class TemplateController {

	@RequestMapping(value = Reference.PATH, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Resource getTemplate(@PathVariable("entity") String entity) {
		return Resource.getTemplate(entity);
	}

	@RequestMapping(value = Reference.PATH, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void createEntityByTemplate(@PathVariable("entity") String entity, @RequestBody Resource resource) {
		resource.createEntityByTemplate(entity);
	}
}
