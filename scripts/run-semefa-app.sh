#!/bin/bash

project=${1}
profile=${2}

sac_home=${HOME}/.sac
source ${sac_home}/semefa-lib.sh

function usage {
  echo "Usage: ${0} <project> <profile>
    project: (required) semefa project (e.g. regafi-proxy or siteds-gateway)
    profile: (required) env profile to use (e.g. dev, test, prod)

    * An environment variable called 'SAC_SEMEFA_TOKEN' should be defined
"
}

cd ${sac_home}
processProfile
showInfo

echo "Starting ${project} container with this configuration:" \
  && cat ${env_file} \
  && env $(cat ${env_file} | xargs) docker-compose -f ./${project}/docker-compose.yml up -d
