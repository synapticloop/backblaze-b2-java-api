package synapticloop.b2.request;

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

public class B2DownloadFileByName {

	public B2DownloadFileByName() {
		
	}
}
