FROM openjdk:8

COPY ./target/reviewservice-*.jar reviewservice.jar

EXPOSE 8085

CMD ["java","-jar","-Dspring.profile.active=local","reviewservice.jar"]