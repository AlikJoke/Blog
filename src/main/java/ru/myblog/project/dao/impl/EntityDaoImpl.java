package ru.myblog.project.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.myblog.project.dao.EntityDao;
import ru.myblog.project.dao.mongo.EntityMongoDao;
import ru.myblog.project.entities.Article;
import ru.myblog.project.entities.Attachment;
import ru.myblog.project.entities.SubObject;
import ru.myblog.project.entities.configuration.Storage;
import ru.myblog.project.entities.mongo.SubMongoEntity;

/**
 * Реализация интерфейса {@link EntityDao}
 * 
 * @author Alimurad A. Ramazanov
 * @since 31.12.2016
 *
 */
@Repository
public class EntityDaoImpl implements EntityDao {

	@Autowired
	private EntityMongoDao mongoDao;

	@PersistenceContext(unitName = "persistenceCtx")
	private EntityManager em;

	@PostConstruct
	public void initialize() {
		Storage.setEntityManager(em);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public boolean saveEntity(SubObject obj) {
		em.persist(obj);
		em.flush();
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void deleteEntity(SubObject obj) {
		em.remove(obj);
		em.flush();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void deleteEntity(String id, Class<?> clazz) {
		em.remove(Storage.getEntityManager().find(clazz, id));
		em.flush();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void updateEntity(SubObject obj) {
		em.refresh(obj);
		em.flush();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public SubObject.Mapped findEntity(String id, Class<?> clazz) {
		SubObject.Mapped entity = (SubObject.Mapped) em.find(clazz, id);
		if (entity instanceof Attachment)
			((Attachment) entity).setFile(mongoDao.getFile(id, SubMongoEntity.class));
		else if (entity instanceof Article)
			((Article) entity).getAttachments().forEach(
					attachment -> attachment.setFile(mongoDao.getFile(attachment.getID(), SubMongoEntity.class)));
		return entity;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<? extends SubObject.Mapped> findAllEntities() {
		List<Article> articles = em.createNamedQuery("all.articles", Article.class).getResultList();
		articles.forEach(article -> article.getAttachments()
				.forEach(attachment -> attachment.setFile(mongoDao.getFile(attachment.getID(), SubMongoEntity.class))));
		return articles;
	}

}
