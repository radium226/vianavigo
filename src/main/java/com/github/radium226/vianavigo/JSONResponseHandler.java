package com.github.radium226.vianavigo;

import java.io.IOException;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class JSONResponseHandler implements ResponseHandler<JSONObject> {

	public JSONResponseHandler() {
		super();
	}
	
	@Override
	public JSONObject handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity, Consts.UTF_8);
		JSONObject jsonObject = new JSONObject(content);
		return jsonObject;
	}

}
