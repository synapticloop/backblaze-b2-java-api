package synapticloop.b2.io;

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

import org.apache.commons.io.input.CountingInputStream;
import org.apache.http.HttpConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpMethodReleaseInputStream extends CountingInputStream {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpMethodReleaseInputStream.class);

	private HttpResponse response;

	/**
	 * Create a HTTP method release input Stream
	 * 
	 * @param response The HTTP response to read from
	 * 
	 * @throws IOException If there is a problem reading from the response
	 * @throws NullPointerException If the response has no message entity
	 */
	public HttpMethodReleaseInputStream(final HttpResponse response) throws IOException {
		super(response.getEntity().getContent());
		this.response = response;
	}

	/**
	 * This will force close the connection if the content has not been fully consumed
	 *
	 * @throws IOException if an I/O error occurs
	 * @see CloseableHttpResponse#close()
	 * @see HttpConnection#shutdown()
	 */
	@Override
	public void close() throws IOException {
		if(response instanceof CloseableHttpResponse) {
			long read = this.getByteCount();
			if(read == response.getEntity().getContentLength()) {
				// Fully consumed
				super.close();
			} else {
				LOGGER.warn("Abort connection for response '{}'", response);
				// Close an HTTP response as quickly as possible, avoiding consuming
				// response data unnecessarily though at the expense of making underlying
				// connections unavailable for reuse.
				// The response proxy will force close the connection.
				((CloseableHttpResponse) response).close();
			}
		} else {
			// Consume and close
			super.close();
		}
	}
}
