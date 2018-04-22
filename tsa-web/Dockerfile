FROM openjdk:8-jdk-alpine
ADD build/libs/tsa-web-1.0.0.jar tsa-web-1.0.0.jar
ENTRYPOINT ["java", "-jar", "/tsa-web-1.0.0.jar"]
EXPOSE 8080