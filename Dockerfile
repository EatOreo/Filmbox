FROM eclipse-temurin:21-jdk AS build
WORKDIR /src
COPY ["filmbox/", "filmbox/"]
WORKDIR "/src/filmbox"
RUN ./mvnw install

FROM eclipse-temurin:21-jdk-alpine AS publish
WORKDIR /tmp
EXPOSE 8080
COPY --from=build /src/filmbox/target/filmbox.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]