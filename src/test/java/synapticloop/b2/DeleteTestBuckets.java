package synapticloop.b2;

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

import java.util.List;

import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2FileInfoResponse;

/**
 * This is a utility class to delete all of the test buckets in the backblaze
 * service - this will delete all buckets that start with the prefix:
 *   b2api-test-
 * 
 *
 */
public class DeleteTestBuckets {

	public static void main(String[] args) throws Exception {
		String b2AccountId = System.getenv(B2TestHelper.B2_ACCOUNT_ID);
		String b2ApplicationKey = System.getenv(B2TestHelper.B2_APPLICATION_KEY);

		boolean isOK = true;

		if(null == b2AccountId) {
			System.err.println("Could not find the environment variable '" + B2TestHelper.B2_ACCOUNT_ID + "', cannot continue with tests, exiting...");
			isOK = false;
		}

		if(null == b2ApplicationKey) {
			System.err.println("Could not find the environment variable '" + B2TestHelper.B2_APPLICATION_KEY + "', cannot continue with tests, exiting...");
			isOK = false;
		}

		if(!isOK) {
			System.exit(-1);
		}

		B2ApiClient client = new B2ApiClient();
		client.authenticate(b2AccountId, b2ApplicationKey);
		List<B2BucketResponse> listBuckets = client.listBuckets();
		System.out.println("Found " + listBuckets.size() + " buckets.");
		for (B2BucketResponse b2BucketResponse : listBuckets) {
			if(b2BucketResponse.getBucketName().startsWith(B2TestHelper.B2_BUCKET_PREFIX)) {
				// go through and delete all of the files
				String bucketId = b2BucketResponse.getBucketId();
				System.out.println("Deleting files in bucket '" + bucketId + "'.");
				List<B2FileInfoResponse> files = client.listFileVersions(bucketId).getFiles();
				for (B2FileInfoResponse b2FileInfoResponse : files) {
					String fileName = b2FileInfoResponse.getFileName();
					System.out.println("Deleting file version name: '" + fileName + "'.");
					client.deleteFileVersion(fileName, b2FileInfoResponse.getFileId());
				}
				System.out.println("Deleting bucket '" + bucketId + "'.");
				client.deleteBucket(bucketId);
			}
		}
	}

}
