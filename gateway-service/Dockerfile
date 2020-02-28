FROM openjdk:8-alpine
ADD target/gateway-service.jar gateway-service.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","gateway-service.jar"] 