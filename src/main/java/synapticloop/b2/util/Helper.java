package synapticloop.b2.util;

/*
 * Copyright (c) 2016 synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.apache.commons.io.IOUtils;

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
	 * @throws B2ApiException if something went wrong with the calculation
	 */
	public static String calculateSha1(File file) throws B2ApiException {
		try {
			return calculateSha1(new FileInputStream(file));
		}
		catch(FileNotFoundException e) {
			throw new B2ApiException(e);
		}
	}

	/**
	 * Calculate and return the sha1 sum of an input stream
	 * 
	 * @param in the input stream to calculate the sha1 sum of
	 * 
	 * @return the sha1 sum
	 * 
	 * @throws B2ApiException if there was an error calculating the sha1 sum
	 */
	public static String calculateSha1(InputStream in) throws B2ApiException {

		MessageDigest messageDigest;
		InputStream inputStream = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
			inputStream = new BufferedInputStream(in);
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
			IOUtils.closeQuietly(inputStream);
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
			return url;
		}
	}

	/**
	 * UTF-8 url encoding wrapper method which does not encode slashes for requests.
	 * See <a href="https://www.backblaze.com/b2/docs/string_encoding.html">https://www.backblaze.com/b2/docs/string_encoding.html</a>
	 * for usage why it will not be encoded.
	 * 
	 * If there was an unsupported encoding exception, return the un-encoded url
	 * 
	 * @param url the URL to encode
	 * 
	 * @return the encoded URL
	 */
	public static String urlEncodeFileName(String url) {
		try {
			return java.net.URLEncoder.encode(url, UTF_8).replace("%2F", "/");
		} catch (UnsupportedEncodingException ex) {
			return url;
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
			return url;
		}
	}

}
