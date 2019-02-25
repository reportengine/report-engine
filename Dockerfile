FROM openjdk:alpine

ENV APP_HOME /app/

COPY target/reportengine-core-*.jar $APP_HOME/reportengine-core.jar

WORKDIR $APP_HOME
CMD java -jar reportengine-core.jar
