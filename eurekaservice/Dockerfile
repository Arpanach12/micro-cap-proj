FROM openjdk:8

COPY ./target/eurekaservice-*.jar eurekaservice.jar

EXPOSE 8761

CMD ["java","-jar","-Dspring.profile.active=local","eurekaservice.jar"]