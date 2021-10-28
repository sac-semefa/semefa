#!/bin/bash

: ${MYSQL_ROOT_PASSWORD:="mysql"}
: ${MYSQL_USER:="semefa"}
: ${MYSQL_PASSWORD:="semefa"}
: ${MYSQL_DB:="semefa"}
container_id=mysql
docker_image='mysql:8.0'

function dbExists {
  output=$(docker exec -t ${container_id} \
    /bin/sh -c "mysql -s -N \\
      --password=${MYSQL_ROOT_PASSWORD} \\
      -e \"SELECT schema_name FROM schemata WHERE schema_name = '${MYSQL_DB}'\" information_schema 2>/dev/null"
  )
  echo "output: ${output}"
  if [[ -z "${output}" ]]; then
    return 1 # does not exist
  else
    return 0 # exists
  fi
}

if [[ ! "$(docker ps -q -f name=${container_id})" ]]; then
  if [[ "$(docker ps -aq -f status=exited -f status=created -f name=${container_id})" ]]; then
    echo "Removing container ${container_id}"
    docker rm -f ${container_id}
  fi

  docker run --name ${container_id} \
    -p 3306:3306 \
    -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} \
    -d ${docker_image}
  sleep 20
  echo "#######################################################################"
  echo "    mysql instance started at localhost:3306"
  echo "#######################################################################"
fi

dbExists
db_result=$?
if [[ ${db_result} == 1 ]]; then
  pushd ../mysql
  mysql_folder=$(pwd)
  popd
  init_script=${mysql_folder}/init.sql
  docker cp ${init_script} ${container_id}:${MYSQL_DB}-init.sql
  docker exec -t ${container_id} \
    /bin/sh -c "mysql \\
      --user=root \\
      --password=${MYSQL_ROOT_PASSWORD} \\
      < ${MYSQL_DB}-init.sql"

  echo "#########################################################################"
  echo "    initialized ${MYSQL_DB} database"
  echo "#########################################################################"
fi

pushd ../liquibase
liquibase_context=$(pwd)
popd
docker cp ${init_script} ${container_id}:${MYSQL_DB}-init.sql
docker run --rm --name liquibase-migration \
  -v ${liquibase_context}:/liquibase/changelog \
  liquibase/liquibase \
  --changeLogFile=changelog-master.xml \
  --url="jdbc:mysql://host.docker.internal:3306/${MYSQL_DB}" \
  --username=${MYSQL_USER} \
  --password=${MYSQL_PASSWORD} \
  update

echo "#########################################################################"
echo "    ${MYSQL_DB} database migrated"
echo "#########################################################################"
