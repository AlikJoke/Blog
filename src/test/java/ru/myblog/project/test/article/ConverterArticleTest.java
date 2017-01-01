package ru.myblog.project.test.article;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import junit.framework.Assert;
import ru.myblog.project.entities.Article;
import ru.myblog.project.entities.Attachment;
import ru.myblog.project.web.resources.ArticleResource;
import ru.myblog.project.web.resources.AttachmentResource;

@RunWith(MockitoJUnitRunner.class)
public class ConverterArticleTest {

	ArticleResource resource = null;

	@Before
	public void createResource() {
		resource = new ArticleResource();
		Assert.assertNotNull(resource);
		resource.id = UUID.randomUUID().toString();
		resource.text = "fffd";
		AttachmentResource attachmentResource = new AttachmentResource();
		Assert.assertNotNull(attachmentResource);
		Assert.assertNotSame(resource.id, attachmentResource.id);
		attachmentResource.attachmentName = "testAtt";
		attachmentResource.type = "MUSIC";
		attachmentResource.id = UUID.randomUUID().toString();
		resource.attachments = Arrays.asList(attachmentResource);
	}

	@Test
	public void convertAttachments() {
		Assert.assertNotNull(this.resource.attachments);
		Assert.assertTrue(!resource.attachments.isEmpty());
		Attachment attachment = (Attachment) AttachmentResource.resourceToEntity
				.apply(this.resource.attachments.get(0));
		Assert.assertNotNull(attachment);
		Assert.assertNotNull(attachment.getID());
		Assert.assertNotNull(attachment.getAttachmentName());
		Assert.assertSame(attachment.getID(), resource.attachments.get(0).id);
	}

	@Test
	public void convertArticle() {
		Assert.assertNotNull(this.resource.createdDate);
		Assert.assertNotNull(resource.id);
		Assert.assertNotNull(this.resource.attachments);
		Assert.assertTrue(!resource.attachments.isEmpty());
		Article article = (Article) ArticleResource.resourceToEntity.apply(this.resource);
		Assert.assertNotNull(article);
		Assert.assertNotNull(article.getID());
		Assert.assertNotNull(article.getText());
		Assert.assertSame(article.getID(), resource.id);
		Assert.assertTrue(article.getAttachments().size() == resource.attachments.size());
	}

}
