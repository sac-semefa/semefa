#!/bin/bash

project=${1}
profile=${2}
update_env=${3}

export sac_home=${HOME}/.sac
source ${sac_home}/semefa-lib.sh

function usage {
  echo "Usage: ${0} <project> <profile> [true|false]
    project: (required) semefa project (e.g. regafi-proxy or siteds-gateway)
    profile: (required) env profile to use (e.g. dev, test, prod)
    update env flag: when true updates .env files from repo
"
}

cd ${sac_home}
processProfile
showInfo
export SILENT=true
. ./stop-semefa-app.sh ${project} ${profile}
. ./remove-semefa-app.sh ${project}
. ./run-semefa-app.sh ${project} ${profile} ${update_env}
export SILENT=
