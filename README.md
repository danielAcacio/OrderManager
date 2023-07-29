# OrderManager

The application was built with **Spring Boot 2.7.14** and designed to run with **JDK 8**, specifically in the system building process I used **JDK-8u202**.

Maven was used as dependency manager and system packager and below is the list of application dependencies:

- com.h2database:h2
- org.postgresql:postgresql42.2.26
- org.projectlombok:lombok
- org.springdoc:springdoc-openapi-ui1.6.12
- org.springframework.boot:spring-boot-maven-plugin
- org.springframework.boot:spring-boot-starter-aop
- org.springframework.boot:spring-boot-starter-data-jpa
- org.springframework.boot:spring-boot-starter-mail
- org.springframework.boot:spring-boot-starter-test
- org.springframework.boot:spring-boot-starter-validation
- org.springframework.boot:spring-boot-starter-web

To run the application, it will be necessary to configure some system variables, below is the list of necessary variables and a brief description:


- **DATABASE_URL**           - Connection string used to access the database.
- **DATABASE_LOGIN**         - User login used to access the database
- **DATABASE_PASSWORD**      - User password to access the database
- **EMAIL_USER_NAME**        - User email used to connect to the smtp server
- **EMAIL_USER_PASSWORD**    - User password for smtp server access
- **EMAIL_SERVER**           - Address of the smtp server
- **LOG_PATH**               - Path where the application logs will be deposited



These environment variables are referenced in Spring's **application.yml** and must be set in the development IDE or on your desktop if you want to run the standalone application.

The application has a simple Dockerfile that, if you want, you can build an image, below is the command example to build the image:


**docker build --build-arg DATABASE_URL_PARAM=<DATABASE_URL> --build-arg DATABASE_LOGIN_PARAM=<DATABASE_USER_LOGIN> --build-arg DATABASE_PASSWORD_PARAM=<DATABASE_USER_PASSWORD> --build-arg EMAIL_USER_NAME=<USER_EMAIL> --build-arg EMAIL_USER_PASS WORD_PARAM= <USER_EMAIL_PASSWORD> --build-arg EMAIL_SERVER_PARAM=<EMAIL_SERVER> --build-arg LOG_PATH_PATH=<LOG_PATH> --no-cache <DOCKERFILE_PATH>**


To run the image, you can run a command similar to the one listed below:

**docker run -p8080:8080 --network=host <GENERATED_IMAGE_ID>**

The application has been configured so that, when started, a database construction script is executed, so once you have correctly configured the database and created the environment variables, the application itself will build the tables, so you can verify DDL in the **schema.sql** file present in the **application's resources folder**.


The application has api documentation provided by **Swagger** that can be accessed at:

**http://localhost:8080/swagger-ui/index.html**

To use the endpoints, just start the application and access the aforementioned address.
