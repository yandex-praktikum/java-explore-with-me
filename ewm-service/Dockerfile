FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]