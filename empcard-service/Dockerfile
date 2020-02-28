FROM openjdk:8-alpine
ADD target/empcard-service.jar empcard-service.jar
EXPOSE 8102
ENTRYPOINT ["java","-jar","empcard-service.jar"] 