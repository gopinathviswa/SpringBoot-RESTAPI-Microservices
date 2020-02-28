FROM openjdk:8-alpine
ADD target/town-service.jar town-service.jar
EXPOSE 8100
ENTRYPOINT ["java","-jar","town-service.jar"] 