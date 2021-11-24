FROM adoptopenjdk/openjdk11:ubuntu

LABEL com.saludaunclic.contact="vico.zizou@gmail.com"
LABEL com.saludaunclic.vendor="Salud a un Clic"
LABEL com.saludaunclic.description="SAC Semefa Gateway"

WORKDIR /app

ENV GRADLE_USER_HOME=/build/src/semefa-gateway/.gradle
ENV GRADLE_OPTS="-Dfile.encoding=utf-8 -Xmx2g"

RUN apt update \
  && apt upgrade -y \
  && apt install -y bash grep openssl git sed tini ca-certificates \
  && update-ca-certificates

RUN mkdir -p /build/src \
  && cd /build/src \
  && echo "Pulling semefa-gateway" \
  && git clone https://github.com/vicozizou/semefa-gateway.git semefa-gateway \
  && cd ./semefa-gateway \
  && git gc --aggressive
