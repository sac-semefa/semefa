#!/bin/bash

project=${SEMEFA_APP}
version=${1}
build_id=${BUILD_ID}
[[ -z "${project}" ]] && echo "Error: project name was not provided" && exit 1
[[ -z "${version}" ]] && echo "Error: project version was not provided" && exit 2
[[ -z "${build_id}" ]] && build_id=master
root_folder=/build/src/semefa
src_folder=${root_folder}/${project}

function buildByCommit {
  local branch=${1}
  local version=${2}
  pushd ${root_folder}
  git fetch --all
  git pull

  if [[ "${branch}" != 'master' && "${branch}" != 'main' ]]; then
    echo "Executing: checkout -b ${branch} --track origin/${branch}"
    git checkout -b ${branch} --track origin/${branch}
  fi

  /build/install-local-deps.sh

  echo "Building [parent]"
  ./mvnw -q clean install -DskipTests

  echo "Building [${project}]"
  pushd ${src_folder}
  ../mvnw package spring-boot:repackage -DskipTests
  cp ./target/${project}-${version}.jar /app/
  popd

  ./mvnw clean
  popd
}

buildByCommit ${build_id} ${version}
ln -s ./${project}-${version}.jar ${project}.jar
rm -rf ~/.m2/repository