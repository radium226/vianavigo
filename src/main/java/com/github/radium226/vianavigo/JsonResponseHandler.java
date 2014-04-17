package com.github.radium226.vianavigo;

import java.io.IOException;

import javax.json.JsonStructure;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.github.radium226.util.JsonUtil;

public class JsonResponseHandler implements ResponseHandler<JsonStructure> {

	public JsonResponseHandler() {
		super();
	}
	
	@Override
	public JsonStructure handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		HttpEntity entity = response.getEntity();
		JsonStructure jsonStructure = JsonUtil.parseHttpEntity(entity);
		return jsonStructure;
	}

}
