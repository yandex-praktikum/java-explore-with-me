FROM eclipse-temurin:21-jre-jammy
VOLUME /tmp
ARG JAR_FILE=target/statservice-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} statservice.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /statservice.jar"]