FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw -q -DskipTests dependency:go-offline

COPY src ./src
RUN ./mvnw -q -DskipTests clean package

EXPOSE 8081

ENTRYPOINT ["java","-jar","target/springbootdemo-0.0.1-SNAPSHOT.jar","--spring.profiles.active=docker"]
