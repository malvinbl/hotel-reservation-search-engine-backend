FROM openjdk:21-jdk AS build

WORKDIR /app

COPY mvnw ./
COPY .mvn/ .mvn/

RUN chmod +x mvnw

COPY pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
