FROM java:8-jre
MAINTAINER Alexander Lukyanchikov <sqshq@sqshq.com>

ADD ./target/user-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/data-service.jar"]

EXPOSE 7000
