package ru.myblog.project.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ru.myblog.project.web.references.ContentReference;
import ru.myblog.project.web.references.ContentReference.UploadedFileResource;
import ru.myblog.project.web.utils.MIMEHelper;

@Controller
public class UploadFileController {

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody UploadedFileResource uploadFileHandler(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file, ContentReference ref) {
		return ref.getUploadedFile(name, file);
	}

	@RequestMapping(value = ContentReference.PATH, method = RequestMethod.GET, produces = { "text/plain" })
	public ResponseEntity<?> getContent(@PathVariable("hash") String hash, ContentReference ref,
			HttpServletResponse response) throws IOException {
		File file = ref.getUploadedFile(hash);
		HttpHeaders headers = new HttpHeaders();
		headers.add(MIMEHelper.CONTENT_TYPE, MIMEHelper.getMIMEType(FilenameUtils.getExtension(file.getName())));
		return new ResponseEntity<InputStreamResource>(new InputStreamResource(new FileInputStream(file)), headers,
				HttpStatus.OK);
	}
}
