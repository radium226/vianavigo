package radium.vianavigo.http;

import static radium.json.Json.json;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Charsets;

public class JsonResponseHandler implements ResponseHandler<JsonNode> {

	public JsonResponseHandler() {
		super();
	}
	
	@Override
	public JsonNode handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		HttpEntity entity = response.getEntity();
		return json(EntityUtils.toString(entity, Charsets.UTF_8));
	}

}
