#!/bin/bash

project=${1}
lines=${2}

semefa_dir=${HOME}/.sac/${project}
source ${semefa_dir}/semefa-lib.sh

function usage {
  echo "Usage: ${0} <project> <profile>
    project: (required) semefa project (e.g. regafi-proxy or siteds-gateway)
"
}

cd ${semefa_dir}
showInfo
[[ -n "${lines}" ]] && lines_param="-n ${lines}"

echo "Checking for ${project} container running" \
  && container_id=$(docker ps -q -f name=${project})
[[ -n "${container_id}" ]] \
  && echo "Found ${project} container: ${container_id}, logging from it" \
  && docker logs ${lines_param} -f ${container_id}
