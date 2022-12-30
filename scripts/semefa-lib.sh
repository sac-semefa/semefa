#!/bin/bash

export semefa_dir=${sac_home}/${project}
export semefa_user_dir=${HOME}/semefa
export docker_org=vicozizou

function exitOnError {
  local code=${1}
  local messg=${2}
  echo "${messg}"
  usage
  exit ${code}
}

function showInfo {
  [[ "${SILENT}" == 'true' ]] && return 0
  echo "###################################
Info:
BRANCH: ${BRANCH}
SILENT: ${SILENT}
project: ${project}
profile: ${profile}
semefa_dir: ${semefa_dir}
semefa_user_dir: ${semefa_user_dir}
env_file: ${env_file}
###################################
"
}

function processProfile {
  [[ "${SILENT}" == 'true' ]] && return

  local error=${1}
  [[ -z "${error}" ]] && error=1

  export env_file="${sac_home}/${project}/.env.${profile}"
  [[ ! -f ${env_file} ]] && exitOnError 1 "Error: env file ${env_file} does not exist"

  [[ "${SILENT}" != 'true' ]] && echo "Discovered env file: ${env_file}"
}

function updateEnv {
  echo "Fetching env data..."
  git clone https://${SAC_SEMEFA_TOKEN}@github.com/sac-semefa/semefa-env.git
  cp ./semefa-env/${project}/.env.* ${semefa_dir}
  ls -las ${semefa_dir}/.env.*
  cat ${semefa_dir}/.env.${profile}
  rm -rf ./semefa-env
}
