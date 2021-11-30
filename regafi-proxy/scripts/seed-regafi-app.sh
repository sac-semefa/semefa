#!/bin/bash

: ${SAC_SEMEFA_TOKEN:="define-one"} && export SAC_SEMEFA_TOKEN
: ${BRANCH:="master"} && export BRANCH
profile=${1}

curl -fsSL https://${SAC_SEMEFA_TOKEN}@raw.githubusercontent.com/sac-semefa/semefa/${BRANCH}/scripts/run-semefa-app.sh -O
curl -fsSL https://${SAC_SEMEFA_TOKEN}@raw.githubusercontent.com/sac-semefa/semefa/${BRANCH}/scripts/stop-semefa-app.sh -O
chmod 755 run-semefa-app.sh
chmod 755 stop-semefa-app.sh
echo "Running with profile ${profile}" && ./run-semefa-app.sh regafi-proxy ${profile}
