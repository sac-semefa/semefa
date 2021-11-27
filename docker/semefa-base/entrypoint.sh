#!/bin/bash

echo "172.17.0.1    host.docker.internal" >> /etc/hosts
java -jar ./${SEMEFA_APP}.jar
