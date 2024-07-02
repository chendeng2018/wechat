FROM openjdk:8-jre-slim
MAINTAINER xuxueli

ENV PARAMS=""

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD target/wechat.jar /app.jar

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILE_ACTIVE /app.jar $PARAMS"]
