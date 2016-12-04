
```

see [B2ApiClient.java](https://github.com/synapticloop/backblaze-b2-java-api/blob/master/src/main/java/synapticloop/b2/B2ApiClient.java) for a complete list of relatively self-explanatory methods.

```
// create a new B2ApiClient
B2ApiClient()

// authenticate the client
authenticate(String, String)

// create a bucket
createBucket(String, BucketType)

// delete bucket
deleteBucket(String)

// delete a version of a file
deleteFileVersion(String, String)

// download the full file by id, returning a variety of objects
downloadFileById(String)
downloadFileByIdToFile(String, File)
downloadFileByIdToStream(String)

// download the full file by name, returning a variety of objects
downloadFileByName(String, String)
downloadFileByNameToFile(String, String, File)
downloadFileByNameToStream(String, String)

// download partial content of a file by id, returning a variety of objects
downloadFileRangeById(String, long, long)
downloadFileRangeByIdToFile(String, File, long, long)
downloadFileRangeByIdToStream(String, long, long)

// download partial content of a file by name, returning a variety of objects
downloadFileRangeByName(String, String, long, long)
downloadFileRangeByNameToFile(String, String, File, long, long)
downloadFileRangeByNameToStream(String, String, long, long)

// retrieve information on a file
getFileInfo(String)

// return the headers associated with a file
headFileById(String)

// list all of the buckets
listBuckets()

// list file names
listFileNames(String)
listFileNames(String, String, Integer)

// list file versions
listFileVersions(String)
listFileVersions(String, String)
listFileVersions(String, String, String, Integer)

// update the bucket type (private or public)
updateBucket(String, BucketType)

// upload a file
uploadFile(String, String, File)
uploadFile(String, String, File, Map<String, String>)
uploadFile(String, String, File, String)
uploadFile(String, String, File, String, Map<String, String>)


```


## Large File Support

Large files can range in size from 100MB to 10TB.

Each large file must consist of at least 2 parts, and all of the parts except the last one must be at least 100MB in size. The last part must contain at least one byte.

```
startLargeFileUpload(String bucketId, String fileName, String mimeType, Map<String, String> fileInfo)
getUploadPartUrl(String fileId)
uploadLargeFilePart(String fileId, int partNumber, HttpEntity entity, String sha1Checksum)
finishLargeFileUpload(String fileId, String[] partSha1Array)

// you can list the parts that are not yet finished
listUnfinishedLargeFiles(String bucketId, String startFileId, Integer maxFileCount)
```

