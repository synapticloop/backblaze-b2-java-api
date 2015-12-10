package synapticloop.b2.response;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;

public class B2DownloadFileResponse extends BaseB2Response {
	private static final Logger LOG = LoggerFactory.getLogger(B2DownloadFileResponse.class);

	private static final int HEADER_CONTENT_LENGTH = 0;
	private static final int HEADER_CONTENT_TYPE = 1;
	private static final int HEADER_X_BZ_FILE_ID = 2;
	private static final int HEADER_X_BZ_FILE_NAME = 3;
	private static final int HEADER_X_BZ_CONTENT_SHA1 = 4;

	// headers are all lowercase for lookup
	private static final Map<String, Integer> headerLookup = new HashMap<String, Integer>();
	static {
		headerLookup.put("content-length", HEADER_CONTENT_LENGTH);
		headerLookup.put("content-type", HEADER_CONTENT_TYPE);
		headerLookup.put("x-bz-file-id", HEADER_X_BZ_FILE_ID);
		headerLookup.put("x-bz-file-name", HEADER_X_BZ_FILE_NAME);
		headerLookup.put("x-bz-content-sha1", HEADER_X_BZ_CONTENT_SHA1);
	}

	private static final Set<String> ignoredHeaders = new HashSet<String>();
	static {
		ignoredHeaders.add("server");
		ignoredHeaders.add("x-content-type-options");
		ignoredHeaders.add("x-xss-protection");
		ignoredHeaders.add("x-frame-options");
		ignoredHeaders.add("cache-control");
		ignoredHeaders.add("date");
	}

	private InputStream content = null;
	private Integer contentLength = null;
	private String contentType = null;
	private String fileId = null;
	private String fileName = null;
	private String contentSha1 = null;
	
	private Map<String, String> fileInfo = new HashMap<String, String>();

	public B2DownloadFileResponse(CloseableHttpResponse closeableHttpResponse) throws B2ApiException {
		try {
			// HEAD responses do not have an entity
			if(null != closeableHttpResponse.getEntity()) {
				content = closeableHttpResponse.getEntity().getContent();
			}
			parseHeaders(closeableHttpResponse);

		} catch (IllegalStateException | IOException ex) {
			throw new B2ApiException("Could not retrieve response", ex);
		} finally {
		}
	}

	private void parseHeaders(CloseableHttpResponse closeableHttpResponse) throws B2ApiException {
		Header[] allHeaders = closeableHttpResponse.getAllHeaders();
		for (Header header : allHeaders) {
			String headerName = header.getName();
			String headerValue = header.getValue();
			// we need to lowercase the headers, as they do not match the normal 
			// camel-case of the input headers
			if(headerLookup.containsKey(headerName.toLowerCase())) {
				switch (headerLookup.get(headerName.toLowerCase())) {
				case HEADER_CONTENT_LENGTH:
					contentLength = Integer.parseInt(headerValue);
					break;
				case HEADER_CONTENT_TYPE:
					contentType = headerValue;
					break;
				case HEADER_X_BZ_CONTENT_SHA1:
					contentSha1 = headerValue;
					break;
				case HEADER_X_BZ_FILE_ID:
					fileId = headerValue;
					break;
				case HEADER_X_BZ_FILE_NAME:
					fileName = headerValue;
					break;
				default:
					throw new B2ApiException("Unknown header for lookup '" + headerName + "'");
				}
			} else {
				// could not find it in the lookup, need to only look for 'X-Bz-Info-*' 
				// headers
				if(headerName.startsWith(HEADER_X_BZ_INFO_PREFIX)) {
					fileInfo.put(headerName.substring(HEADER_X_BZ_INFO_PREFIX.length()), headerValue);
				} else {
					if(!ignoredHeaders.contains(headerName.toLowerCase())) {
						LOG.warn("Found a header named '{}' with value '{}', that was not mapped", headerName, headerValue);
					}
				}
			}
		}
	}

	/**
	 * Get the content of the downloaded file, if this was a HEAD request, then 
	 * this will return null.
	 * 
	 * @return the downloaded file
	 */
	public InputStream getContent() {
		return this.content;
	}

	/**
	 * Get the content length of the downloaded file
	 * 
	 * @return the length of the content
	 */
	public Integer getContentLength() {
		return this.contentLength;
	}

	/**
	 * Get the content type of the downloaded file
	 * 
	 * @return the content type of the downloaded file
	 */
	public String getContentType() {
		return this.contentType;
	}

	public String getFileId() {
		return this.fileId;
	}

	public String getFileName() {
		return this.fileName;
	}

	public String getContentSha1() {
		return this.contentSha1;
	}

	public Map<String, String> getFileInfo() {
		return this.fileInfo;
	}
}
