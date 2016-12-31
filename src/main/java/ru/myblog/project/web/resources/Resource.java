package ru.myblog.project.web.resources;

import java.util.Calendar;
import java.util.function.Function;

import org.codehaus.jackson.annotate.JsonProperty;

import ru.myblog.project.entities.Article;
import ru.myblog.project.entities.Audio;
import ru.myblog.project.entities.SubObject;

public class Resource {

	public String id;

	public static Function<Resource, SubObject.Mapped> resourceToEntity = new Function<Resource, SubObject.Mapped>() {

		@Override
		public SubObject.Mapped apply(Resource resource) {
			if (resource instanceof ArticleResource) {
				Article entity = new Article();
				entity.setCreatedTime(Calendar.getInstance());
				entity.setText(((ArticleResource) resource).text);
				return entity;
			} else if (resource instanceof AudioResource || resource instanceof VideoResource) {
				Audio entity = new Audio();

				return entity;
			}
			return null;
		}

	};
	
	public Resource(@JsonProperty("id") String id) {
		this.id = id;
	}
	
	public Resource(SubObject.Mapped entity) {
		this.id = entity.getID();
	}
}
