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

- Create a database in PostgreSQL named *checkers*. Run the script located at *src/main/resources/sql/schema.sql*

- Open the project within IntelliJ and execute all tests using the following VM options:

  ```
  -Dkeystore-path=/c/Users/dkala/projects/checkers-antivirus/bizhub-api/src/main/resources/certs/bizhub-api.p12
  -Dkeystore-password=changeit
  -Ddatabase-url=jdbc:postgresql://localhost:5432/bizhub
  -Ddatabase-username=postgres
  -Ddatabase-password=changeitdb
  -Dkeycloak-admin-username=NA
  -Dkeycloak-admin-password=NA
  -Dkeycloak-checkers-uuid=NA
  -Dspring.profiles.active=it
```
  
  
