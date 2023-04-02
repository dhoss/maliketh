FROM maven:3.9.0-amazoncorretto-19 as builder
WORKDIR application
COPY . .
RUN mvn clean install -DskipTests
COPY target/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM amazoncorretto:20.0.0-alpine
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "--enable-preview", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "org.springframework.boot.loader.JarLauncher"]
EXPOSE 8080