package synapticloop.b2.request;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2GetUploadUrlResponse;
import synapticloop.b2.response.B2UploadFileResponse;
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
	private B2AuthorizeAccountResponse b2AuthorizeAccountResponse = null;
	private B2GetUploadUrlResponse b2GetUploadUrlResponse = null;
	private File file = null;
	private String fileName = null;
	private String mimeType = null;

	public B2UploadFileRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, B2GetUploadUrlResponse b2GetUploadUrlResponse, File file) {
		this.b2AuthorizeAccountResponse = b2AuthorizeAccountResponse;
		this.b2GetUploadUrlResponse = b2GetUploadUrlResponse;
		this.file = file;
		this.fileName = file.getName();
	}

	public B2UploadFileRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, B2GetUploadUrlResponse b2GetUploadUrlResponse, File file, String mimeType) {
		this(b2AuthorizeAccountResponse, b2GetUploadUrlResponse, file);
		this.mimeType = mimeType;
	}

	public B2UploadFileResponse getResponse() throws B2ApiException {
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		try {

			connection = getPostConnection(b2GetUploadUrlResponse);
			connection.setRequestProperty(HEADER_X_BZ_FILE_NAME, URLEncoder.encode(fileName, VALUE_UTF_8));
			connection.setRequestProperty(HEADER_X_BZ_CONTENT_SHA1, Helper.calculateSha1(file));

			if(null == mimeType) {
				connection.setRequestProperty(HEADER_CONTENT_TYPE, VALUE_B2_X_AUTO);
			} else {
				connection.setRequestProperty(HEADER_CONTENT_TYPE, mimeType);
			}

			inputStream = writeBinaryPostData(connection, Files.readAllBytes(file.toPath()));

			return(new B2UploadFileResponse(inputStream));
		} catch (IOException ex) {
			throw new B2ApiException(ex);
		} finally {
			tidyUp(inputStream, connection);
		}

	}
}
