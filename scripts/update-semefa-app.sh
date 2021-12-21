#!/bin/bash

project=${1}
profile=${2}

semefa_dir=${HOME}/.sac/${project}
source ${semefa_dir}/semefa-lib.sh

function usage {
  echo "Usage: ${0} <project> <profile>
    project: (required) semefa project (e.g. regafi-proxy or siteds-gateway)
    profile: (required) env profile to use (e.g. dev, test, prod)
"
}

cd ${semefa_dir}
showInfo
export SILENT=true
. ./stop-semefa-app.sh ${project} ${profile}
. ./remove-semefa-app.sh ${project}
. ./run-semefa-app.sh ${project} ${profile}
export SILENT=
