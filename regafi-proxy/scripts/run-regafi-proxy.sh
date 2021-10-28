#!/bin/bash

profile=${1}
mode=${2}
[[ -z "${mode}" ]] && mode=compose

echo "Specified profile: ${profile}"
[[ -z "${profile}" ]] && profile='dev'
env_file=".env.${profile}"
echo "Discovered env file: ${profile}"

[[ ! -f ${env_file} ]] && echo "No existe archivo .env.<perfil>, abortando"

if [[ "${mode}" == 'compose' ]]; then
  env $(cat ${env_file} | xargs) docker-compose -f docker-compose.yml up
elif [[ "${mode}" == 'docker' ]]; then
  echo "Doing nothing since not supported... yet"
fi
