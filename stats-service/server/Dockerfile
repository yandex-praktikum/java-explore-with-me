FROM amazoncorretto:21-alpine as builder
WORKDIR application
COPY target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM amazoncorretto:21-alpine
COPY --from=builder /application/dependencies/ ./
COPY --from=builder /application/spring-boot-loader/ ./
COPY --from=builder /application/snapshot-dependencies/ ./
COPY --from=builder /application/application ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
