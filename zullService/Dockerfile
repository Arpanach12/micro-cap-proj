FROM openjdk:8

COPY ./target/zullService-*.jar zullService.jar

EXPOSE 8089

CMD ["java","-jar","-Dspring.profile.active=local","zullService.jar"]