FROM openjdk:11.0.10-slim
RUN mkdir /opt/application
COPY target/phone-reservation-*-SNAPSHOT.jar /opt/application/application.jar
WORKDIR /opt/application
ENTRYPOINT ["java", "-jar", "application.jar"]
