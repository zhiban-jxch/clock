FROM openjdk:21-slim

WORKDIR /app

COPY ./target/zhiban-clock.jar ./app.jar
COPY ./data ./data

ENV ACTIVE=prod
ENV DATA_DIR=/app/data

EXPOSE 8088

CMD ["java", "-jar", "-Dspring.profiles.active=${ACTIVE}", "-Ddata-dir=${DATA_DIR}", "./app.jar"]
