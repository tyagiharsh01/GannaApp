FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/PurchiApp-0.0.1-SNAPSHOT.jar .
EXPOSE 8082
CMD ["java", "-jar", "PurchiApp-0.0.1-SNAPSHOT.jar"]
