FROM openjdk:12-alpine AS builder

#Get APK up to date
RUN apk update && apk upgrade
VOLUME /root/.m2
#Install Maven
RUN apk add maven

#Git
RUN apk add git
RUN mkdir /kar
RUN git clone https://github.com/tjidde-nl/geo-ov-kv7netwerk.git /kar

RUN  mvn -f /kar clean install -q

#Create Tomcat
#FROM tomcat:10.1.0-jre11-temurin
FROM tomcat:9-jre11-temurin
LABEL maintainer='Tjidde.Nieuwenhuizen@merkator.com'
COPY --from=builder $HOME/kar/target/*.war /usr/local/tomcat/webapps/kv7network.war
COPY ./EXT_Files/jar/* lib/
COPY ./EXT_Files/context/* conf/

EXPOSE 8080

CMD [ "catalina.sh", "run" ]
