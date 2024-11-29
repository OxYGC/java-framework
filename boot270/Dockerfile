FROM openjdk:8-jre

WORKDIR /data
# 这里修改成你的项目路径
COPY boot270/target/*.jar /data/app.jar
#暴露容器的端口
EXPOSE 9000
ENV TZ "Asia/Shanghai"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime
ENTRYPOINT ["java","-jar","/data/app.jar"]

