#!/bin/bash

#sh -c "$(curl -fsSL https://raw.githubusercontent.com/vicozizou/semefa-gateway/master/scripts/seed-docker.sh)"

: ${BRANCH:='master'} && export BRANCH
script_resources=(docker-compose.yml runGw.sh setup.sh)
target_dir=${HOME}/.sac/semefa-gateway

[[ -d ${target_dir} ]] && rm -rf ${target_dir}
mkdir -p ${target_dir}
cp .env.* ${target_dir}
cd ${target_dir}
git clone https://github.com/vicozizou/semefa-gateway.git
ls -las .

for res in "${script_resources[@]}"
do
  echo "Preparing ${res}..."
  mv ./semefa-gateway/scripts/${res} ./
done

rm -rf ./semefa-gateway
ls -las .
