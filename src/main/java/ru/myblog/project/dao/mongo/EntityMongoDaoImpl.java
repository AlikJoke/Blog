package ru.myblog.project.dao.mongo;

import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

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

	ApplicationContext ctx;
	MongoOperations mongoOperation;
	MongoDBConfig config;

	@PostConstruct
	private void initializeAppContext() {
		ctx = new AnnotationConfigApplicationContext(MongoDBConfig.class);
		mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		config = new MongoDBConfig();
	}

	public EntityMongoDaoImpl() {
		initializeAppContext();
	}

	@Override
	public void saveFile(SubMongoEntity mongoEntity) {
		try {
			GridFS gfs = new GridFS(config.mongo().getDB(config.getDatabaseName()), "files");
			GridFSInputFile gfsFile = gfs.createFile(mongoEntity.getFile());
			gfsFile.setFilename(mongoEntity.getId());
			gfsFile.save();
		} catch (Exception e1) {
			throw new RuntimeException("Error while save file in mongo database");
		}
		mongoEntity.setFile(null); // java.io.File not implements Serializable
		mongoOperation.getCollection(mongoEntity.getClass().getAnnotation(Document.class).collection())
				.insert(mongoEntity.convertTo());
	}

	@Override
	public SubMongoEntity getFile(String id, Class<?> clazz) {
		BasicDBObject query = new BasicDBObject();
		query.put("id", id);
		DB db = null;
		try {
			db = config.mongo().getDB(config.getDatabaseName());
			DBCollection collection = db.getCollection(clazz.getAnnotation(Document.class).collection());
			GridFS gfs = new GridFS(config.mongo().getDB(config.getDatabaseName()), "files");
			GridFSDBFile gfsFile = gfs.findOne(id);
			if (gfsFile == null)
				return null;
			SubMongoEntity mongoEntity = SubMongoEntity.convertFrom(collection.findOne(query));
			File file = new File(System.getProperty("java.io.tmpdir") + id);
			FileUtils.copyInputStreamToFile(gfsFile.getInputStream(), file);
			mongoEntity.setFile(file);
			return mongoEntity;
		} catch (Exception e) {
			throw new RuntimeException("Error while get file from mongo database");
		}
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
