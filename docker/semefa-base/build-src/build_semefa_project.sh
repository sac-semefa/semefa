#!/bin/bash

project=${SEMEFA_APP}
version=${VERSION}
build_id=${BUILD_ID}
[[ -z "${project}" ]] && echo "Error: project name was not provided" && exit 1
[[ -z "${version}" ]] && echo "Error: project version was not provided" && exit 2
[[ -z "${build_id}" ]] && build_id=master
root_folder=/build/src/semefa

function buildByCommit {
  local branch=${1}
  local version=${2}
  cd ${root_folder}
  git fetch --all
  git pull

  if [[ "${branch}" != 'master' && "${branch}" != 'main' ]]; then
    echo "Executing: checkout -b ${branch} --track origin/${branch}"
    git checkout -b ${branch} --track origin/${branch}
  fi

  echo 'Building [parent]'
  ./mvnw -q clean install -DskipTests

  echo "Building [${project}]"
  pushd ${project}
  ../mvnw package spring-boot:repackage -DskipTests
  cp ./target/${project}-${version}.jar /app/
  cd ..

  ./mvnw clean
}

buildByCommit ${build_id} ${version}
cd /app
ln -s ./${project}-${version}.jar ${project}.jar