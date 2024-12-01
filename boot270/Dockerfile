FROM openjdk:8-jre

WORKDIR /data
# 这里修改成你的项目路径
COPY boot270/target/*.jar /data/app.jar
EXPOSE 9000

# >>>>MacOS环境Docker运行
# COPY boot270/target/*.jar /Users/data/app.jar
#COPY ./target/*.jar /Users/tmp/app.jar
#EXPOSE 10000

ENV TZ "Asia/Shanghai"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime
ENTRYPOINT ["java","-jar","/data/app.jar"]

