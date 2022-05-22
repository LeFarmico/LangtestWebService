FROM openjdk:11
COPY /target/spring-jwt-web-service-0.0.1-SNAPSHOT.jar /usr/src/myapp/
WORKDIR /usr/src/myapp
EXPOSE 5000
CMD ["java", "-jar", "spring-jwt-web-service-0.0.1-SNAPSHOT.jar"]
