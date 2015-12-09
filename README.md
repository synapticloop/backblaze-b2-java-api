A Java API for the truly excellent backblaze B2 storage

Usable, however still a work in progress... (YMMV)

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