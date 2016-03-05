[![Build Status](https://travis-ci.org/synapticloop/backblaze-b2-java-api.svg?branch=master)](https://travis-ci.org/synapticloop/backblaze-b2-java-api) [![Download](https://api.bintray.com/packages/synapticloop/maven/backblaze-b2-java-api/images/download.svg) ](https://bintray.com/synapticloop/maven/backblaze-b2-java-api/_latestVersion)

A Java API for the truly excellent backblaze B2 storage

# Usage

```
String b2AccountId = ""; // your b2 account ID
String b2ApplicationKey = ""; // your b2 application Key

B2ApiClient b2ApiClient = new B2ApiClient(b2AccountId, b2ApplicationKey);

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
B2ApiClient(String, String)

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

# Logging

slf4j is the logging framework used, (with log4j for the tests, a sample `log4j2.xml` is below:

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

# Dependency Management

> Note that the latest version can be found [https://bintray.com/synapticloop/maven/backblaze-b2-java-api/view](https://bintray.com/synapticloop/maven/backblaze-b2-java-api/view)

Include the dependency

## maven

this comes from the jcenter bintray, to set up your repository:

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

And now for the dependency

    <dependency>
      <groupId>synapticloop</groupId>
      <artifactId>backblaze-b2-java-api</artifactId>
      <version>v1.1.3</version>
      <type>jar</type>
    </dependency>
 
 
## gradle

Repository

    repositories {
        maven {
            url  "http://jcenter.bintray.com" 
        }
    }
 
 or just
 
    repositories {
      jcenter()
    }

and then include the dependency:

    runtime(group: 'synapticloop', name: 'backblaze-b2-java-api', version: 'v1.1.3', ext: 'jar')

    compile(group: 'synapticloop', name: 'backblaze-b2-java-api', version: 'v1.1.3', ext: 'jar')
 
or, more simply for later versions of gradle

    runtime 'synapticloop:backblaze-b2-java-api:v1.1.3'

    compile 'synapticloop:backblaze-b2-java-api:v1.1.3'
    
## Other packages

You may either download the files from [https://bintray.com/synapticloop/maven/backblaze-b2-java-api/](https://bintray.com/synapticloop/maven/backblaze-b2-java-api/) or from [https://github.com/synapticloop/backblaze-b2-java-api/releases](https://github.com/synapticloop/backblaze-b2-java-api/releases)

You will also need the dependencies:

 - runtime 'org.apache.httpcomponents:httpclient:4.3.4'
 - runtime 'commons-io:commons-io:2.4'
 - runtime 'org.json:json:20090211'
 - runtime 'org.slf4j:slf4j-api:1.7.13'

which can be found by searching here: [http://mvnrepository.com/](http://mvnrepository.com/) (and you may need to download their dependencies in turn)

# Building the Package

`gradlew build`

this will output the artefacts into the `build/libs/` directory.

Note that this will also run all of the tests (see notes below)

# Running the Tests

`gradle --info test`

Which will also print out the logging

if you do not have gradle installed, try:

`gradlew --info test`

**!!!   IMPORTANT   !!!** 

You **MUST** have the following environment variables set:

```
export B2_ACCOUNT_ID="your-account-id"
export B2_APPLICATION_KEY="your-application-key"
```

**WARNING:** These tests make API calls against your account which contribute to your call limits, which may lead to a cost.

# License

```
The MIT License (MIT)

Copyright (c) 2015 Synapticloop

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