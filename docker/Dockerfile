FROM openjdk:21-slim

WORKDIR /app

COPY zhiban-clock.jar ./app.jar

ENV ACTIVE=prod

EXPOSE 8088

CMD ["java", "-jar", "-Dspring.profiles.active=${ACTIVE}", "./app.jar"]
