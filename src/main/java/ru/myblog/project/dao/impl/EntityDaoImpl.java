package ru.myblog.project.dao.impl;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.myblog.project.dao.EntityDao;
import ru.myblog.project.entities.SubObject;

@Repository
public class EntityDaoImpl implements EntityDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	@PostConstruct
	private void init() {
		try {
			session = sessionFactory.getCurrentSession();
		} catch (org.hibernate.HibernateException he) {
			session = sessionFactory.openSession();
			session.setFlushMode(FlushMode.ALWAYS);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public boolean saveEntity(SubObject obj) {
		if (Optional.ofNullable(session.save(obj)).isPresent()) {
			session.flush();
			return true;
		}
		return false;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void deleteEntity(SubObject obj) {
		session.delete(obj);
		session.flush();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void deleteEntity(String id, Class<?> clazz) {
		session.delete(id, clazz);
		session.flush();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void updateEntity(SubObject obj) {
		session.saveOrUpdate(obj);
		session.flush();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public SubObject.Mapped findEntity(String id, Class<?> clazz) {
		return (SubObject.Mapped) session.get(clazz, id);
	}
	
}
