package com.github.radium226.vianavigo;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

public class HTMLResponseHandler implements ResponseHandler<Document> {

	@Override
	public Document handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		HttpEntity entity = response.getEntity();
		InputStream content = entity.getContent();
		try {
			Document document = null; 
			Tidy tidy = new Tidy();
			document = tidy.parseDOM(content, null);
			return document;
		} finally {
			IOUtil.closeQuietly(content);
		}
	}

}
