FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar statistics-server.jar
ENTRYPOINT ["java","-jar","statistics-server.jar"]