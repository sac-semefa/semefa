#!/bin/bash

project=${1}
profile=${2}
sac_home=${HOME}/.sac
semefa_dir=${sac_home}/${project}
semefa_user_dir=${HOME}/semefa
docker_org=vicozizou

function usage {
  echo "Usage: ${0} <project> [<profile>]
    project: (required) semefa project (e.g. regafi-proxy or siteds-gateway)
    profile: (optional) env profile to use when running app (e.g. dev, test, prod)

    * An environment variable called 'SAC_SEMEFA_TOKEN' should be defined
"
}

function required {
  [[ -z "${SAC_SEMEFA_TOKEN}" ]] && echo "Error: SAC_SEMEFA_TOKEN is missing" && exit 1
  : ${BRANCH:='master'} && export BRANCH
  echo "Using ${BRANCH} branch"
  eval "$(curl -fsSL https://${SAC_SEMEFA_TOKEN}@raw.githubusercontent.com/sac-semefa/semefa/${BRANCH}/scripts/semefa-lib.sh | tail -n +2)"
}

function processArgs {
  [[ -z "${project}" ]] && exitOnError 2 "Error: project is missing"
}

function prepareInstall {
  echo "Cleaning semefa folders"
  rm -rf ${semefa_dir} && mkdir -p ${semefa_dir}
  rm -rf ${semefa_user_dir} && mkdir -p ${semefa_user_dir}
}

function installApp {
  prepareInstall
  cd ${sac_home}

  echo "Fetching env data..."
  git clone https://${SAC_SEMEFA_TOKEN}@github.com/sac-semefa/semefa-env.git
  cp ./semefa-env/${project}/.env.* ${semefa_dir}
  rm -rf ./semefa-env

  local global_resources=(
    scripts/semefa-lib.sh
    scripts/stop-semefa-app.sh
    scripts/run-semefa-app.sh
    scripts/remove-semefa-app.sh
    scripts/update-semefa-app.sh
    scripts/log-semefa-app.sh
  )
  echo "Fetching GLOBAL files to run ${project} app..."
  for res in "${global_resources[@]}"
  do
    echo "Fetching ${res}"
    curl -fsSL https://${SAC_SEMEFA_TOKEN}@raw.githubusercontent.com/sac-semefa/semefa/${BRANCH}/${res} -O

    local based=$(basename ${res})
    [[ "${based}" == *.sh ]] \
      && chmod 755 ${based} \
      && ln -s ${sac_home}/${based} ${semefa_user_dir}/${based}
  done
  ls -las ${sac_home} && ls -las ${semefa_user_dir}

  cd ${semefa_dir}
  local script_resources=(
      docker/${project}/docker-compose.yml
      ${project}/scripts/setup.sh
    )
  echo "Fetching files to run ${project} app..."
  for res in "${script_resources[@]}"
  do
    echo "Fetching ${res}"
    curl -fsSL https://${SAC_SEMEFA_TOKEN}@raw.githubusercontent.com/sac-semefa/semefa/${BRANCH}/${res} -O

    local based=$(basename ${res})
    [[ "${based}" == *.sh ]] \
      && chmod 755 ${based} \
      && ln -s ${semefa_dir}/${based} ${semefa_user_dir}/${based}
  done
  ls -las ${semefa_dir} && ls -las ${semefa_user_dir}
}

function runApp {
  local script=update-semefa-app.sh
  cd ${semefa_user_dir}
  echo "Running app ./${script} ${project} ${profile}" \
    && . ./${script} ${project} ${profile} \
    && . ./log-semefa-app.sh ${project}
}

required
processArgs
showInfo
installApp
[[ -n "${profile}" ]] && runApp
