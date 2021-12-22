#!/bin/bash

: ${SAC_SEMEFA_TOKEN:="define-one"} && export SAC_SEMEFA_TOKEN
: ${BRANCH:="master"} && export BRANCH
profile=${1}
runner=/tmp/install-semefa.sh

curl -fsSL https://${SAC_SEMEFA_TOKEN}@raw.githubusercontent.com/sac-semefa/semefa/${BRANCH}/scripts/install-semefa.sh -o ${runner}
chmod 755 ${runner}
. ${runner} ${project} ${profile}
