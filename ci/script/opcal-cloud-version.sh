#!/bin/bash

## this script depends on xmlstarlet
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

DEPENDENCIES_FILE=${ROOT_PATH}/ci/maven/dependencies.properties

VERSION=$(props value ${DEPENDENCIES_FILE} opcal-cloud-starter-parent.version)

echo "opcal cloud version is [${VERSION}]"
# update opcal-cloud-starter-parent version
xmlstarlet edit -P -L -O -u "/_:project/_:parent/_:version" -v ${VERSION} ${ROOT_PATH}/pom.xml
