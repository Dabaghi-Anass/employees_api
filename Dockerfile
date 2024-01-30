FROM openjdk:17-jdk-alpine
RUN addgroup -S app && adduser -S app -G app
USER spring
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar" , "/app.jar"]