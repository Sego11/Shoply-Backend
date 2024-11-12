FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
COPY --from=build target/shoply-app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

#FROM openjdk:21-jdk
#WORKDIR /app
#COPY target/shoply-app.jar app.jar
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]

