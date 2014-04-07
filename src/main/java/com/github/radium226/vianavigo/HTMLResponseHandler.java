package com.github.radium226.vianavigo;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLResponseHandler implements ResponseHandler<Document> {

	@Override
	public Document handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		HttpEntity entity = response.getEntity();
		InputStream content = entity.getContent();
		try {
			Document document = Jsoup.parse(content, null, "caca");
			return document;
		} finally {
			IOUtil.closeQuietly(content);
		}
	}

}
