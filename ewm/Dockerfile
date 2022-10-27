FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar ewm.jar
ENTRYPOINT ["java","-jar","/ewm.jar"]