FROM amazoncorretto:11-alpine-jdk
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8988
COPY target/*.jar server.jar
ENTRYPOINT ["java","-jar","/server.jar"]