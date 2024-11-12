FROM openjdk:17-oracle

WORKDIR /app

COPY target/*.jar /app/app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/app/app.jar"]