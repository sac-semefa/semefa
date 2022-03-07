#!/bin/bash

[[ -z "${SEMEFA_APP}" ]] \
  && echo 'SEMEFA_APP environment variable is missing' \
  && exit 1

echo "Found SEMEFA_APP variable: ${SEMEFA_APP}"

: ${SAC_SEMEFA_TOKEN:="define-one"} && export SAC_SEMEFA_TOKEN
: ${BRANCH:="master"} && export BRANCH
profile=${1}
runner=/tmp/install-semefa.sh

curl -fsSL \
  https://${SAC_SEMEFA_TOKEN}@raw.githubusercontent.com/sac-semefa/semefa/${BRANCH}/scripts/install-semefa.sh \
  -o \
  ${runner}
chmod 755 ${runner}
. ${runner} ${SEMEFA_APP} ${profile}
