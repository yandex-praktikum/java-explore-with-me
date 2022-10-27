FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar statistics.jar
ENTRYPOINT ["java","-jar","/statistics.jar"]