FROM tomcat:8.5
RUN rm -rf /usr/local/tomcat/webapps/*
ADD target/report-application.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]
