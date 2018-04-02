FROM java:8-jre
MAINTAINER Alexander Lukyanchikov <sqshq@sqshq.com>

ADD ./target/system-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/system-service.jar"]

EXPOSE 7000