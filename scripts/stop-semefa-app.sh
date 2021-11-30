#!/bin/bash

project=${1}
profile=${2}

function usage {
  echo "Usage: ${0} <project>
  project: semefa project (e.g. regafi-proxy or siteds-gateway)
  profile: env profile to use (e.g. dev, test, prod)
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
[[ -z "${profile}" ]] && exitOnError 3 "Error: profile is missing"

target_dir=${HOME}/.sac/${project}
if [[ ! -d ${target_dir} ]]; then
  mkdir -p ${target_dir}
  cd ${target_dir}
  echo "Fetching env data..."
  git clone https://${SAC_SEMEFA_TOKEN}@github.com/sac-semefa/semefa-env.git
  ls -las ./semefa-env/${project}
  cp ./semefa-env/${project}/.* .
  rm -rf ./semefa-env
  echo "Fetching docker-compose.yml since it is missing"
  curl -fsSL https://${SAC_SEMEFA_TOKEN}@raw.githubusercontent.com/sac-semefa/semefa/${BRANCH}/docker/${project}/docker-compose.yml -O
else
  cd ${target_dir}
fi

echo "Stopping docker image"
env $(cat ${env_file} | xargs) docker-compose -f docker-compose.yml down
