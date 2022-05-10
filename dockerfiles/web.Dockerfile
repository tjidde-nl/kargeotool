FROM openjdk:12-alpine AS builder
#Get APK up to date
RUN apk update && apk upgrade
VOLUME /root/.m2
#Install Maven
RUN apk add maven

#Git
RUN apk add git
RUN mkdir /kar
RUN git clone https://github.com/tjidde-nl/kargeotool.git /kar

RUN mvn -f /kar clean install -q

#Create TOMCAT
FROM tomcat:9.0-alpine

LABEL maintainer='Tjidde.Nieuwenhuizen@merkator.com'
COPY --from=builder $HOME/kar/target/* /
COPY ./EXT_Files/jar/* lib/
COPY ./EXT_Files/context/* conf/
EXPOSE 8080


RUN mkdir -p /usr/local/tomcat/webapps/kargeotool/
RUN unzip -qo /kargeotool.war -d /usr/local/tomcat/webapps/kargeotool/
EXPOSE 8080

COPY  ./dockerfiles/entrypoint/* /entry/
RUN chmod +x /entrypoint 
ENTRYPOINT [ "/entry/entrypoint" ]
CMD ["tomcat"]

