FROM vicozizou/semefa-base:latest as build-semefa

LABEL com.saludaunclic.contact="vico.zizou@gmail.com"
LABEL com.saludaunclic.vendor="Salud a un Clic"
LABEL com.saludaunclic.description="Semefa Siteds Gateway"

ARG BUILD_ID=master
ENV BUILD_ID=${BUILD_ID}
ARG VERSION="1.0-SNAPSHOT"
ENV VERSION=${VERSION}
ARG SERVER_PORT=9002
ARG ACTUATOR_PORT=9003
ENV SEMEFA_APP=siteds-gateway

WORKDIR /build

RUN ./build-semefa-project.sh


FROM adoptopenjdk/openjdk11:ubuntu-slim

ARG VERSION="1.0-SNAPSHOT"
ENV VERSION=${VERSION}
ENV SEMEFA_APP=siteds-gateway

RUN apt update \
  && apt upgrade -y \
  && apt install -y bash grep openssl git sed tini ca-certificates \
  && update-ca-certificates

WORKDIR /app

COPY entrypoint.sh .
RUN find /app/ -type f -name "*.sh" -exec chmod a+x {} \;

COPY --from=build-semefa /build/${SEMEFA_APP}-${VERSION}.jar ./${SEMEFA_APP}.jar
RUN ls -las .

EXPOSE ${SERVER_PORT} ${ACTUATOR_PORT}

ENTRYPOINT ["/usr/bin/tini", "--", "/app/entrypoint.sh"]
