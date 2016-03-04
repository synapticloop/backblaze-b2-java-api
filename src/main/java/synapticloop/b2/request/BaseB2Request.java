package synapticloop.b2.request;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import synapticloop.b2.exception.B2Exception;
import synapticloop.b2.response.B2AuthorizeAccountResponse;

public abstract class BaseB2Request {
	private static final Logger log = LoggerFactory.getLogger(BaseB2Request.class);

	protected static final String BASE_API_HOST = "https://api.backblaze.com";
	protected static final String BASE_API_VERSION = "/b2api/v1/";
	protected static final String BASE_API = BASE_API_HOST + BASE_API_VERSION;

    public static final String VALUE_APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
	public static final String VALUE_UTF_8 = "UTF-8";

	public static final int MAX_FILE_COUNT_RETURN = 1000;

	protected final Map<String, String> requestHeaders = new HashMap<>();

    /**
     * Query parameters for request URI
     */
    protected Map<String, String> requestParameters = new HashMap<>();

    /**
     * POST data key value pairs
     */
	protected Map<String, Object> requestBodyData = new HashMap<>();

    private CloseableHttpClient client;

    private final String url;

    /**
     * Instantiate the base B2 with no authorization header.
     *
     * @param client Shared HTTP client
     * @param url Fully qualified request URI
     */
    protected BaseB2Request(CloseableHttpClient client, final String url) {
        this.client = client;
        this.url = url;
    }

    /**
     * Instantiate the base B2 request which adds headers with the authorization
     * token.
     *  @param client Shared HTTP client
     * @param b2AuthorizeAccountResponse the authorize account response
     * @param url Fully qualified request URI
     */
    protected BaseB2Request(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
                            String url) {
        this(client, b2AuthorizeAccountResponse, url, Collections.<String, String>emptyMap());
    }

    protected BaseB2Request(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
                            String url, Map<String, String> headers) {
        this(client, url);
		this.requestHeaders.put(HttpHeaders.CONTENT_TYPE, VALUE_APPLICATION_X_WWW_FORM_URLENCODED);
        this.requestHeaders.put(HttpHeaders.AUTHORIZATION, b2AuthorizeAccountResponse.getAuthorizationToken());
    }

	/**
	 * Execute an HTTP HEAD request and return the response for further parsing
	 * 
	 * @return the response object
	 * 
	 * @throws B2Exception if something went wrong with the call
	 */
	protected CloseableHttpResponse executeHead() throws B2Exception {
		try {
			URI uri = this.buildUri();

			log.debug("HEAD request to URL '{}'", uri.toString());

			HttpHead httpHead = new HttpHead(uri);

			CloseableHttpResponse httpResponse = this.execute(httpHead);

            switch(httpResponse.getStatusLine().getStatusCode()) {
                case HttpStatus.SC_OK:
                    return httpResponse;
            }
            throw new B2Exception(EntityUtils.toString(httpResponse.getEntity()), new HttpResponseException(
                    httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase()));
		} catch (IOException | URISyntaxException ex) {
			throw new B2Exception(ex);
		}
	}

    /**
     * Execute a GET request, returning the data stream from the response.
	 * 
	 * @return The response from the GET request
	 * 
	 * @throws B2Exception if there was an error with the request
	 */
	protected CloseableHttpResponse executeGet() throws B2Exception {
		try {
			URI uri = this.buildUri();

			HttpGet httpGet = new HttpGet(uri);

			CloseableHttpResponse httpResponse = this.execute(httpGet);

			// you will either get an OK or a partial content
            switch(httpResponse.getStatusLine().getStatusCode()) {
                case HttpStatus.SC_OK:
                case HttpStatus.SC_PARTIAL_CONTENT:
                    return httpResponse;
            }
            throw new B2Exception(EntityUtils.toString(httpResponse.getEntity()), new HttpResponseException(
                    httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase()));
		} catch (IOException | URISyntaxException ex) {
			throw new B2Exception(ex);
		}
	}

	/**
	 * Execute a POST request returning the response data as a String
	 * 
	 * @return the response data as a string
	 * 
	 * @throws B2Exception if there was an error with the call, most notably
	 *     a non OK status code (i.e. not 200)
	 */
	protected CloseableHttpResponse executePost() throws B2Exception {
		try {
            URI uri = this.buildUri();

    		String postData = convertPostData();
    		HttpPost httpPost = new HttpPost(uri);

			httpPost.setEntity(new StringEntity(postData));
			CloseableHttpResponse httpResponse = this.execute(httpPost);

            switch(httpResponse.getStatusLine().getStatusCode()) {
                case HttpStatus.SC_OK:
                    return httpResponse;
            }
            throw new B2Exception(EntityUtils.toString(httpResponse.getEntity()), new HttpResponseException(
                    httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase()));
        } catch (IOException | URISyntaxException ex) {
			throw new B2Exception(ex);
		}
	}

	/**
	 * Execute a POST request with the contents of a file.
	 * 
	 * @param entity Content to write
	 * 
	 * @return the string representation of the response
	 * 
	 * @throws B2Exception if there was an error with the call, most notably
	 *     a non OK status code (i.e. not 200)
	 */
	protected CloseableHttpResponse executePost(HttpEntity entity) throws B2Exception {
		try {
            URI uri = this.buildUri();

            HttpPost httpPost = new HttpPost(uri);

            httpPost.setEntity(entity);
			CloseableHttpResponse httpResponse = this.execute(httpPost);

            switch(httpResponse.getStatusLine().getStatusCode()) {
                case HttpStatus.SC_OK:
                    return httpResponse;
            }
            throw new B2Exception(EntityUtils.toString(httpResponse.getEntity()), new HttpResponseException(
                    httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase()));
        } catch (IOException | URISyntaxException ex) {
			throw new B2Exception(ex);
		}
	}

	/**
	 * Convert the stringData and integerData Maps to JSON format, to be included
	 * in the POST body of the request.
	 * 
	 * @return the JSON string of the data
	 * 
	 * @throws B2Exception if there was an error converting the data.
	 */
	protected String convertPostData() throws B2Exception {
		JSONObject jsonObject = new JSONObject();
        for(final String key : requestBodyData.keySet()) {
            try {
                jsonObject.put(key, requestBodyData.get(key));
            }
            catch(JSONException ex) {
                throw new B2Exception(ex);
            }
        }
		return jsonObject.toString();
	}

	/**
	 * Return the URI for this request, which adds any parameters found in the 
	 * 'parameters' data structure
	 * 
	 * @return The URI for this request, with properly encoded parameters
	 * 
	 * @throws URISyntaxException If there was an error building the URI
	 */
	protected URI buildUri() throws URISyntaxException {
		URIBuilder uriBuilder = new URIBuilder(url);

        for(final String key : requestParameters.keySet()) {
            uriBuilder.addParameter(key, requestParameters.get(key));
        }

		return uriBuilder.build();
	}

	/**
	 * Set the headers safely, go through the headers Map and add them to the http 
	 * request with properly encode values.  If they already exist on the http 
	 * request, it will be ignored.
	 * 
	 * To override what headers are set, this should be done in the constructor
	 * of the base request object.
	 * 
	 * @param request The http request to set the headers on
	 */
	protected void setHeaders(HttpUriRequest request) throws B2Exception {
		for (String headerKey : requestHeaders.keySet()) {
			if(!request.containsHeader(headerKey)) {
                final String headerValue = requestHeaders.get(headerKey);
                log.trace("Setting header '" + headerKey + "' to '" + headerValue + "'.");
                request.setHeader(headerKey, headerValue);
			}
            else {
                log.warn("Ignore duplicate header " + headerKey);
            }
		}
	}

    protected CloseableHttpResponse execute(final HttpUriRequest request) throws IOException, B2Exception {
        this.setHeaders(request);
        log.debug("{} request to URL '{}'", request.getMethod(), request.getURI());
        final CloseableHttpResponse httpResponse = client.execute(request);
        if(log.isDebugEnabled()) {
            log.debug("Received status code of: {}, for {} request to url '{}'",
                    httpResponse.getStatusLine().getStatusCode(), request.getMethod(), request.getURI());
        }
        return httpResponse;
    }
}
