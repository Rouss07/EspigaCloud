FROM maven:3.9.11-eclipse-temurin-17 AS build-tools
FROM docker:28-cli AS docker-cli
FROM jenkins/jenkins:lts-jdk21
USER root
RUN apt-get update && apt-get install --no-install-recommends -y ca-certificates curl git && rm -rf /var/lib/apt/lists/*
COPY --from=build-tools /opt/java/openjdk /opt/jdk17
COPY --from=build-tools /usr/share/maven /usr/share/maven
COPY --from=docker-cli /usr/local/bin/docker /usr/local/bin/docker
COPY --from=docker-cli /usr/local/libexec/docker/cli-plugins/docker-compose /usr/local/libexec/docker/cli-plugins/docker-compose
ENV MAVEN_HOME=/usr/share/maven
ENV PATH="${MAVEN_HOME}/bin:${PATH}"
RUN groupadd -f docker && usermod -aG docker jenkins
USER jenkins
COPY --chown=jenkins:jenkins plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt
