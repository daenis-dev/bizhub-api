# BizHub API

### Overview

BizHub provides a free suite of easy to use business tools over the web. The initial product offering allows the user to backup up to ten gigabytes of files.

This repository is used to manage changes to the API codebase. Navigate to the [demo project repository](https://github.com/daenis-dev/checkers-antivirus-demo) for setup instructions, API documentation and additional project notes.



### Running Automated Tests

- Install Java, PostgreSQL and IntelliJ

- Navigate to *src/main/resources*, create a directory named *certs* and generate a self-signed certificate

  ```
  mkdir src/main/resources/certs && cd certs
  
  keytool -genkeypair -alias bizhub-api -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore bizhub-api.p12 -validity 3650 -storepass changeit
  ```

- Create a database in PostgreSQL named *bizhub*. Run the script located at *src/main/resources/sql/schema.sql*

- Open the project within IntelliJ and execute all tests using the following VM options:

  ```
  -Dkeystore-path=/c/Users/dkala/projects/bizhub/bizhub-api/src/main/resources/certs/bizhub-api.p12
  -Dkeystore-password=changeit
  -DSPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/bizhub
  -DSPRING_DATASOURCE_USERNAME=postgres
  -DSPRING_DATASOURCE_PASSWORD=changeitdb
  -DAUTH_ADMIN=NA
  -DAUTH_PASSWORD=NA
  -Dkeycloak-bizhub-uuid=NA
  -Dspring.profiles.active=it
  -Djwt-issuer-uri=NA
  -Dclient-id=NA
  -Dkeycloak-base-url=NA
  -Dkeycloak-bizhub-base-url=NA
  -Dkeycloak-bizhub-login-url=NA
  ```
  
  And Environment Variables:
  
  ```
  SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/bizhub
  SPRING_DATASOURCE_USERNAME:postgres
  SPRING_DATASOURCE_PASSWORD:changeitdb
  ```
  
  