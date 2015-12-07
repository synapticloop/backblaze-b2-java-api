package synapticloop.b2.request;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2GetUploadUrlResponse;
import synapticloop.b2.util.Helper;

/**
 * <p>Uploads one file to B2, returning its unique file ID.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_upload_file</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_upload_file.html">http://www.backblaze.com/b2/docs/b2_upload_file.html</a>
 * 
 * @author synapticloop
 */

public class B2UploadFileRequest extends BaseB2Request {
	private File file = null;
	private String mimeType = null;
	private Map<String, String> fileInfo = null;
	private String fileName = null;

	public B2UploadFileRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, B2GetUploadUrlResponse b2GetUploadUrlResponse, String fileName, File file) {
		super(b2AuthorizeAccountResponse);
		this.fileName = fileName;
		this.url = b2GetUploadUrlResponse.getUploadUrl();
		this.file = file;

		// now go through and add in the 'X-Bz-Info-*' headers
		if(null != fileInfo) {
			Iterator<String> iterator = fileInfo.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				headers.put("X-Bz-Info-" + key, fileInfo.get(key));
			}
		}
	}


	public B2UploadFileRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, B2GetUploadUrlResponse b2GetUploadUrlResponse, String fileName, File file, Map<String, String> fileInfo) {
		super(b2AuthorizeAccountResponse);
		this.fileInfo = fileInfo;
	}

	public B2UploadFileRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, B2GetUploadUrlResponse b2GetUploadUrlResponse, String fileName, File file, String mimeType) {
		this(b2AuthorizeAccountResponse, b2GetUploadUrlResponse, fileName, file);
		this.mimeType = mimeType;
	}

	public B2UploadFileRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, B2GetUploadUrlResponse b2GetUploadUrlResponse, String fileName, File file, String mimeType, Map<String, String> fileInfo) {
		this(b2AuthorizeAccountResponse, b2GetUploadUrlResponse, fileName, file, fileInfo);
	}

	public B2FileResponse getResponse() throws B2ApiException {
		if(null == mimeType) {
			headers.put(HEADER_CONTENT_TYPE, VALUE_B2_X_AUTO);
		} else {
			headers.put(HEADER_CONTENT_TYPE, mimeType);
		}

		try {
			headers.put(HEADER_X_BZ_FILE_NAME, URLEncoder.encode(fileName, VALUE_UTF_8));
		} catch (UnsupportedEncodingException ex) {
			// should never happen
		}

		headers.put(HEADER_X_BZ_CONTENT_SHA1, Helper.calculateSha1(file));

		return(new B2FileResponse(executePost(file)));
	}
}
