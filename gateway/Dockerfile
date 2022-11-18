FROM adoptopenjdk/openjdk8:ubi
WORKDIR /data
COPY gateway/target/*.jar /data
EXPOSE 8081
ENTRYPOINT  java  -jar *.jar
ENV TZ "Asia/Shanghai"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime