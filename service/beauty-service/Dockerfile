FROM java:8-jre
MAINTAINER Alexander Lukyanchikov <sqshq@sqshq.com>

ADD ./target/beauty-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/beauty-service.jar"]

EXPOSE 7000