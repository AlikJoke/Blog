package ru.myblog.project.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import ru.myblog.project.dao.EntityDao;
import ru.myblog.project.entities.SubObject;
import ru.myblog.project.web.resources.Resource;

@Service("restOperationsImpl")
@Configurable
public class RestOperationsImpl implements RestOperations {

	@Autowired
	private EntityDao entityDao;

	@Override
	public boolean create(Resource obj) {
		SubObject.Mapped entity = Resource.resourceToEntity.apply(obj);
		return entityDao.saveEntity(entity);
	}

	@Override
	public void delete(Resource obj) {
		SubObject.Mapped entity = Resource.resourceToEntity.apply(obj);
		entityDao.deleteEntity(entity);
	}

	@Override
	public void delete(String id, Resource resource) {
		SubObject.Mapped entity = Resource.resourceToEntity.apply(resource);
		entityDao.deleteEntity(resource.id, entity.getClass());
	}

	@Override
	public void update(Resource obj) {
		SubObject.Mapped entity = Resource.resourceToEntity.apply(obj);
		entityDao.updateEntity(entity);
	}

	@Override
	public Resource get(String id, Class<?> clazz) {
		return new Resource(entityDao.findEntity(id, clazz));
	}

}
