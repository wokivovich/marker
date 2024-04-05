FROM openjdk:17-oracle
ARG JARFILE=target/*.jar
COPY ${JARFILE} /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]