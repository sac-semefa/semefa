#!/bin/bash

project=${1}

sac_home=${HOME}/.sac
source ${sac_home}/semefa-lib.sh

function usage {
  echo "Usage: ${0} <project> <profile>
    project: (required) semefa project (e.g. regafi-proxy or siteds-gateway)
"
}

cd ${sac_home}
showInfo

echo "Checking for ${project} container running" \
  && container_id=$(docker ps -q -f name=${project})
[[ -n "${container_id}" ]] \
  && echo "Found ${project} container: ${container_id}, stopping it" \
  && env $(cat ${env_file} | xargs) docker-compose -f ./${project}/docker-compose.yml down
