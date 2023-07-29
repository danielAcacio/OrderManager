FROM maven:3.5-jdk-8-alpine as order_manager

WORKDIR /app
COPY . .

RUN mvn package

ARG DATABASE_URL_PARAM
ARG DATABASE_LOGIN_PARAM
ARG DATABASE_PASSWORD_PARAM
ARG EMAIL_USER_NAME
ARG EMAIL_USER_PASSWORD_PARAM
ARG EMAIL_SERVER_PARAM
ARG LOG_PATH_PARAM


ENV DATABASE_URL=${DATABASE_URL_PARAM}
ENV DATABASE_LOGIN=${DATABASE_LOGIN_PARAM}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD_PARAM}
ENV EMAIL_USER_NAME=${EMAIL_USER_NAME}
ENV EMAIL_USER_PASSWORD=${EMAIL_USER_PASSWORD_PARAM}
ENV EMAIL_SERVER=${EMAIL_SERVER_PARAM}
ENV LOG_PATH=${LOG_PATH_PARAM}

EXPOSE 8080
ENTRYPOINT java -jar /app/target/OrderManager-0.0.1-SNAPSHOT.jar
