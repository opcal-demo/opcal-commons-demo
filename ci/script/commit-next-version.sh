#!/bin/bash

set -e

shopt -s expand_aliases

SCRIPT=$(readlink -f "${BASH_SOURCE:-$0}")
SCRIPT_DIR_PATH=$(dirname ${SCRIPT})
CI_DIR_PATH=$(dirname ${SCRIPT_DIR_PATH})
ROOT_PATH=$(dirname ${CI_DIR_PATH})

# tag release
RELEASE_VERSION=$("${ROOT_PATH}"/mvnw help:evaluate -Dexpression=project.version | grep "^[^\\[]" |grep -v Download | cut -d '-' -f 1)
TAG_NAME=${RELEASE_VERSION}.release
git tag -a ${TAG_NAME} -m "Release Tag ${RELEASE_VERSION}"
git push origin ${TAG_NAME}

# next version
"${SCRIPT_DIR_PATH}"/next-version.sh -${VERSION_DIRECTION:-n}

"${SCRIPT_DIR_PATH}"/clean-pom.sh

NEXT_DEVELOPMENT_VERSION=$(cat /tmp/NEXT_DEVELOPMENT_VERSION)

# commit
git add .
git commit -m "[skip ci] Next development version (${NEXT_DEVELOPMENT_VERSION})"

# skip gitlab ci pipeline for next development version update
git push -o ci.skip