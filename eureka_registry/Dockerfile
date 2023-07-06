FROM openjdk:19-jdk-alpine
LABEL authors="gauti4ru"
COPY ./build/libs /app
ENTRYPOINT ["java","-jar","app/eureka_registry-0.0.1-SNAPSHOT.jar"]
