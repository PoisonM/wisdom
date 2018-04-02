FROM java:8-jre
MAINTAINER Alexander Lukyanchikov <sqshq@sqshq.com>

ADD ./target/wisdom-project.jar D:\dock\
CMD ["java", "-Xmx200m", "-jar", "D:\dock\wisdom-project.jar"]

EXPOSE 6000