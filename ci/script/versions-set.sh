#!/bin/bash

set -e

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

VERSION=$1

echo ${VERSION}
${ROOT_PATH}/mvnw -U clean compile >> /dev/null 2>&1

## project.version way
${ROOT_PATH}/mvnw versions:set -DnewVersion=${VERSION}

## revision property version way
#${ROOT_PATH}/mvnw versions:set-property -Dproperty=revision -DnewVersion=${VERSION} >> /dev/null 2>&1
