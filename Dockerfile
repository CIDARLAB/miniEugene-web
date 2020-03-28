FROM tomcat:8.0-alpine
LABEL maintainer="sanka@bu.edu"

# This line ensures that all the default tomcat ui and documentation is deleted
RUN rm -rf /usr/local/tomcat/webapps/*

#This line ensures that the miniEugene.war is renamed as ROOT.war to make it the default app
COPY ./target/miniEugene.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]