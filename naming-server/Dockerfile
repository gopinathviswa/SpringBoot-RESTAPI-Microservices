FROM openjdk:8-alpine
ADD target/naming-service.jar naming-service.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","naming-service.jar"] 