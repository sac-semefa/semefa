#!/bin/bash

: ${SAC_SEMEFA_TOKEN:="define-one"} && export SAC_SEMEFA_TOKEN
: ${BRANCH:="master"} && export BRANCH
profile=${1}
script=/tmp/install-semefa.sh

echo "Running with profile ${profile}" \
  && sh -c "$(curl -fsSL https://${SAC_SEMEFA_TOKEN}@raw.githubusercontent.com/sac-semefa/semefa/${BRANCH}/scripts/install-semefa.sh) ${profile}"
