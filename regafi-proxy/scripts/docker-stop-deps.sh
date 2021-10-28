#!/bin/bash

set -e

container_id=mysql
migration_container_id=liquibase-migration

if [[ "$(docker ps -q -f name=${container_id})" ]]; then
  docker rm -f ${container_id}

  echo "###########################################"
  echo "####      removed mysql instance       ####"
  echo "###########################################"
fi

if [[ "$(docker ps -a -q -f name=${migration_container_id})" ]]; then
  docker rm -f ${migration_container_id}

  echo "############################################"
  echo "####     removed migration instance     ####"
  echo "############################################"
fi
