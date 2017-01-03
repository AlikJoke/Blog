package ru.myblog.project.web.utils;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import ru.myblog.project.web.references.Reference;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class ResourceLink implements Serializable {

	private static final long serialVersionUID = -8366737731655354734L;
	
	private final String rel;

	public String getRel() {
		return this.rel;
	}

	protected final String href;

	public String getHref() {
		return this.href;
	}
	
	@JsonIgnore
	public URI getUri() {
		try {
			if (this.href != null)
				return new URI(this.href);
			else
				return null;
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public ResourceLink(Reference ref) {
		this("self", ref.getHref());
	}

	public ResourceLink(String rel, Reference reference) {
		this(rel, reference.getHref());
	}

	@JsonCreator
	public ResourceLink(@JsonProperty("rel") String rel, @JsonProperty("href") String href) {
		super();
		this.rel = rel;
		this.href = ServletConfig.getServerRootUrl() + href;
	}

	public static String rel(String relation) {
		return relation;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ResourceLink that = (ResourceLink) o;

		if (!href.equals(that.href))
			return false;
		if (rel != null ? !rel.equals(that.rel) : that.rel != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = rel != null ? rel.hashCode() : 0;
		result = 31 * result + href.hashCode();
		return result;
	}

}
