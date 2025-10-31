FROM amazoncorretto:25

USER root

ARG JAR_FILE=build/libs/rest-service-app-*.jar
ARG SCRIPT_FILE=entrypoint.sh
COPY ${JAR_FILE} app.jar
COPY ${SCRIPT_FILE} entrypoint.sh
EXPOSE 8080
ENTRYPOINT ["sh","entrypoint.sh"]