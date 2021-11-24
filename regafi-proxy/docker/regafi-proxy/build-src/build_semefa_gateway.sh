#!/bin/bash

build_id=${1}
[[ -z "${build_id}" ]] && build_id=master
src_folder=/build/src/semefa-gateway

function buildByCommit {
  local branch=${1}
  cd ${src_folder}
  git fetch --all
  git pull

  echo "Checking out commit/tag/branch: $1"
  if [[ "${branch}" != 'master' && "${branch}" != 'main' ]]; then
    echo "Executing: checkout -b ${branch} --track origin/${branch}"
    git checkout -b ${branch} --track origin/${branch}
  fi

  ./gradlew clean build -x test
  ./gradlew bootJar --no-daemon --build-cache
  cp ${src_folder}/build/libs/semefa-gateway-1.0.jar /app
  cp ${src_folder}/application.yml /app
}

buildByCommit ${build_id}
