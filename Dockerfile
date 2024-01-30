FROM openjdk:17-jdk-alpine
RUN addgroup -S app && adduser -S spring -G spring
USER spring
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar" , "/app.jar"]