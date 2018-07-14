FROM 192.168.0.220:6789/openjdk:8-jdk-alpine
LABEL maintainer="KLR <prefix614@gmail.com>"

VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
