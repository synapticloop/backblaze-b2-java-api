package synapticloop.b2.request;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;

public class BaseB2Request {
	private static final Logger LOG = LoggerFactory.getLogger(BaseB2Request.class);

	protected static final String BASE_API_HOST = "https://api.backblaze.com";
	protected static final String BASE_API_VERSION = "/b2api/v1/";

	protected static final String BASE_API = BASE_API_HOST + BASE_API_VERSION;


	private static final String REQUEST_PROPERTY_CHARSET = "charset";
	private static final String REQUEST_PROPERTY_AUTHORIZATION = "Authorization";
	private static final String REQUEST_PROPERTY_CONTENT_TYPE = "Content-Type";

	protected static final String KEY_ACCOUNT_ID = "accountId";
	protected static final String KEY_BUCKET_ID = "bucketId";
	protected static final String KEY_BUCKET_NAME = "bucketName";
	protected static final String KEY_BUCKET_TYPE = "bucketType";
	protected static final String KEY_FILE_NAME = "fileName";
	protected static final String KEY_FILE_ID = "fileId";
	protected static final String KEY_START_FILE_NAME = "startFileName";
	protected static final String KEY_START_FILE_ID = "startFileId";
	protected static final String KEY_MAX_FILE_COUNT = "maxFileCount";

	protected static final int MAX_FILE_COUNT_RETURN = 1000;

	protected static final String HEADER_CONTENT_TYPE = "Content-Type";
	protected static final String HEADER_X_BZ_FILE_NAME = "X-Bz-File-Name";
	protected static final String HEADER_X_BZ_CONTENT_SHA1 = "X-Bz-Content-Sha1";

	protected static final String VALUE_APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
	protected static final String VALUE_UTF_8 = "UTF-8";
	protected static final String VALUE_B2_X_AUTO = "b2/x-auto";

	protected String url = null;
	protected Map<String, String> headers = new HashMap<String, String>();

	protected Map<String, String> stringData = new HashMap<String, String>();
	protected Map<String, Integer> integerData = new HashMap<String, Integer>();

	protected BaseB2Request(B2AuthorizeAccountResponse b2AuthorizeAccountResponse) {
		if(null != b2AuthorizeAccountResponse) {
			headers.put(REQUEST_PROPERTY_AUTHORIZATION, b2AuthorizeAccountResponse.getAuthorizationToken());
		}
		headers.put(REQUEST_PROPERTY_CONTENT_TYPE, VALUE_APPLICATION_X_WWW_FORM_URLENCODED);
		headers.put(REQUEST_PROPERTY_CHARSET, VALUE_UTF_8);
	}

	protected String getPostData() throws B2ApiException {
		JSONObject jsonObject = new JSONObject();
		Iterator<String> iterator = stringData.keySet().iterator();

		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			try {
				jsonObject.put(key, stringData.get(key));
			} catch (JSONException ex) {
				throw new B2ApiException(ex);
			}
		}

		iterator = integerData.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			try {
				jsonObject.put(key, integerData.get(key));
			} catch (JSONException ex) {
				throw new B2ApiException(ex);
			}
		}

		return(jsonObject.toString());
	}

	protected String executeGet() throws B2ApiException {
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		setHeaders(httpGet);

		LOG.debug("GET request to URL '{}'", url);

		try {

			CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			String response = EntityUtils.toString(httpResponse.getEntity());

			LOG.debug("Received status code of:{}, for GET request to url '{}'", statusCode, url);

			if(statusCode != 200) {
				throw new B2ApiException(response);
			} else {
				return(response);
			}
		} catch (IOException ex) {
			throw new B2ApiException(ex);
		}

	}

	protected String executePost() throws B2ApiException {
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		String postData = getPostData();
		HttpPost httpPost = new HttpPost(url);

		setHeaders(httpPost);

		LOG.debug("POST request to URL '{}', with data of '{}'", url, postData);

		try {
			httpPost.setEntity(new StringEntity(postData));
			CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();

			LOG.debug("Received status code of:{}, for POST request to url '{}'", statusCode, url);

			String response = EntityUtils.toString(httpResponse.getEntity());


			if(statusCode != 200) {
				if(response.trim().length() == 0) {
					throw new B2ApiException(httpResponse.getStatusLine().toString());
				} else {
					throw new B2ApiException(response);
				}
			} else {
				return(response);
			}
		} catch (IOException ex) {
			throw new B2ApiException(ex);
		}
	}

	protected String executePost(File file) throws B2ApiException {
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		setHeaders(httpPost);

		LOG.debug("POST request to URL '{}', with file", url);

		try {
			httpPost.setEntity(new FileEntity(file));
			CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			String response = EntityUtils.toString(httpResponse.getEntity());

			LOG.debug("Received status code of:{}, for POST request to url '{}'", statusCode, url);

			if(statusCode != 200) {
				throw new B2ApiException(response);
			} else {
				return(response);
			}
		} catch (IOException ex) {
			throw new B2ApiException(ex);
		}
	}

	/**
	 * Set the headers safely, go through the headers Map and add them to the http 
	 * request.  If they already exist on the http request, it will be ignored.  
	 * To over-ride what headers are set, this should be done in the constructor 
	 * 
	 * @param httpRequestBase The http request to set the headers on
	 */
	private void setHeaders(HttpRequestBase httpRequestBase) {
		Set<String> headerKeySet = headers.keySet();
		for (String headerKey : headerKeySet) {
			if(!httpRequestBase.containsHeader(headerKey)) {
				httpRequestBase.setHeader(headerKey, headers.get(headerKey));
			}
		}
	}
}
