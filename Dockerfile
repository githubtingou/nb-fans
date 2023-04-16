FROM openjdk:8-jdk-alpine
MAINTAINER ting/1486630136@qq.com

ADD target/nbfans.jar /data/dockerjars/nbfans.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/data/dockerjars/nbfans.jar"]