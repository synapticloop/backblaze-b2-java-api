package synapticloop.b2.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import synapticloop.b2.exception.B2ApiException;

public class Helper {
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
}
