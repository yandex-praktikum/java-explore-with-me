FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar main-service.jar
ENTRYPOINT ["java","-jar","/main-service.jar"]