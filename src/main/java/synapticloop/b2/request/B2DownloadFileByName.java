package synapticloop.b2.request;

import java.io.File;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2DownloadFileResponse;

/**
 * <p>Downloads one file by providing the name of the bucket and the name of the file.</p>
 * 
 * <p>The base URL to use comes from the b2_authorize_account call, and looks something like https://f345.backblaze.com. The "f" in the URL stands for "file", and the number is the cluster number that your account is in. To this base, you add your bucket name, a "/", and then the name of the file. The file name may itself include more "/" characters.</p>
 * <p>If you have a bucket named "photos", and a file called "cute/kitten.jpg", then the URL for downloading that file would be: https://f345.backblaze.com/file/photos/cute/kitten.jpg.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_download_file_by_name</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_download_file_by_name.html">http://www.backblaze.com/b2/docs/b2_download_file_by_name.html</a>
 * 
 * @author synapticloop
 */

public class B2DownloadFileByName extends BaseB2Request {

	private static final String B2_DOWNLOAD_FILE_BY_ID = BASE_API_VERSION + "b2_download_file_by_id";
	private File fileTo = null;

	public B2DownloadFileByName(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileName) {
		super(b2AuthorizeAccountResponse);
		url = b2AuthorizeAccountResponse.getDownloadUrl() + B2_DOWNLOAD_FILE_BY_ID;
		stringData.put(KEY_FILE_ID, fileName);
	}

	public B2DownloadFileByName(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileName, File fileTo) {
		this(b2AuthorizeAccountResponse, fileName);
		this.fileTo = fileTo;
	}

	public B2DownloadFileResponse getResponse() throws B2ApiException {
		return(new B2DownloadFileResponse(executePostWithData(), fileTo));
	}
}
