FROM openjdk:8-alpine
ADD target/branch-service.jar branch-service.jar
EXPOSE 8101
ENTRYPOINT ["java","-jar","branch-service.jar"] 