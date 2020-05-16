FROM tomcat:8.0-jre8
LABEL maintainer="sanka@bu.edu"

# This line ensures that all the default tomcat ui and documentation is deleted
RUN rm -rf /usr/local/tomcat/webapps/*

#This line ensures that the miniEugene.war is renamed as ROOT.war to make it the default app
COPY ./target/miniEugene.war /usr/local/tomcat/webapps/ROOT.war

#make pigeon binary
RUN mkdir pigeon
COPY ./pigeon ./pigeon/

RUN apt-get -y update
RUN apt-get -y install python3-pip \
			python3-setuptools
RUN	pip3 install pipenv \
			https://github.com/pyinstaller/pyinstaller/archive/develop.zip 
RUN	pip3 install -r ./pigeon/requirements.txt 
RUN pyinstaller ./pigeon/cmdline.py --onefile

#move the binary to /user/local/tomcat
RUN mv ./pigeon/pigeon_test.txt ./

EXPOSE 8080
CMD ["catalina.sh", "run"]
