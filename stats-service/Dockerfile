FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar app.jar
WORKDIR /java
ENTRYPOINT ["java","-jar","/app.jar"]