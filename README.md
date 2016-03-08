[![Build Status](https://travis-ci.org/synapticloop/backblaze-b2-java-api.svg?branch=master)](https://travis-ci.org/synapticloop/backblaze-b2-java-api)[![Download](https://api.bintray.com/packages/synapticloop/maven/backblaze-b2-java-api/images/download.svg)](https://bintray.com/synapticloop/maven/backblaze-b2-java-api/_latestVersion)[![GitHub Release](https://img.shields.io/github/release/synapticloop/backblaze-b2-java-api.svg)](https://github.com/synapticloop/backblaze-b2-java-api/releases)

# backblaze-b2-java-api



> A java api for the truly excellent backblaze b2 storage service


# Usage

```
String b2AccountId = ""; // your b2 account ID
String b2ApplicationKey = ""; // your b2 application Key

B2ApiClient b2ApiClient = new B2ApiClient();
b2ApiClient.authorize(b2AccountId, b2ApplicationKey);

// now create a private bucket
B2BucketResponse createPrivateBucket = b2ApiClient.createBucket("super-secret-bucket" , BucketType.ALL_PRIVATE);

// or a public one
B2BucketResponse createPublicBucket = b2ApiClient.createBucket("everyone-has-access" , BucketType.ALL_PUBLIC);

// upload a file
b2ApiClient.uploadFile(createPrivateBucket.getBucketId(), "myfile.txt", new File("/tmp/temporary-file.txt"));
```

see [B2ApiClient.java](https://github.com/synapticloop/backblaze-b2-java-api/blob/master/src/main/java/synapticloop/b2/B2ApiClient.java) for a complete list of relatively self-explanatory methods.

```
// create a new B2ApiClient
B2ApiClient()

// authorize the client
authorize(String, String)

// create a bucket
createBucket(String, BucketType)

// delete bucket
deleteBucket(String)

// delete bucket and all containing files
deleteBucketFully(String)

// delete a version of a file
deleteFileVersion(String, String)

// download the full file by id, returning a variety of objects
downloadFileById(String)
downloadFileByIdToBytes(String)
downloadFileByIdToFile(String, File)
downloadFileByIdToStream(String)

// download the full file by name, returning a variety of objects
downloadFileByName(String, String)
downloadFileByNameToBytes(String, String)
downloadFileByNameToFile(String, String, File)
downloadFileByNameToStream(String, String)

// download partial content of a file by id, returning a variety of objects
downloadFileRangeById(String, long, long)
downloadFileRangeByIdToBytes(String, long, long)
downloadFileRangeByIdToFile(String, File, long, long)
downloadFileRangeByIdToStream(String, long, long)

// download partial content of a file by name, returning a variety of objects
downloadFileRangeByName(String, String, long, long)
downloadFileRangeByNameToBytes(String, String, long, long)
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

# Building the Package

## *NIX/Mac OS X

From the root of the project, simply run

`./gradlew build`


## Windows

`./gradlew.bat build`


This will compile and assemble the artefacts into the `build/libs/` directory.

Note that this may also run tests (if applicable see the Testing notes)

# Running the Tests

## *NIX/Mac OS X

From the root of the project, simply run

`gradle --info test`

if you do not have gradle installed, try:

`gradlew --info test`

## Windows

From the root of the project, simply run

`gradle --info test`

if you do not have gradle installed, try:

`./gradlew.bat --info test`


The `--info` switch will also output logging for the tests


**WARNING:** These tests make calls against resources (either API calls to a service provider, or consumption of resources from a service provider) which may contribute to your limits, which may lead to a cost.


**!!!   IMPORTANT   !!!** 

You **MUST** have the following environment variables set:

```
export B2_ACCOUNT_ID="your-account-id"
export B2_APPLICATION_KEY="your-application-key"
```

# Logging

slf4j is the logging framework used for this project.  In order to use a logging framework with this project, sample configurations are below:

## Log4j

A sample `log4j2.xml` is below:

```
<Configuration status="WARN">
	<Appenders>
		<Console name="Console" target="SYSTEM_OUT">
			<PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
		</Console>
	</Appenders>
	<Loggers>
		<Root level="trace">
			<AppenderRef ref="Console"/>
		</Root>
	</Loggers>
</Configuration>
```

# Artefact Publishing - Github

This project publishes artefacts to [GitHib](https://github.com/)

> Note that the latest version can be found [https://github.com/synapticloop/backblaze-b2-java-api/releases](https://github.com/synapticloop/backblaze-b2-java-api/releases)

As such, this is not a repository, but a location to download files from.

# Dependency Management Maven

This project publishes artefacts to [Maven Central](https://search.maven.org/)

> Note that the latest version can be found [mvn central](http://search.maven.org/#artifactdetails|synapticloop|backblaze-b2-java-api|1.3.0|jar)

## maven setup

No setup is required

## gradle setup

Repository

```
repositories {
	mavenCentral()
}
```

## Dependencies - Gradle

```
dependencies {
	runtime(group: 'synapticloop', name: 'backblaze-b2-java-api', version: '1.3.0', ext: 'jar')

	compile(group: 'synapticloop', name: 'backblaze-b2-java-api', version: '1.3.0', ext: 'jar')
}
```

or, more simply for versions of gradle greater than 2.4

```
dependencies {
	runtime 'synapticloop:backblaze-b2-java-api:1.3.0'

	compile 'synapticloop:backblaze-b2-java-api:1.3.0'
}
```

## Dependencies - Maven

```
<dependency>
	<groupId>synapticloop</groupId>
	<artifactId>backblaze-b2-java-api</artifactId>
	<version>1.3.0</version>
	<type>jar</type>
</dependency>
```

## Other packages


You may either download the files from [https://bintray.com/synapticloop/maven/backblaze-b2-java-api/](https://bintray.com/synapticloop/maven/backblaze-b2-java-api/) or from [https://github.com/synapticloop/backblaze-b2-java-api/releases](https://github.com/synapticloop/backblaze-b2-java-api/releases)

You will also need the dependencies:

### runtime dependencies

  - org.apache.httpcomponents, httpclient, 4.5.1: (It may be available on one of: [bintray](https://bintray.com/org.apache.httpcomponents/maven/httpclient/4.5.1/view#files/org.apache.httpcomponents/httpclient/4.5.1) [mvn central](http://search.maven.org/#artifactdetails|org.apache.httpcomponents|httpclient|4.5.1|jar) [mvn repository](http://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient/4.5.1) )
  - commons-io, commons-io, 2.4: (It may be available on one of: [bintray](https://bintray.com/commons-io/maven/commons-io/2.4/view#files/commons-io/commons-io/2.4) [mvn central](http://search.maven.org/#artifactdetails|commons-io|commons-io|2.4|jar) [mvn repository](http://mvnrepository.com/artifact/commons-io/commons-io/2.4) )
  - org.json, json, 20160212: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20160212/view#files/org.json/json/20160212) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160212|jar) [mvn repository](http://mvnrepository.com/artifact/org.json/json/20160212) )
  - org.slf4j, slf4j-api, 1.7.13: (It may be available on one of: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.13/view#files/org.slf4j/slf4j-api/1.7.13) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.13|jar) [mvn repository](http://mvnrepository.com/artifact/org.slf4j/slf4j-api/1.7.13) )


### compile dependencies

  - org.apache.httpcomponents, httpclient, 4.5.1: (It may be available on one of: [bintray](https://bintray.com/org.apache.httpcomponents/maven/httpclient/4.5.1/view#files/org.apache.httpcomponents/httpclient/4.5.1) [mvn central](http://search.maven.org/#artifactdetails|org.apache.httpcomponents|httpclient|4.5.1|jar) [mvn repository](http://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient/4.5.1) )
  - commons-io, commons-io, 2.4: (It may be available on one of: [bintray](https://bintray.com/commons-io/maven/commons-io/2.4/view#files/commons-io/commons-io/2.4) [mvn central](http://search.maven.org/#artifactdetails|commons-io|commons-io|2.4|jar) [mvn repository](http://mvnrepository.com/artifact/commons-io/commons-io/2.4) )
  - org.json, json, 20160212: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20160212/view#files/org.json/json/20160212) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160212|jar) [mvn repository](http://mvnrepository.com/artifact/org.json/json/20160212) )
  - org.slf4j, slf4j-api, 1.7.13: (It may be available on one of: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.13/view#files/org.slf4j/slf4j-api/1.7.13) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.13|jar) [mvn repository](http://mvnrepository.com/artifact/org.slf4j/slf4j-api/1.7.13) )



**NOTE:** You may need to download any dependencies of the above dependencies in turn

# License

```
The MIT License (MIT)

Copyright (c) 2016 synapticloop

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```


--

> `This README.md file was hand-crafted with care utilising synapticloop`[`templar`](https://github.com/synapticloop/templar/)`->`[`documentr`](https://github.com/synapticloop/documentr/)

--

