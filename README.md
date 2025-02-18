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
  -Dserver.ssl.key-store=/c/Users/username/projects/bizhub/bizhub-api/src/main/resources/certs/bizhub-api.p12
  -Dserver.ssl.key-store-password=changeit
  -Dserver.ssl.key-store-type=pkcs12
  -Dserver.ssl.key-alias=bizhub-api
  -Dserver.ssl.key-password=changeit
  -Dhibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
  -Dspring.datasource.url=jdbc:postgresql://localhost:5432/bizhub
  -Dspring.datasource.username=postgres
  -Dspring.datasource.password=changeitdb
  -Dspring.jpa.hibernate.ddl-auto=none
  -Dkeycloak.admin.username=auth-admin
  -Dkeycloak.admin.password=changeit
  -Dkeycloak-admin-client-name=admin-cli
  -Dkeycloak-bizhub-uuid=2fc108f7-7aa3-4848-a875-5b96de059c1d
  -Dspring.profiles.active=it
  -Djwt-issuer-uri=https://keycloak-server:9880/realms/bizhub
  -Djwk-set-uri=https://keycloak-server:9880/realms/bizhub/protocol/openid-connect/certs
  -Dclient-id=NA
  -Dkeycloak-base-url=NA
  -Dkeycloak-bizhub-base-url=NA
  -Dkeycloak-bizhub-login-url=NA
  -Dkeycloak-bizhub-token-schema=NA
  ```
  
  