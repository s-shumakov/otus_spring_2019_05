FROM openjdk:8-jdk

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD target/web-app-0.0.1-SNAPSHOT.jar $PROJECT_DIR/

EXPOSE 8080

CMD ["java", "-jar", "/opt/project/web-app-0.0.1-SNAPSHOT.jar"]
