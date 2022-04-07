FROM maven:3.5-jdk-7-alpine as build
WORKDIR /app
COPY . /src/main
WORKDIR /src/main
RUN mvn install

FROM tomcat:8.0-alpine as server
LABEL maintainer="sanka@bu.edu"

# This line ensures that all the default tomcat ui and documentation is deleted
RUN rm -rf /usr/local/tomcat/webapps/*

#This line ensures that the miniEugene.war is renamed as ROOT.war to make it the default app
COPY --from=build /src/main/target/miniEugene.war /usr/local/tomcat/webapps/

EXPOSE 8080
CMD ["catalina.sh", "run"]