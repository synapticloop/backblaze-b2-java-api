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

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

public class URLEncoder {
	private static final String UTF_8 = "UTF-8";

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
	public static String encode(String url) {
		try {
			final StringBuilder b = new StringBuilder();
			final StringTokenizer t = new StringTokenizer(url, "/");
			if (!t.hasMoreTokens()) {
				return url;
			}
			while (t.hasMoreTokens()) {
				b.append(java.net.URLEncoder.encode(t.nextToken(), UTF_8));
				if (t.hasMoreTokens()) {
					b.append('/');
				}
			}
			if (url.endsWith("/")) {
				b.append('/');
			}
			// Becuase URLEncoder uses <code>application/x-www-form-urlencoded</code> we have to replace these
			// for proper URI percented encoding.
			return b.toString()
					.replace("+", "%20")
					.replace("*", "%2A");
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

	public static String decode(String url) {
		try {
			return java.net.URLDecoder.decode(url, UTF_8);
		} catch (UnsupportedEncodingException ex) {
			return url;
		}
	}

}
