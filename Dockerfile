FROM amazoncorretto:17-alpine-3.20
COPY *.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
