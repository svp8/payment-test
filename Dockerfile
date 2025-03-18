FROM bellsoft/liberica-openjdk-alpine:21
VOLUME /tmp
COPY target/payment-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
