FROM openjdk:8-jdk-alpine
RUN addgroup -S beuhier && adduser -S beuhier -G beuhier
USER beuhier:beuhier
EXPOSE 8091
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
ADD target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]