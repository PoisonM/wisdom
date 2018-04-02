FROM java:8-jre
MAINTAINER Alexander Lukyanchikov <sqshq@sqshq.com>

ADD ./target/weixin-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/weixin-service.jar"]

EXPOSE 7000