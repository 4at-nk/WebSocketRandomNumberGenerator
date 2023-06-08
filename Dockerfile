FROM maven as build

COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package


FROM jetty:9.4.36-jdk15
COPY --from=build /home/app/target/random-number-generator.war /var/lib/jetty/webapps
CMD java -jar "$JETTY_HOME/start.jar"