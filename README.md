 <a name="#documentr_top"></a>[![Build Status](https://travis-ci.org/synapticloop/backblaze-b2-java-api.svg?branch=master)](https://travis-ci.org/synapticloop/backblaze-b2-java-api) [![Download](https://api.bintray.com/packages/synapticloop/maven/backblaze-b2-java-api/images/download.svg)](https://bintray.com/synapticloop/maven/backblaze-b2-java-api/_latestVersion) [![GitHub Release](https://img.shields.io/github/release/synapticloop/backblaze-b2-java-api.svg)](https://github.com/synapticloop/backblaze-b2-java-api/releases) 



<a name="documentr_heading_0"></a>

# backblaze-b2-java-api <sup><sup>[top](#documentr_top)</sup></sup>



> A java api for the truly excellent backblaze b2 storage service






<a name="documentr_heading_1"></a>

# Table of Contents <sup><sup>[top](#documentr_top)</sup></sup>



 - [backblaze-b2-java-api](#documentr_heading_0)
 - [Table of Contents](#documentr_heading_1)
 - [Usage](#documentr_heading_2)
   - [Large File Support](#documentr_heading_3)
 - [Building the Package](#documentr_heading_4)
   - [*NIX/Mac OS X](#documentr_heading_5)
   - [Windows](#documentr_heading_6)
 - [Running the Tests](#documentr_heading_7)
   - [*NIX/Mac OS X](#documentr_heading_8)
   - [Windows](#documentr_heading_9)
 - [Logging - slf4j](#documentr_heading_10)
   - [Log4j](#documentr_heading_11)
 - [Artefact Publishing - Github](#documentr_heading_16)
 - [Artefact Publishing - Bintray](#documentr_heading_17)
   - [maven setup](#documentr_heading_18)
   - [gradle setup](#documentr_heading_19)
   - [Dependencies - Gradle](#documentr_heading_20)
   - [Dependencies - Maven](#documentr_heading_21)
   - [Dependencies - Downloads](#documentr_heading_22)
 - [License](#documentr_heading_28)


# Just looking for a GUI?

We thoroughly recommend either [cyberduck](https://cyberduck.io/) or [mountainduck](https://mountainduck.io/) which includes this code for the awesome BackBlaze storage service.


<a name="documentr_heading_2"></a>

# Usage <sup><sup>[top](#documentr_top)</sup></sup>



```


package synapticloop.b2;

import java.io.File;
import java.io.IOException;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2BucketResponse;

public class QuickExampleMain {

	public static void main(String[] args) throws B2ApiException, IOException {
		String b2AccountId = ""; // your b2 account ID
		String b2ApplicationKey = ""; // your b2 application Key

		try {
			B2ApiClient b2ApiClient = new B2ApiClient();
			b2ApiClient.authenticate(b2AccountId, b2ApplicationKey);

			// now create a private bucket
			B2BucketResponse createPrivateBucket = b2ApiClient.createBucket("super-secret-bucket" , BucketType.allPrivate);

			// or a public one
			B2BucketResponse createPublicBucket = b2ApiClient.createBucket("everyone-has-access" , BucketType.allPublic);

			// upload a file to the private bucket
			b2ApiClient.uploadFile(createPrivateBucket.getBucketId(), "myfile.txt", new File("/tmp/temporary-file.txt"));

		} catch(B2ApiException | IOException ex) {
			ex.printStackTrace();
		}
	}

}

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






<a name="documentr_heading_3"></a>

## Large File Support <sup><sup>[top](#documentr_top)</sup></sup>

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







<a name="documentr_heading_4"></a>

# Building the Package <sup><sup>[top](#documentr_top)</sup></sup>



<a name="documentr_heading_5"></a>

## *NIX/Mac OS X <sup><sup>[top](#documentr_top)</sup></sup>

From the root of the project, simply run

`./gradlew build`




<a name="documentr_heading_6"></a>

## Windows <sup><sup>[top](#documentr_top)</sup></sup>

`./gradlew.bat build`


This will compile and assemble the artefacts into the `build/libs/` directory.

Note that this may also run tests (if applicable see the Testing notes)



<a name="documentr_heading_7"></a>

# Running the Tests <sup><sup>[top](#documentr_top)</sup></sup>



<a name="documentr_heading_8"></a>

## *NIX/Mac OS X <sup><sup>[top](#documentr_top)</sup></sup>

From the root of the project, simply run

`gradle --info test`

if you do not have gradle installed, try:

`gradlew --info test`



<a name="documentr_heading_9"></a>

## Windows <sup><sup>[top](#documentr_top)</sup></sup>

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





<a name="documentr_heading_10"></a>

# Logging - slf4j <sup><sup>[top](#documentr_top)</sup></sup>

slf4j is the logging framework used for this project.  In order to set up a logging framework with this project, sample configurations are below:



<a name="documentr_heading_11"></a>

## Log4j <sup><sup>[top](#documentr_top)</sup></sup>


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





<a name="documentr_heading_16"></a>

# Artefact Publishing - Github <sup><sup>[top](#documentr_top)</sup></sup>

This project publishes artefacts to [GitHub](https://github.com/)

> Note that the latest version can be found [https://github.com/synapticloop/backblaze-b2-java-api/releases](https://github.com/synapticloop/backblaze-b2-java-api/releases)

As such, this is not a repository, but a location to download files from.



<a name="documentr_heading_17"></a>

# Artefact Publishing - Bintray <sup><sup>[top](#documentr_top)</sup></sup>

This project publishes artefacts to [bintray](https://bintray.com/)

> Note that the latest version can be found [https://bintray.com/synapticloop/maven/backblaze-b2-java-api/view](https://bintray.com/synapticloop/maven/backblaze-b2-java-api/view)



<a name="documentr_heading_18"></a>

## maven setup <sup><sup>[top](#documentr_top)</sup></sup>

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





<a name="documentr_heading_19"></a>

## gradle setup <sup><sup>[top](#documentr_top)</sup></sup>

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





<a name="documentr_heading_20"></a>

## Dependencies - Gradle <sup><sup>[top](#documentr_top)</sup></sup>



```
dependencies {
	runtime(group: 'synapticloop', name: 'backblaze-b2-java-api', version: '2.2.1', ext: 'jar')

	compile(group: 'synapticloop', name: 'backblaze-b2-java-api', version: '2.2.1', ext: 'jar')
}
```



or, more simply for versions of gradle greater than 2.1



```
dependencies {
	runtime 'synapticloop:backblaze-b2-java-api:2.2.1'

	compile 'synapticloop:backblaze-b2-java-api:2.2.1'
}
```





<a name="documentr_heading_21"></a>

## Dependencies - Maven <sup><sup>[top](#documentr_top)</sup></sup>



```
<dependency>
	<groupId>synapticloop</groupId>
	<artifactId>backblaze-b2-java-api</artifactId>
	<version>2.2.1</version>
	<type>jar</type>
</dependency>
```





<a name="documentr_heading_22"></a>

## Dependencies - Downloads <sup><sup>[top](#documentr_top)</sup></sup>


You will also need to download the following dependencies:



### cobertura dependencies

  - net.sourceforge.cobertura:cobertura:2.1.1: (It may be available on one of: [bintray](https://bintray.com/net.sourceforge.cobertura/maven/cobertura/2.1.1/view#files/net.sourceforge.cobertura/cobertura/2.1.1) [mvn central](http://search.maven.org/#artifactdetails|net.sourceforge.cobertura|cobertura|2.1.1|jar))


### compile dependencies

  - org.apache.httpcomponents:httpclient:4.5.2: (It may be available on one of: [bintray](https://bintray.com/org.apache.httpcomponents/maven/httpclient/4.5.2/view#files/org.apache.httpcomponents/httpclient/4.5.2) [mvn central](http://search.maven.org/#artifactdetails|org.apache.httpcomponents|httpclient|4.5.2|jar))
  - commons-io:commons-io:2.5: (It may be available on one of: [bintray](https://bintray.com/commons-io/maven/commons-io/2.5/view#files/commons-io/commons-io/2.5) [mvn central](http://search.maven.org/#artifactdetails|commons-io|commons-io|2.5|jar))
  - org.json:json:20160810: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20160810/view#files/org.json/json/20160810) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160810|jar))
  - org.slf4j:slf4j-api:1.7.13: (It may be available on one of: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.13/view#files/org.slf4j/slf4j-api/1.7.13) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.13|jar))


### runtime dependencies

  - org.apache.httpcomponents:httpclient:4.5.2: (It may be available on one of: [bintray](https://bintray.com/org.apache.httpcomponents/maven/httpclient/4.5.2/view#files/org.apache.httpcomponents/httpclient/4.5.2) [mvn central](http://search.maven.org/#artifactdetails|org.apache.httpcomponents|httpclient|4.5.2|jar))
  - commons-io:commons-io:2.5: (It may be available on one of: [bintray](https://bintray.com/commons-io/maven/commons-io/2.5/view#files/commons-io/commons-io/2.5) [mvn central](http://search.maven.org/#artifactdetails|commons-io|commons-io|2.5|jar))
  - org.json:json:20160810: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20160810/view#files/org.json/json/20160810) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160810|jar))
  - org.slf4j:slf4j-api:1.7.13: (It may be available on one of: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.13/view#files/org.slf4j/slf4j-api/1.7.13) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.13|jar))


### testCompile dependencies

  - junit:junit:4.12: (It may be available on one of: [bintray](https://bintray.com/junit/maven/junit/4.12/view#files/junit/junit/4.12) [mvn central](http://search.maven.org/#artifactdetails|junit|junit|4.12|jar))
  - org.apache.logging.log4j:log4j-slf4j-impl:2.7: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-slf4j-impl/2.7/view#files/org.apache.logging.log4j/log4j-slf4j-impl/2.7) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-slf4j-impl|2.7|jar))
  - org.apache.logging.log4j:log4j-core:2.7: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-core/2.7/view#files/org.apache.logging.log4j/log4j-core/2.7) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-core|2.7|jar))
  - org.json:json:20160810: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20160810/view#files/org.json/json/20160810) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160810|jar))


### testRuntime dependencies

  - junit:junit:4.12: (It may be available on one of: [bintray](https://bintray.com/junit/maven/junit/4.12/view#files/junit/junit/4.12) [mvn central](http://search.maven.org/#artifactdetails|junit|junit|4.12|jar))
  - org.apache.logging.log4j:log4j-slf4j-impl:2.7: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-slf4j-impl/2.7/view#files/org.apache.logging.log4j/log4j-slf4j-impl/2.7) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-slf4j-impl|2.7|jar))
  - org.apache.logging.log4j:log4j-core:2.7: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-core/2.7/view#files/org.apache.logging.log4j/log4j-core/2.7) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-core|2.7|jar))
  - org.json:json:20160810: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20160810/view#files/org.json/json/20160810) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160810|jar))

**NOTE:** You may need to download any dependencies of the above dependencies in turn (i.e. the transitive dependencies)



<a name="documentr_heading_28"></a>

# License <sup><sup>[top](#documentr_top)</sup></sup>



```
The MIT License (MIT)

Copyright (c) 2017 synapticloop

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
