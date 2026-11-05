
# Stage 1: Build WAR
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B clean package -DskipTests

# Stage 2: Run Tomcat
FROM tomcat:10.1-jdk21-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

# Disable shutdown port 8005
RUN sed -i 's/port="8005"/port="-1"/' \
    /usr/local/tomcat/conf/server.xml

# Change HTTP port from 8080 to 10000
RUN sed -i 's/port="8080"/port="10000"/' \
    /usr/local/tomcat/conf/server.xml

COPY --from=build /app/target/Hungngu.war \
    /usr/local/tomcat/webapps/ROOT.war

EXPOSE 10000

CMD ["catalina.sh", "run"]
