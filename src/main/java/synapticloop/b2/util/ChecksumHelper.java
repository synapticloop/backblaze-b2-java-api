package synapticloop.b2.util;

/*
 * Copyright (c) 2016 - 2017 Synapticloop.
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.apache.commons.io.IOUtils;

public class ChecksumHelper {
	/**
	 * Calculate and return the sha1 sum of a file
	 *
	 * @param file the file to calculate the sha1 sum on
	 *
	 * @return the sha1 of the file
	 *
	 * @throws IOException if something went wrong with the calculation
	 */
	public static String calculateSha1(File file) throws IOException {
		try {
			return calculateSha1(new FileInputStream(file));
		}
		catch(FileNotFoundException e) {
			throw new IOException(e);
		}
	}

	/**
	 * Calculate and return the sha1 sum of an input stream
	 *
	 * @param in the input stream to calculate the sha1 sum of
	 *
	 * @return the sha1 sum
	 *
	 * @throws IOException if there was an error calculating the sha1 sum
	 */
	public static String calculateSha1(InputStream in) throws IOException {

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
		} catch (NoSuchAlgorithmException ex) {
			throw new IOException(ex);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}
}
