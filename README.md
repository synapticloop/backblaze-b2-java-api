A Java API for the truly excellent backblaze B2 storage

Usable, however still a work in progress... (YMMV)


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
      <version>v1.0.0</version>
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

    runtime(group: 'synapticloop', name: 'backblaze-b2-java-api', version: 'v1.0.0', ext: 'jar')

    compile(group: 'synapticloop', name: 'backblaze-b2-java-api', version: 'v1.0.0', ext: 'jar')
 
or 

    runtime 'synapticloop:backblaze-b2-java-api:v1.0.0'

    compile 'synapticloop:backblaze-b2-java-api:v1.0.0'
    
## Other

You may either download the files from [https://bintray.com/synapticloop/maven/backblaze-b2-java-api/](https://bintray.com/synapticloop/maven/backblaze-b2-java-api/) or from [https://github.com/synapticloop/backblaze-b2-java-api/releases](https://github.com/synapticloop/backblaze-b2-java-api/releases)

You will also need the dependencies:

 - runtime 'org.apache.httpcomponents:httpclient:4.3.4'
 - runtime 'commons-io:commons-io:2.4'
 - runtime 'org.json:json:20090211'
 - runtime 'org.slf4j:slf4j-api:1.7.13'

which can be found by searching here: [http://mvnrepository.com/](http://mvnrepository.com/)

# Running the Tests

`gradled --info test`

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