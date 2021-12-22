FROM adoptopenjdk/openjdk11:ubuntu-slim

LABEL com.saludaunclic.contact="vico.zizou@gmail.com"
LABEL com.saludaunclic.vendor="Salud a un Clic"
LABEL com.saludaunclic.description="Semefa Base Image"

ARG SAC_SEMEFA_TOKEN

WORKDIR /build

RUN apt update \
  && apt upgrade -y \
  && apt install -y bash grep openssl git tini ca-certificates \
  && update-ca-certificates

COPY build-src/ /build/
RUN chmod 500 /build/* && find /build/ -type f -name "*.sh" -exec chmod a+x {} \;

RUN mkdir -p /build/src \
  && cd /build/src \
  && echo "Pulling semefa parent project" \
  && git clone https://${SAC_SEMEFA_TOKEN}@github.com/sac-semefa/semefa.git semefa \
  && cd ./semefa \
  && git gc --aggressive \
  && /build/install-local-deps.sh \
  && echo 'Building [parent]' \
  && ./mvnw -q clean install -DskipTests

