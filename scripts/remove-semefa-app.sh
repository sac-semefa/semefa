#!/bin/bash

project=${1}

sac_home=${HOME}/.sac
source ${sac_home}/semefa-lib.sh

function usage {
  echo "Usage: ${0} <project>
    project: (required) semefa project (e.g. regafi-proxy or siteds-gateway)
"
}

cd ${sac_home}
showInfo

echo "Checking for ${docker_org}/${project} pulled image"
image_id=$(docker images | grep ${docker_org}/${project} | awk '{print $3}')
if [[ -n "${image_id}" ]]; then
  echo "Found image: ${image_id}, removing it"
  docker rmi -f ${image_id}
else
  echo "${project} image cannot be found"
fi
