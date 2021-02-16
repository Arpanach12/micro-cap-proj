FROM openjdk:8

COPY ./target/config-server-*.jar config-server.jar

EXPOSE 8084

CMD ["java","-jar","-Dspring.profile.active=local","config-server.jar"]