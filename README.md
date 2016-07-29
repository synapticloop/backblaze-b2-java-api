[![Build Status](https://travis-ci.org/synapticloop/backblaze-b2-java-api.svg?branch=master)](https://travis-ci.org/synapticloop/backblaze-b2-java-api) [![Download](https://api.bintray.com/packages/synapticloop/maven/backblaze-b2-java-api/images/download.svg)](https://bintray.com/synapticloop/maven/backblaze-b2-java-api/_latestVersion) [![GitHub Release](https://img.shields.io/github/release/synapticloop/backblaze-b2-java-api.svg)](https://github.com/synapticloop/backblaze-b2-java-api/releases) 

# backblaze-b2-java-api



> A java api for the truly excellent backblaze b2 storage service


# Just looking for a GUI?

We thoroughly recommend either [cyberduck](https://cyberduck.io/) or [mountainduck](https://mountainduck.io/) which includes this code for the awesome BackBlaze storage service.
# Usage

```
// required imports
import synapticloop.b2.B2ApiClient;
import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.request.*;
import synapticloop.b2.response.*;


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

# Logging - slf4j

slf4j is the logging framework used for this project.  In order to set up a logging framework with this project, sample configurations are below:

## Log4j


You will need to include dependencies for this - note that the versions may need to be updated.

### Maven

```
<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-slf4j-impl</artifactId>
	<version>2.5</version>
	<scope>runtime</scope>
</dependency>

<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-core</artifactId>
	<version>2.5</version>
	<scope>runtime</scope>
</dependency>

```

### Gradle &lt; 2.1

```
dependencies {
	...
	runtime(group: 'org.apache.logging.log4j', name: 'log4j-slf4j-impl', version: '2.5', ext: 'jar')
	runtime(group: 'org.apache.logging.log4j', name: 'log4j-core', version: '2.5', ext: 'jar')
	...
}
```
### Gradle &gt;= 2.1

```
dependencies {
	...
	runtime 'org.apache.logging.log4j:log4j-slf4j-impl:2.5'
	runtime 'org.apache.logging.log4j:log4j-core:2.5'
	...
}
```


### Setting up the logging:

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

# Artefact Publishing - Bintray

This project publishes artefacts to [bintray](https://bintray.com/)

> Note that the latest version can be found [https://bintray.com/synapticloop/maven/backblaze-b2-java-api/view](https://bintray.com/synapticloop/maven/backblaze-b2-java-api/view)

## maven setup

this comes from the jcenter bintray, to set up your repository:

```
<?xml version="1.0" encoding="UTF-8" ?>
<settings xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd' xmlns='http://maven.apache.org/SETTINGS/1.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
  <profiles>
    <profile>
      <repositories>
        <repository>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
          <id>central</id>
          <name>bintray</name>
          <url>http://jcenter.bintray.com</url>
        </repository>
      </repositories>
      <pluginRepositories>
        <pluginRepository>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
          <id>central</id>
          <name>bintray-plugins</name>
          <url>http://jcenter.bintray.com</url>
        </pluginRepository>
      </pluginRepositories>
      <id>bintray</id>
    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>bintray</activeProfile>
  </activeProfiles>
</settings>
```

## gradle setup

Repository

```
repositories {
	maven {
		url  "http://jcenter.bintray.com" 
	}
}
```

or just

```
repositories {
	jcenter()
}
```

## Dependencies - Gradle

```
dependencies {
	runtime(group: 'synapticloop', name: 'backblaze-b2-java-api', version: '2.0.1', ext: 'jar')

	compile(group: 'synapticloop', name: 'backblaze-b2-java-api', version: '2.0.1', ext: 'jar')
}
```

or, more simply for versions of gradle greater than 2.1

```
dependencies {
	runtime 'synapticloop:backblaze-b2-java-api:2.0.1'

	compile 'synapticloop:backblaze-b2-java-api:2.0.1'
}
```

## Dependencies - Maven

```
<dependency>
	<groupId>synapticloop</groupId>
	<artifactId>backblaze-b2-java-api</artifactId>
	<version>2.0.1</version>
	<type>jar</type>
</dependency>
```

## Dependencies - Downloads


You will also need to download the following dependencies:



### cobertura dependencies

  - net.sourceforge.cobertura:cobertura:2.1.1: (It may be available on one of: [bintray](https://bintray.com/net.sourceforge.cobertura/maven/cobertura/2.1.1/view#files/net.sourceforge.cobertura/cobertura/2.1.1) [mvn central](http://search.maven.org/#artifactdetails|net.sourceforge.cobertura|cobertura|2.1.1|jar))


### compile dependencies

  - org.apache.httpcomponents:httpclient:4.5.1: (It may be available on one of: [bintray](https://bintray.com/org.apache.httpcomponents/maven/httpclient/4.5.1/view#files/org.apache.httpcomponents/httpclient/4.5.1) [mvn central](http://search.maven.org/#artifactdetails|org.apache.httpcomponents|httpclient|4.5.1|jar))
  - commons-io:commons-io:2.4: (It may be available on one of: [bintray](https://bintray.com/commons-io/maven/commons-io/2.4/view#files/commons-io/commons-io/2.4) [mvn central](http://search.maven.org/#artifactdetails|commons-io|commons-io|2.4|jar))
  - org.json:json:20160212: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20160212/view#files/org.json/json/20160212) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160212|jar))
  - org.slf4j:slf4j-api:1.7.13: (It may be available on one of: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.13/view#files/org.slf4j/slf4j-api/1.7.13) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.13|jar))


### runtime dependencies

  - org.apache.httpcomponents:httpclient:4.5.1: (It may be available on one of: [bintray](https://bintray.com/org.apache.httpcomponents/maven/httpclient/4.5.1/view#files/org.apache.httpcomponents/httpclient/4.5.1) [mvn central](http://search.maven.org/#artifactdetails|org.apache.httpcomponents|httpclient|4.5.1|jar))
  - commons-io:commons-io:2.4: (It may be available on one of: [bintray](https://bintray.com/commons-io/maven/commons-io/2.4/view#files/commons-io/commons-io/2.4) [mvn central](http://search.maven.org/#artifactdetails|commons-io|commons-io|2.4|jar))
  - org.json:json:20160212: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20160212/view#files/org.json/json/20160212) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160212|jar))
  - org.slf4j:slf4j-api:1.7.13: (It may be available on one of: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.13/view#files/org.slf4j/slf4j-api/1.7.13) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.13|jar))


### testCompile dependencies

  - junit:junit:4.12: (It may be available on one of: [bintray](https://bintray.com/junit/maven/junit/4.12/view#files/junit/junit/4.12) [mvn central](http://search.maven.org/#artifactdetails|junit|junit|4.12|jar))
  - org.apache.logging.log4j:log4j-slf4j-impl:2.5: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-slf4j-impl/2.5/view#files/org.apache.logging.log4j/log4j-slf4j-impl/2.5) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-slf4j-impl|2.5|jar))
  - org.apache.logging.log4j:log4j-core:2.5: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-core/2.5/view#files/org.apache.logging.log4j/log4j-core/2.5) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-core|2.5|jar))
  - org.json:json:20160212: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20160212/view#files/org.json/json/20160212) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160212|jar))


### testRuntime dependencies

  - junit:junit:4.12: (It may be available on one of: [bintray](https://bintray.com/junit/maven/junit/4.12/view#files/junit/junit/4.12) [mvn central](http://search.maven.org/#artifactdetails|junit|junit|4.12|jar))
  - org.apache.logging.log4j:log4j-slf4j-impl:2.5: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-slf4j-impl/2.5/view#files/org.apache.logging.log4j/log4j-slf4j-impl/2.5) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-slf4j-impl|2.5|jar))
  - org.apache.logging.log4j:log4j-core:2.5: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-core/2.5/view#files/org.apache.logging.log4j/log4j-core/2.5) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-core|2.5|jar))
  - org.json:json:20160212: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20160212/view#files/org.json/json/20160212) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160212|jar))

**NOTE:** You may need to download any dependencies of the above dependencies in turn (i.e. the transitive dependencies)

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

