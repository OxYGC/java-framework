FROM java:8

WORKDIR /data
COPY boot270/target/*.jar /data/apps/app.jar
EXPOSE 9000
ENV TZ "Asia/Shanghai"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime
ENTRYPOINT ["java","-jar","/data/apps/app.jar"]

