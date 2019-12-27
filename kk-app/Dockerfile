FROM hub.c.163.com/library/java:8-jre

RUN mkdir app

RUN cd app & echo "Asia/Shanghai" > /etc/timezone

WORKDIR /app

ADD target/*.jar ec-app-cust.jar

EXPOSE 8630

ENTRYPOINT ["java", "-server","-Xms256m","-Xmx512m","-jar", "ec-app-cust.jar"]