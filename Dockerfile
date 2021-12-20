#
# Build stage
#
FROM maven:3.8.4-openjdk-17 AS build
# Download dependencies
RUN mkdir /home/app
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml verify clean --fail-never
# Compile code
COPY src /home/app/src
RUN mvn -f /home/app/pom.xml package

#
# Package stage
#
FROM openjdk:17-alpine
COPY --from=build /home/app/target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/application.jar"]
