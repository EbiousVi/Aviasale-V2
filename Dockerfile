FROM bellsoft/liberica-openjdk-alpine-musl:latest
LABEL maintainer="EbiousVi" github="https://github.com/EbiousVi"
ARG JAR_FILE=target/aviasale.jar
WORKDIR /opt/aviasale
COPY  ${JAR_FILE} .
EXPOSE 6060
ENTRYPOINT ["java", "-jar", "aviasale.jar"]