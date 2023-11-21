FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/server-0.0.1-SNAPSHOT.jar"]