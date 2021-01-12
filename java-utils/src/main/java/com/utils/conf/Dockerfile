# VERSION 1.0.0
# Author: robin
# 打包jar采用maven镜像
FROM maven:3.5.4-jdk-8-alpine AS MAVEN_BUILD
MAINTAINER robin <hantong96@outlook.com>
# 工作目录在镜像的 /build 目录下
WORKDIR /build/
#设置时区
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
# 把本地的 pom.xml 和 src目录 复制到镜像的/build目录下
COPY pom.xml /build/
COPY src /build/src/
# 执行maven打包
RUN mvn package
# 运行jar采用jdk基础镜像
FROM openjdk:8-jdk-alpine
# 设置工作目录在镜像的 /app 目录下
WORKDIR /app
# 将jar包添加到容器中并更名为app.jar
COPY --from=MAVEN_BUILD /build/target/deviceFaceData-1.0-SNAPSHOT.jar /app/
# 运行jar包
ENTRYPOINT  ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
