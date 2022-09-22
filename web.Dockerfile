FROM openjdk:12-alpine AS builder
#Get APK up to date
RUN apk update && apk upgrade
VOLUME /root/.m2
#Install Maven
RUN apk add maven

#Git
#RUN apk add git
RUN mkdir /kar


COPY ./ /kar

RUN ls -lR
RUN mvn -f /kar  install  -e -q

#Create TOMCAT
FROM tomcat:9.0-alpine

LABEL maintainer='Tjidde.Nieuwenhuizen@merkator.com'
COPY --from=builder $HOME/kar/target/* /
COPY ./EXT_Files/jar/* lib/
COPY ./EXT_Files/context/* conf/ 
#COPY --from=builder $HOME/kar/fiets/tomcat-users.xml webapps/host-manager/META-INF/
#COPY --from=builder $HOME/kar/fiets/context.xml conf/

EXPOSE 8080


RUN mkdir -p /usr/local/tomcat/webapps/kargeotool/
RUN unzip -qo /kargeotool.war -d /usr/local/tomcat/webapps/kargeotool/
EXPOSE 8080

COPY  ./dockerfiles/entrypoint/* /entry/
RUN chmod +x /entry/entrypoint
ENTRYPOINT ["/entry/entrypoint"]
CMD ["tomcat"]

