package synapticloop.b2.response;

/*
 * Copyright (c) 2016 iterate GmbH.
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

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import synapticloop.b2.exception.B2ApiException;

import java.util.ArrayList;
import java.util.List;

public class B2ListPartsResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2ListPartsResponse.class);

	private final String nextPartNumber;
	private final List<B2PartInfoResponse> files;

	public B2ListPartsResponse(String json) throws B2ApiException {
		super(json);

		this.nextPartNumber = this.readString(B2ResponseProperties.KEY_NEXT_FILE_ID);

		JSONArray filesArray = this.readObjects(B2ResponseProperties.KEY_PARTS);

		files = new ArrayList<B2PartInfoResponse>();
		for(int i = 0; i < filesArray.length(); i ++) {
			files.add(new B2PartInfoResponse(filesArray.optJSONObject(i)));
		}

		this.warnOnMissedKeys();
	}

	public String getNextPartNumber() {
		return nextPartNumber;
	}

	public List<B2PartInfoResponse> getFiles() {
		return files;
	}

	@Override
	protected Logger getLogger() { return LOGGER; }
}
