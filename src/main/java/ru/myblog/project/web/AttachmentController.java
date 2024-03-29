package ru.myblog.project.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.myblog.project.web.references.AttachmentReference;
import ru.myblog.project.web.resources.AttachmentResource;

@Controller
public class AttachmentController extends ExceptionHandlerController {

	@RequestMapping(value = AttachmentReference.PATH, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody AttachmentResource doGet(@PathVariable("id") String id, AttachmentReference ref) {
		return (AttachmentResource) ref.doGet(restOperations, id);
	}

	@RequestMapping(value = AttachmentReference.PATH, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void doPost(@RequestBody AttachmentResource resource, AttachmentReference ref) {
		ref.doPost(restOperations, resource);
	}

	@RequestMapping(value = AttachmentReference.PATH, method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void doPut(@RequestBody AttachmentResource resource, AttachmentReference ref) {
		ref.doPut(restOperations, resource);
	}

	@RequestMapping(value = AttachmentReference.PATH, method = RequestMethod.OPTIONS)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void doOptions(HttpServletRequest request, HttpServletResponse response) {
		super.doOptions(request, response);
	}
}
