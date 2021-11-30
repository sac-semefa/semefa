#!/bin/bash

project=${1}

function usage {
  echo "Usage: ${0} <project>
  project: semefa project (e.g. regafi-proxy or siteds-gateway)
"
}

function exitOnError {
  local code=${1}
  local messg=${2}
  echo "$@
  ${messg}"
  usage
  exit ${code}
}

[[ -z "${SAC_SEMEFA_TOKEN}" ]]  && exitOnError 1 "Error: SAC_SEMEFA_TOKEN is missing"
: ${BRANCH:='master'} && export BRANCH
[[ -z "${project}" ]] && exitOnError 2 "Error: project is missing"
target_dir=${HOME}/.sac/${project}

if [[ ! -d ${target_dir} ]]; then
  mkdir -p ${target_dir}
fi
cd ${target_dir}
if [[ -f ./docker-compose.yml ]]; then
  echo "Using found docker-compose.yml"
else
  echo "Fetching docker-compose.yml since it is missing"
  curl -fsSL https://${SAC_SEMEFA_TOKEN}@raw.githubusercontent.com/sac-semefa/semefa/${BRANCH}/docker/${project}/docker-compose.yml -O
fi

echo "Stopping docker image"
docker-compose -f docker-compose.yml down
