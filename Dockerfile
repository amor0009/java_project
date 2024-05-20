FROM maven AS build

WORKDIR /app
COPY ./pom.xml ./

RUN mvn dependency:go-offline -DexcludeArtifactIds=maven-checkstyle-plugin

COPY ./src ./src

RUN mvn clean install -DskipTests -Pdocker

FROM openjdk:21-jdk-slim AS RUN

WORKDIR /app

COPY --from=build /app/target/*.jar /app/mangaProject.jar

EXPOSE 8080

CMD ["java", "-jar", "mangaProject.jar"]