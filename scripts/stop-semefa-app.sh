#!/bin/bash

project=${1}

function usage {
  echo "Usage: ${0} <project>
  project: semefa project (e.g. regafi-proxy or siteds-gateway)
"
}

function exitOnError {
  local code=${1}
  usage
  exit ${code}
}

[[ -z "${SAC_SEMEFA_TOKEN}" ]] && echo "Error: SAC_SEMEFA_TOKEN is missing" && exitOnError 1
: ${BRANCH:='master'} && export BRANCH
[[ -z "${project}" ]] && echo "Error: project is missing" && exitOnError 2
target_dir=${HOME}/.sac/${project}

usage

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
