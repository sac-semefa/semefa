#!/bin/bash

project=${1}
profile=${2}

semefa_home=${HOME}/.sac
source ${semefa_home}/semefa-lib.sh

function usage {
  echo "Usage: ${0} <project> <profile>
    project: (required) semefa project (e.g. regafi-proxy or siteds-gateway)
    profile: (required) env profile to use (e.g. dev, test, prod)

    * An environment variable called 'SAC_SEMEFA_TOKEN' should be defined
"
}

cd ${semefa_home}
processProfile
showInfo

export SILENT=true
. ./stop-semefa-app.sh ${project} ${profile}
export SILENT=

echo "Starting ${project} container" \
  && env $(cat ${env_file} | xargs) docker-compose -f ./${project}/docker-compose.yml up -d
