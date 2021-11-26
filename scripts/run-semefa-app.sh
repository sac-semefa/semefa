#!/bin/bash

project=${1}
profile=${2}
mode=${3}

function exitOnError {
  local code=${1}
  echo "Usage: ${0} <project> <profile> <mode>
  project: semefa project (e.g. regafi-proxy or siteds-gateway)
  profile: env profile to use (e.g. dev, test, prod)
  mode: (Optional) how to invoke docker, default value: compose
"
  exit ${code}
}

[[ -z "${SAC_SEMEFA_TOKEN}" ]] && echo "Error: SAC_SEMEFA_TOKEN is missing" && exitOnError 1
: ${BRANCH:='master'} && export BRANCH
[[ -z "${profile}" ]] && echo "Error: profile argument is missing" && exitOnError 2
[[ -z "${mode}" ]] && mode=compose

target_dir=${HOME}/.sac/${project}
[[ -d ${target_dir} ]] && rm -rf ${target_dir}
mkdir -p ${target_dir}
cd ${target_dir}

echo "Fetching env data..."
git clone https://${SAC_SEMEFA_TOKEN}@github.com/sac-semefa/semefa-env.git
ls -las ./semefa-env/${project}
cp ./semefa-env/${project}/.* .
rm -rf ./semefa-env

script_resources=(${project}/docker/docker-compose.yml ${project}/scripts/setup.sh)
echo "Fetching files to run ${project} app..."
for res in "${script_resources[@]}"
do
  echo "Fetching ${res}"
  curl -fsSL https://${SAC_SEMEFA_TOKEN}@raw.githubusercontent.com/sac-semefa/semefa/${BRANCH}/${res} -O
  based=$(basename ${res})
  [[ "${based}" == *.sh ]] && chmod 755 ${based}
done
ls -las .

echo "Specified profile: ${profile}"
env_file=".env.${profile}"
[[ ! -f ${env_file} ]] && echo "Error: env file ${env_file} does not exist" && exitOnError 3
echo "Discovered env file: ${env_file}"

if [[ "${mode}" == 'compose' ]]; then
  env $(cat ${env_file} | xargs) docker-compose -f docker-compose.yml up
elif [[ "${mode}" == 'docker' ]]; then
  echo "Doing nothing since not supported... yet"
fi
