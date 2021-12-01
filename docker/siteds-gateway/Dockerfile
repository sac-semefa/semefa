FROM vicozizou/semefa-base:latest as build-semefa

LABEL com.saludaunclic.contact="vico.zizou@gmail.com"
LABEL com.saludaunclic.vendor="Salud a un Clic"
LABEL com.saludaunclic.description="Semefa Siteds Gateway"

ARG BUILD_ID=master
ENV BUILD_ID=${BUILD_ID}
ARG VERSION="1.0-SNAPSHOT"
ARG SERVER_PORT=9002
ARG ACTUATOR_PORT=9003
ENV SEMEFA_APP=siteds-gateway

WORKDIR /app

RUN /build/build_semefa_project.sh ${VERSION}

EXPOSE ${SERVER_PORT} ${ACTUATOR_PORT}

ENTRYPOINT ["/usr/bin/tini", "--", "/app/entrypoint.sh"]
