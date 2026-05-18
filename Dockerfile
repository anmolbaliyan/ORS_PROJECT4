FROM tomcat:9

# Copy WAR file
COPY target/ORS_Project4.war /usr/local/tomcat/webapps/ORS_Project4.war

# Expose default Tomcat port
EXPOSE 8080

# Start Tomcat 
CMD ["catalina.sh", "run"]