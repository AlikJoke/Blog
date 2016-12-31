package ru.myblog.project.entities.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "audios")
public class Video extends SubMongoEntity {

}
