FROM openjdk:8-jdk-alpine
MAINTAINER Nitish Srivastava
VOLUME /tmp
EXPOSE 9090
ADD target/*.jar app.jar
ENTRYPOINT ["sh","-c","java -jar /app.jar"]