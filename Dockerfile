FROM openjdk:21-jdk
WORKDIR /app
COPY target/shoply-app.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]