FROM amazoncorretto:11-alpine
RUN apk --no-cache add curl
VOLUME /tmp
ARG JAR_FILE=target/filetransformer-v1.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]