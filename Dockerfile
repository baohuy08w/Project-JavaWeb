
# Stage 1: Build the Java Web application
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B clean package -DskipTests

# Stage 2: Run the application with Tomcat
FROM tomcat:10.1-jdk21-temurin

# Remove default applications
RUN rm -rf /usr/local/tomcat/webapps/*

# Configure Tomcat to listen on port 10000
RUN sed -i 's/port="8080"/port="10000"/' \
    /usr/local/tomcat/conf/server.xml

# Deploy WAR as the root application
COPY --from=build /app/target/Hungngu.war \
    /usr/local/tomcat/webapps/ROOT.war

EXPOSE 10000

CMD ["catalina.sh", "run"]
