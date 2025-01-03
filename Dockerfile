FROM node:21 AS style
WORKDIR /style
COPY ["filmbox/src/main/resources/", "filmbox/src/main/resources/"]
COPY ["tailwind.config.js", "."]
COPY ["input.css", "."]
RUN npm install tailwindcss
RUN npx tailwindcss -i input.css -o output.css

FROM python:3 AS films
RUN pip install --upgrade setuptools
WORKDIR /drive
COPY ["scripts/requirements.txt", "requirements.txt"]
COPY ["scripts/GetFilms.py", "GetFilms.py"]
COPY ["filmbox/src/main/resources/static/films", "filmbox/src/main/resources/static/films"]
RUN pip install -r requirements.txt
RUN python GetFilms.py

FROM eclipse-temurin:21-jdk AS build
WORKDIR /src
COPY ["filmbox/", "filmbox/"]
COPY --from=style /style/output.css filmbox/src/main/resources/static/output.css
COPY --from=films /drive/filmbox/src/main/resources/static/films filmbox/src/main/resources/static/films
WORKDIR "/src/filmbox"
RUN ./mvnw install

FROM eclipse-temurin:21-jdk-alpine AS publish
WORKDIR /tmp
EXPOSE 8080
COPY --from=build /src/filmbox/target/filmbox.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
