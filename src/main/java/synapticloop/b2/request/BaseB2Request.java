package synapticloop.b2.request;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2GetUploadUrlResponse;

public class BaseB2Request {
	private static final String UTF_8 = "utf-8";
	private static final String REQUEST_PROPERTY_CHARSET = "charset";
	private static final String REQUEST_METHOD_POST = "POST";
	private static final String REQUEST_PROPERTY_AUTHORIZATION = "Authorization";
	private static final String REQUEST_PROPERTY_CONTENT_TYPE = "Content-Type";
	private static final String REQUEST_PROPERTY_CONTENT_LENGTH = "Content-Length";
	protected static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";

	protected static final String KEY_ACCOUNT_ID = "accountId";
	protected static final String KEY_BUCKET_ID = "bucketId";
	protected static final String KEY_BUCKET_NAME = "bucketName";
	protected static final String KEY_BUCKET_TYPE = "bucketType";
	protected static final String KEY_FILE_NAME = "fileName";
	protected static final String KEY_FILE_ID = "fileId";

	protected static final String HEADER_CONTENT_TYPE = "Content-Type";
	protected static final String HEADER_X_BZ_FILE_NAME = "X-Bz-File-Name";
	protected static final String HEADER_X_BZ_CONTENT_SHA1 = "X-Bz-Content-Sha1";

	protected static final String VALUE_B2_X_AUTO = "b2/x-auto";
	protected static final String VALUE_UTF_8 = "UTF-8";

	protected String getPostData(Map<String, String> data) throws B2ApiException {
		JSONObject jsonObject = new JSONObject();
		Iterator<String> iterator = data.keySet().iterator();

		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			try {
				jsonObject.put(key, data.get(key));
			} catch (JSONException ex) {
				throw new B2ApiException(ex);
			}
		}

		return(jsonObject.toString());
	}


	protected HttpURLConnection getApiPostConnection(String urlSuffix, B2AuthorizeAccountResponse b2AuthorizeAccountResponse) throws IOException {
		return(getPostConnection(b2AuthorizeAccountResponse.getApiUrl() + urlSuffix, b2AuthorizeAccountResponse));
	}

	protected HttpURLConnection getDownloadPostConnection(String urlSuffix, B2AuthorizeAccountResponse b2AuthorizeAccountResponse) throws IOException {
		return(getPostConnection(b2AuthorizeAccountResponse.getDownloadUrl() + urlSuffix, b2AuthorizeAccountResponse));
	}

	private HttpURLConnection getPostConnection(String fullUrl, B2AuthorizeAccountResponse b2AuthorizeAccountResponse) throws IOException {
		HttpURLConnection connection = null;
		URL url = new URL(fullUrl);
		connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod(REQUEST_METHOD_POST);
		connection.setRequestProperty(REQUEST_PROPERTY_AUTHORIZATION, b2AuthorizeAccountResponse.getAuthorizationToken());
		connection.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED);
		connection.setRequestProperty(REQUEST_PROPERTY_CHARSET, UTF_8);
		return(connection);
	}

	protected HttpURLConnection getPostConnection(B2GetUploadUrlResponse b2GetUploadUrlResponse) throws IOException {
		HttpURLConnection connection = null;
		URL url = new URL(b2GetUploadUrlResponse.getUploadUrl());
		connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod(REQUEST_METHOD_POST);
		connection.setRequestProperty(REQUEST_PROPERTY_AUTHORIZATION, b2GetUploadUrlResponse.getAuthorizationToken());
		connection.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED);
		connection.setRequestProperty(REQUEST_PROPERTY_CHARSET, UTF_8);
		return(connection);
	}

	protected String executePost(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String url, Map<String, String> data) throws B2ApiException {
		String postData = getPostData(data);

		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(b2AuthorizeAccountResponse.getApiUrl() + url);
		httpPost.setHeader(REQUEST_PROPERTY_AUTHORIZATION, b2AuthorizeAccountResponse.getAuthorizationToken());
		httpPost.setHeader(REQUEST_PROPERTY_CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED);
		httpPost.setHeader(REQUEST_PROPERTY_CHARSET, UTF_8);

		try {
			httpPost.setEntity(new StringEntity(postData));

			CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			String response = EntityUtils.toString(httpResponse.getEntity());

			if(statusCode != 200) {
				throw new B2ApiException(response);
			} else {
				return(response);
			}
		} catch (IOException ex) {
			throw new B2ApiException(ex);
		}
	}

	protected InputStream writePostData(HttpURLConnection connection, Map<String, String> data) throws B2ApiException {
		DataOutputStream dataOutputStream = null;
		try {

			String postData = getPostData(data);
			connection.setRequestProperty(REQUEST_PROPERTY_CONTENT_LENGTH, Integer.toString(postData.length()));

			connection.setDoOutput(true);
			dataOutputStream = new DataOutputStream(connection.getOutputStream());
			dataOutputStream.write(postData.getBytes(StandardCharsets.UTF_8));
			dataOutputStream.flush();
			return(new BufferedInputStream(connection.getInputStream()));
		} catch(IOException ex) {
			throw new B2ApiException(ex);
		} finally {
			if(null != dataOutputStream) {
				try {
					dataOutputStream.close();
				} catch(IOException ex) {
					// do nothing
				}
			}
		}
	}

	protected InputStream writeBinaryPostData(HttpURLConnection connection, byte[] data) throws B2ApiException {
		DataOutputStream dataOutputStream = null;
		try {

			connection.setRequestProperty(REQUEST_PROPERTY_CONTENT_LENGTH, Integer.toString(data.length));

			connection.setDoOutput(true);
			dataOutputStream = new DataOutputStream(connection.getOutputStream());
			dataOutputStream.write(data);
			dataOutputStream.flush();
			return(new BufferedInputStream(connection.getInputStream()));
		} catch(IOException ex) {
			throw new B2ApiException(ex);
		} finally {
			if(null != dataOutputStream) {
				try {
					dataOutputStream.close();
				} catch(IOException ex) {
					// do nothing
				}
			}
		}
	}
	protected void tidyUp(InputStream inputStream, HttpURLConnection connection) {
		if(null != inputStream) {
			try {
				inputStream.close();
			} catch (IOException ex) {
				// do nothing
			} }

		if(null != connection) { 
			connection.disconnect(); 
		}

	}
}
