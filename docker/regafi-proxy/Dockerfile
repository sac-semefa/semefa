FROM vicozizou/semefa-base:latest as build-semefa

LABEL com.saludaunclic.contact="vico.zizou@gmail.com"
LABEL com.saludaunclic.vendor="Salud a un Clic"
LABEL com.saludaunclic.description="Semefa Regafi Proxy"

ARG BUILD_ID=master
ENV BUILD_ID=${BUILD_ID}
ARG VERSION="1.0-SNAPSHOT"
ENV VERSION=${VERSION}
ARG SERVER_PORT=9000
ARG ACTUATOR_PORT=9001
ENV SEMEFA_APP=regafi-proxy

WORKDIR /app

RUN /build/build_semefa_project.sh
RUN ls -las .

FROM adoptopenjdk/openjdk11:ubuntu-slim

WORKDIR /app

RUN apt update \
  && apt upgrade -y \
  && apt install -y bash grep openssl git sed tini ca-certificates \
  && update-ca-certificates \

COPY --from=build-semefa /app/ /app/
RUN ls -las .

EXPOSE ${SERVER_PORT} ${ACTUATOR_PORT}

ENTRYPOINT ["/usr/bin/tini", "--", "/app/entrypoint.sh"]
