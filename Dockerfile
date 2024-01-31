FROM openjdk:17-jdk-alpine
RUN addgroup -S app && adduser -S app -G app
USER app
WORKDIR /app
COPY . .
RUN ./mvnw clean install
ENTRYPOINT ["java", "-jar" , "/app/target/communication-1.jar"]
