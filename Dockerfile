FROM openjdk:8-jre-alpine

MAINTAINER Aléx Carvalho <alex_carvalho@outlook.com.br>

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT java -jar  app.jar