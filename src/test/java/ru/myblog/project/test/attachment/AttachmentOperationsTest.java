package ru.myblog.project.test.attachment;

import java.util.Calendar;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ru.myblog.project.dao.EntityDao;
import ru.myblog.project.entities.Article;
import ru.myblog.project.entities.Attachment;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentOperationsTest extends Assert {

	private Attachment attachment;
	
	@SuppressWarnings("deprecation")
	@Mock
	private EntityDao entityDao;
	
	@Before
	public void createAttachment() {
		attachment = new Attachment();
		attachment.setID(UUID.randomUUID().toString());
		attachment.setAttachmentName("test");
		attachment.setArticle(null);
	}
	
	@Test
	public void saveAttachment() {
		entityDao.saveEntity(attachment);
	}
	
	@Test
	public void updateAttachment() {
		attachment.setAttachmentName("TTTT");
		entityDao.updateEntity(attachment);
	}
	
	@Test
	public void getAttachment() {
		Assert.assertNotNull(this.attachment);
		this.attachment.setID("0");
		entityDao.saveEntity(attachment);
	}
	
	@After
	public void deleteArticle() {
		entityDao.deleteEntity(attachment);
	}
}
