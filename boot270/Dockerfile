FROM adoptopenjdk/openjdk8:ubi
WORKDIR /home
COPY target/*.jar /home/app.jar
EXPOSE 9000
ENV TZ "Asia/Shanghai"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime
ENTRYPOINT ["java","-jar","/home/app.jar"]