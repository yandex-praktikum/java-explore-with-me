FROM amazoncorretto:11-alpine-jdk
# ENV TZ="Europe/Moscow"
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]