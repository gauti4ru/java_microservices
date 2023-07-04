FROM openjdk:19-jdk-alpine
COPY ./build/libs /app
COPY ./src/main/resources/server_cert.p12  /app
ENTRYPOINT ["java","-jar","app/CA_Cert-0.0.1-SNAPSHOT.jar"]
