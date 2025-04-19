FROM amazoncorretto:17-alpine3.21
COPY *.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
