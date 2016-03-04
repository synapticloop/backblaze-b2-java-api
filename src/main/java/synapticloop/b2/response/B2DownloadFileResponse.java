package synapticloop.b2.response;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.input.NullInputStream;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2Exception;

public class B2DownloadFileResponse {
	private static final Logger log = LoggerFactory.getLogger(B2DownloadFileResponse.class);

	private final InputStream content;
	private final Integer contentLength;
	private final String contentType;
	private final String fileId;
	private final String fileName;
	private final String contentSha1;

	private final Map<String, String> fileInfo = new HashMap<>();

	public B2DownloadFileResponse(CloseableHttpResponse response) throws B2Exception {
		try {
			if(null != response.getEntity()) {
				content = response.getEntity().getContent();
			}
			else {
				// HEAD responses do not have an entity
				content = new NullInputStream(0L);
				EntityUtils.consume(response.getEntity());
			}
			contentLength = Integer.parseInt(response.getFirstHeader(HttpHeaders.CONTENT_LENGTH).getValue());
			contentType = response.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();
			contentSha1 = response.getFirstHeader(B2ResponseHeaders.HEADER_X_BZ_CONTENT_SHA1).getValue();
			fileId = response.getFirstHeader(B2ResponseHeaders.HEADER_X_BZ_FILE_ID).getValue();
			fileName = response.getFirstHeader(B2ResponseHeaders.HEADER_X_BZ_FILE_NAME).getValue();
			for (Header header : response.getAllHeaders()) {
				String headerName = header.getName();
				String headerValue = header.getValue();
				if(headerName.toLowerCase(Locale.ENGLISH).startsWith(
						B2ResponseHeaders.HEADER_X_BZ_INFO_PREFIX.toLowerCase(Locale.ENGLISH))) {
					fileInfo.put(headerName.substring(B2ResponseHeaders.HEADER_X_BZ_INFO_PREFIX.length()), headerValue);
				} else {
					log.warn("Found a header named '{}' with value '{}', that was not mapped", headerName, headerValue);
				}
			}
		} catch (IllegalStateException | IOException ex) {
			throw new B2Exception("Could not retrieve response", ex);
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
	 * Get the file info for the file, this is stored as x-bz-info-* headers when 
	 * the file was uploaded.  This will be mapped with the x-bz-info- header
	 * prefix removed.  E.g. if the file was uploaded with the header: 
	 *   x-bz-info-tag=super-secret-tag
	 * This will be returned in the map as key(tag), value (super-secret-tag)
	 * 
	 * @return The map of the file info 
	 */
	public Map<String, String> getFileInfo() { return this.fileInfo; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2DownloadFileResponse{");
		sb.append("content=").append(content);
		sb.append(", contentLength=").append(contentLength);
		sb.append(", contentType='").append(contentType).append('\'');
		sb.append(", fileId='").append(fileId).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append(", contentSha1='").append(contentSha1).append('\'');
		sb.append(", fileInfo=").append(fileInfo);
		sb.append('}');
		return sb.toString();
	}
}
