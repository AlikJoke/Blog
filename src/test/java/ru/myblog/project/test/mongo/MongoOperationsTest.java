package ru.myblog.project.test.mongo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import ru.myblog.project.dao.mongo.EntityMongoDao;
import ru.myblog.project.entities.mongo.SubMongoEntity;

@RunWith(MockitoJUnitRunner.class)
public class MongoOperationsTest {

	private SubMongoEntity entity;

	@Mock
	private EntityMongoDao dao;

	File file = new File("src/test/resources/testFile");

	@Before
	public void createMongoEntity() {
		entity = new SubMongoEntity();
		entity.setId(UUID.randomUUID().toString());
	}

	@Test
	public void saveEntity() throws IOException {
		Assert.assertNotNull(entity);
		FileInputStream input = new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
				IOUtils.toByteArray(input));
		Assert.assertNotNull(multipartFile);
		Assert.assertTrue(multipartFile.getSize() > 0);
		entity.setSize(file.length());
		entity.setFileName(file.getName());
		entity.setFile(file);
		dao.saveFile(entity);
	}

	@Test
	public void getEntity() {
		Assert.assertNotNull(entity.getId());
		dao.getFile(entity.getId(), entity.getClass());
	}

	@Test
	public void deleteEntity() {
		dao.deleteFile(entity.getId(), entity.getClass());
		Assert.assertNull(dao.getFile(entity.getId(), entity.getClass()));
	}
}
