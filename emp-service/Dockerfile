FROM openjdk:8-alpine
ADD target/emp-service.jar emp-service.jar
EXPOSE 8104
ENTRYPOINT ["java","-jar","emp-service.jar"] 