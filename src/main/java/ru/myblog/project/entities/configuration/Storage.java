package ru.myblog.project.entities.configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.beans.factory.annotation.Configurable;

public class Storage {

	@Configurable
	private static class EntityManagerHolder {
		@PersistenceContext(unitName = "persistenceCtx")
		EntityManager em;

		EntityManager getEntityManager() {
			if (null == em)
				throw new RuntimeException(
						"EntityManager is not injected yet. Check there is an active database transaction, and properly configured EntityManagerFactory.");
			return em;
		};

		public static final EntityManagerHolder INSTANCE = new EntityManagerHolder();
	}
	
	public static EntityManager getEntityManager() {
		return EntityManagerHolder.INSTANCE.getEntityManager();
	}
	
	public static void setEntityManager(EntityManager em) {
		EntityManagerHolder.INSTANCE.em = em;
	}
	
	public static <T> T unproxy(final T entity) {

		if (entity instanceof org.hibernate.proxy.HibernateProxy) {
            SessionImplementor initializerSession = 
                    ((org.hibernate.proxy.HibernateProxy)entity).getHibernateLazyInitializer().getSession(); 

            if (initializerSession == null || initializerSession.isClosed()){
                SessionImplementor currentSession = getEntityManager().unwrap(SessionImplementor.class);
                ((org.hibernate.proxy.HibernateProxy)entity).getHibernateLazyInitializer().setSession(currentSession);
            }

            if (!org.hibernate.Hibernate.isInitialized(entity)) org.hibernate.Hibernate.initialize(entity);
            @SuppressWarnings("unchecked")
            final T entity1 = (T) ((org.hibernate.proxy.HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
            return entity1;
		} else
			return entity;

	}
}
