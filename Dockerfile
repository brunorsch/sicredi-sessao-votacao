FROM openjdk:21-jdk

COPY build/libs/sicredi-sessao-votacao-1.0.0.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]