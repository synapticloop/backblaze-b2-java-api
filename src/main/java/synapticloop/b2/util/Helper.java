package synapticloop.b2.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import synapticloop.b2.exception.B2ApiException;

public class Helper {
	private static final String UTF_8 = "UTF-8";

	/**
	 * Calculate and return the sha1 sum of a file
	 * 
	 * @param file the file to calculate the sha1 sum on
	 * 
	 * @return the sha1 of the file
	 * 
	 * @throws B2ApiException if something went wrong with the calculation, 
	 *   either through a no such algorithm exception (unlikely) or an IO
	 *   exception with the reading of the file
	 */
	public static String calculateSha1(File file) throws B2ApiException {

		MessageDigest messageDigest = null;
		InputStream inputStream = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
			inputStream = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[8192];
			int len = inputStream.read(buffer);

			while (len != -1) {
				messageDigest.update(buffer, 0, len);
				len = inputStream.read(buffer);
			}

			return(new HexBinaryAdapter().marshal(messageDigest.digest()));
		} catch (NoSuchAlgorithmException | IOException ex) {
			throw new B2ApiException(ex);
		} finally {
			if(null != inputStream) {
				try { inputStream.close(); } catch (IOException ex) { }
			}
		}
	}

	/**
	 * UTF-8 url encoding wrapper method.  If there was an unsupported encoding 
	 * exception, return the un-encoded url
	 * 
	 * @param url the URL to encode
	 * 
	 * @return the encoded URL
	 */
	public static String urlEncode(String url) {
		try {
			return java.net.URLEncoder.encode(url, UTF_8);
		} catch (UnsupportedEncodingException ex) {
			// highly unlikely
			return(url);
		}
	}

	/**
	 * UTF-8 url decoding wrapper method.  If there was an unsupported encoding 
	 * exception, return the encoded url
	 * 
	 * @param url the URL to decode
	 * 
	 * @return the decoded URL
	 */

	public static String urlDecode(String url) {
		try {
			return java.net.URLDecoder.decode(url, UTF_8);
		} catch (UnsupportedEncodingException ex) {
			// highly unlikey
			return(url);
		}
	}

}
