#!/bin/bash

api_url=${1}
username=${2}
password=${3}

[[ -z "${api_url}" ]] && api_url='localhost:9000/api/public/setup'
[[ -z "${username}" ]] && username='admin'
[[ -z "${password}" ]] && password='admin'

docker exec -it \
  semefa-gateway \
  curl --location --request POST ${api_url} --header 'Content-Type: application/json' --data-raw "{\"user\": {\"username\":\"${username}\",\"password\": \"${password}\"}}"
