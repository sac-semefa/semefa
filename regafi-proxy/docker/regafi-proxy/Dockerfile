FROM vicozizou/semefa-gateway-base:latest as build-semefa-gateway

LABEL com.saludaunclic.contact="vico.zizou@gmail.com"
LABEL com.saludaunclic.vendor="Salud a un Clic"
LABEL com.saludaunclic.description="SAC Semefa Gateway"

ARG BUILD_ID=HEAD
ARG PROFILE=dev
ARG SERVER_PORT=9000
ENV SERVER_PORT=${SERVER_PORT}
ENV PROFILE=${PROFILE}
ENV GRADLE_USER_HOME=/build/src/semefa-gateway/.gradle
ENV GRADLE_OPTS="-Dfile.encoding=utf-8 -Xmx2g"

WORKDIR /app

COPY build-src/ /build/
RUN chmod 500 /build/build_semefa_gateway.sh && /build/build_semefa_gateway.sh ${BUILD_ID}

COPY entrypoint.sh /app/
RUN find /app/ -type f -name "*.sh" -exec chmod a+x {} \;

EXPOSE 9000 9001

ENTRYPOINT ["/usr/bin/tini", "--", "/app/entrypoint.sh"]
