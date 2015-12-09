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
	/**
	 * Calculate and return the sha1 sum of a file
	 * 
	 * @param file the file to calculate the sha1 sum on
	 * 
	 * @return the sha1 of the file
	 * 
	 * @throws B2ApiException if something went wrong with the calculation
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

	public static String urlEncode(String s) throws UnsupportedEncodingException {
		return java.net.URLEncoder.encode(s, "UTF-8");
	}

	public static String urlDecode(String s) throws UnsupportedEncodingException {
		return java.net.URLDecoder.decode(s, "UTF-8");
	}

}
