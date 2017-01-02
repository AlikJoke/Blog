package ru.myblog.project.dao.mongo;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import ru.myblog.project.configuration.mongo.MongoDBConfig;
import ru.myblog.project.entities.mongo.SubMongoEntity;

/**
 * Реализация интерфейса {@link EntityMongoDao}
 * 
 * @author Alimurad A. Ramazanov
 * @since 31.12.2016
 */
@Repository
public class EntityMongoDaoImpl implements EntityMongoDao {

	ApplicationContext ctx = null;
	MongoOperations mongoOperation = null;

	@PostConstruct
	private void initializeAppContext() {
		ctx = new AnnotationConfigApplicationContext(MongoDBConfig.class);
		mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
	}

	@Override
	public void saveFile(SubMongoEntity mongoEntity) {
		mongoOperation.save(mongoEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getFile(String id, Class<?> clazz) {
		BasicDBObject query = new BasicDBObject();
		query.put("id", id);
		MongoDBConfig config = new MongoDBConfig();
		DB db = null;
		try {
			db = config.mongo().getDB(config.getDatabaseName());
			DBCollection collection = db.getCollection(clazz.getAnnotation(Document.class).collection());
			return (T) collection.findOne(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteFile(String id, Class<?> clazz) {
		BasicDBObject query = new BasicDBObject();
		query.put("id", id);
		MongoDBConfig config = new MongoDBConfig();
		DB db = null;
		try {
			db = config.mongo().getDB(config.getDatabaseName());
			DBCollection collection = db.getCollection(clazz.getAnnotation(Document.class).collection());
			collection.findAndRemove(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
