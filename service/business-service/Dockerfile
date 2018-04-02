FROM java:8-jre
MAINTAINER Alexander Lukyanchikov <sqshq@sqshq.com>

ADD ./target/business-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/business-service.jar"]

EXPOSE 7000