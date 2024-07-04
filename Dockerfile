FROM openjdk:8-jre-slim
MAINTAINER xuxueli

EXPOSE 8080

ENV TZ=PRC

#ADD target/jar-0.0.1-SNAPSHOT.jar /app.jar
COPY jar-0.0.1-SNAPSHOT.jar /app.jar
#ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=dev"]
