FROM openjdk:8-jdk-alpine
RUN addgroup -S beuhier && adduser -S beuhier -G beuhier
USER beuhier:beuhier
EXPOSE 8081
#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT ["java","-cp","app:app/lib/*","com.example.apigateway.ApiGatewayApplication"]
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
ADD target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]