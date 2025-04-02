#!/bin/bash

shopt -s expand_aliases

OSTYPE=$(uname)

if [[ "${OSTYPE}" == "Darwin" ]]; then
  if readlink -f "${BASH_SOURCE:-$0}" > /dev/null 2>&1; then
    echo "ok"
  else 
    if greadlink --version > /dev/null 2>&1; then
      alias readlink=greadlink
    fi
  fi
fi

SCRIPT=`readlink -f "${BASH_SOURCE:-$0}"`
SCRIPT_DIR_PATH=`dirname ${SCRIPT}`
CI_DIR_PATH=`dirname ${SCRIPT_DIR_PATH}`
ROOT_PATH=`dirname ${CI_DIR_PATH}`

${ROOT_PATH}/mvnw -U clean package -Dmaven.test.skip=true

mkdir -p /tmp/artifact

find ${ROOT_PATH}/**/**/target/ -type f -iname '**.jar' | xargs -I {} cp {} /tmp/artifact;
