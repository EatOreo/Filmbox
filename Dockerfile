FROM python:3 AS films
WORKDIR /drive
COPY ["scripts/GetFilms.py", "GetFilms.py"]
COPY ["filmbox/src/main/resources/static/films", "filmbox/src/main/resources/static/films"]
RUN pip install checksumdir
RUN pip install gdown
RUN python GetFilms.py

FROM eclipse-temurin:21-jdk AS build
WORKDIR /src
COPY ["filmbox/", "filmbox/"]
COPY --from=films /drive/filmbox/src/main/resources/static/films filmbox/src/main/resources/static/films
WORKDIR "/src/filmbox"
RUN ./mvnw install

FROM eclipse-temurin:21-jdk-alpine AS publish
WORKDIR /tmp
EXPOSE 8080
COPY --from=build /src/filmbox/target/filmbox.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]