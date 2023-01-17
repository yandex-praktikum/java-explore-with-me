#FROM amazoncorretto:11-alpine-jdk
FROM amazoncorretto:11-al2-jdk
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]