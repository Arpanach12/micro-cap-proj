FROM openjdk:8

COPY ./target/orderingService-*.jar orderingservice.jar

EXPOSE 8081

CMD ["java","-jar","-Dspring.profile.active=local","orderingservice.jar"]