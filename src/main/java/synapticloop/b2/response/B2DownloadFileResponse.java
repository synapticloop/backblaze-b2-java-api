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
	private static final Logger LOGGER = LoggerFactory.getLogger(B2DownloadFileResponse.class);

	private static final int HEADER_CONTENT_LENGTH = 0;
	private static final int HEADER_CONTENT_TYPE = 1;
	private static final int HEADER_X_BZ_FILE_ID = 2;
	private static final int HEADER_X_BZ_FILE_NAME = 3;
	private static final int HEADER_X_BZ_CONTENT_SHA1 = 4;
	private static final int HEADER_ACCEPT_RANGES = 5;
	private static final int HEADER_CONTENT_RANGE = 6;

	// headers are all lowercase for lookup
	private static final Map<String, Integer> headerLookup = new HashMap<String, Integer>();
	static {
		headerLookup.put("content-length", HEADER_CONTENT_LENGTH);
		headerLookup.put("content-type", HEADER_CONTENT_TYPE);
		headerLookup.put("x-bz-file-id", HEADER_X_BZ_FILE_ID);
		headerLookup.put("x-bz-file-name", HEADER_X_BZ_FILE_NAME);
		headerLookup.put("x-bz-content-sha1", HEADER_X_BZ_CONTENT_SHA1);
		headerLookup.put("accept-ranges", HEADER_ACCEPT_RANGES);
		headerLookup.put("content-range", HEADER_CONTENT_RANGE);
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
	private String acceptRanges = null;
	private String contentRanges = null;

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
				case HEADER_ACCEPT_RANGES:
					acceptRanges = headerValue;
					break;
				case HEADER_CONTENT_RANGE:
					contentRanges = headerValue;
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
						LOGGER.warn("Found a header named '{}' with value '{}', that was not mapped", headerName, headerValue);
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
	public InputStream getContent() { return this.content; }

	/**
	 * Get the content length of the downloaded file
	 * 
	 * @return the length of the content
	 */
	public Integer getContentLength() { return this.contentLength; }

	/**
	 * Get the content type of the downloaded file
	 * 
	 * @return the content type of the downloaded file
	 */
	public String getContentType() { return this.contentType; }

	/**
	 * Get the fileId that uniquely identifies this file
	 * 
	 * @return the fileId
	 */
	public String getFileId() { return this.fileId; }

	/**
	 * Get the name of the file as stored in the backblaze bucket
	 * 
	 * @return the name of the file as stored in the backblaze bucket
	 */
	public String getFileName() { return this.fileName; }

	/**
	 * Get the SHA1 of the returned content
	 * 
	 * @return the SHA1 of the returned content
	 */
	public String getContentSha1() { return this.contentSha1; }

	/**
	 * Get the Accept-Ranges header value, this will only be non-null if the 
	 * range (bytes=from-to) header was set on the request.
	 * 
	 * @return The accept ranges header value
	 */
	public String getAcceptRanges() { return this.acceptRanges; }

	/**
	 * Get the Content-Ranges header value, this will only be non-null if the 
	 * range (bytes=from-to) header was set on the request.
	 * 
	 * @return The content ranges header value
	 */
	public String getContentRanges() { return this.contentRanges; }

	/**
	 * Get the file info for the file, this is stored as x-bz-info-* headers when 
	 * the file was uploaded.  This will be mapped with the x-bz-info- header
	 * prefix removed.  E.g. if the file was uploaded with the header: 
	 *   x-bz-info-tag=super-secret-tag
	 * This will be returned in the map as key(tag), value (super-secret-tag)
	 * 
	 * @return The map of the file info 
	 */
	public Map<String, String> getFileInfo() { return this.fileInfo; }
}
