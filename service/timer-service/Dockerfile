FROM java:8-jre
MAINTAINER Alexander Lukyanchikov <sqshq@sqshq.com>

ADD ./target/timer-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/timer-service.jar"]

EXPOSE 7000