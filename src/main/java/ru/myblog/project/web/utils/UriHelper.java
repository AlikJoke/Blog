package ru.myblog.project.web.utils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

public class UriHelper {
	private final Map<String, String> vars;
	private final MultiValueMap<String, String> queryParams;
	private final UriComponents uc;

	public UriHelper(URI uri, String template) {
		this(uri, new UriTemplate(template.startsWith("**") ? template.replace("**", "") : template));
	}

	public UriHelper(URI uri, UriTemplate template) {
		vars = template.match(uri.getPath());

		UriComponentsBuilder ucb = UriComponentsBuilder.fromUriString(uri.toString());
		uc = ucb.build();

		queryParams = new LinkedMultiValueMap<String, String>();
		if (uc.getQueryParams() != null && !uc.getQueryParams().isEmpty()) {
			for (Entry<String, List<String>> param : uc.getQueryParams().entrySet()) {
				List<String> vals = new ArrayList<String>();
				for (String val : param.getValue()) {
					try {
						vals.add(val == null ? null : URLDecoder.decode(val, "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						vals.add(val);
					}
				}
				try {
					queryParams.put(URLDecoder.decode(param.getKey(), "UTF-8"), vals);
				} catch (UnsupportedEncodingException e) {
					queryParams.put(param.getKey(), vals);
				}
			}
		}
	}

	public String getPathVariable(String name) {
		String result = vars.get(name);
		return result;
	}

	public String getParameter(String name) {
		String param = queryParams.getFirst(name);
		if (param == null && queryParams.containsKey(name)) {
			param = "";
		}
		return param;
	}

	public MultiValueMap<String, String> getQueryParameters() {
		return queryParams;
	}

}