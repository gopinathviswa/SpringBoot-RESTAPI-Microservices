FROM openjdk:8-alpine
ADD target/report-service.jar report-service.jar
EXPOSE 8105
ENTRYPOINT ["java","-jar","report-service.jar"] 