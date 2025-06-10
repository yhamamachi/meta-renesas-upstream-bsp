#!/bin/bash -eu

SCRIPT_DIR=$(cd `dirname $0` && pwd)
cd $SCRIPT_DIR
API_URL="https://api.github.com/repos/morimoto/linux/branches"

LATEST_BRANCH=$(curl -s ${API_URL} | jq ".[].name" | grep sparrow-hawk | tail -1)
CURRENT_BRANCH=$(cat recipes-kernel/linux/kernel_6.12.inc | grep BRANCH | awk '{print $3}')

if [[ "$LATEST_BRANCH" != "$CURRENT_BRANCH" ]]; then
    echo "New branch has been pushed !"
    BRANCH=${LATEST_BRANCH}
    KERNEL_VERSION=$(echo $BRANCH | sed -e "s|.*renesas-.*/v||" -e "s/-.*$//")
    COMMIT=$(curl -s ${API_URL} | jq ".[] | select(.name == ${BRANCH} ) | .commit.sha")
    #cat  recipes-kernel/linux/kernel_6.12.inc
    #echo "-----------------"
    sed -i recipes-kernel/linux/kernel_6.12.inc \
        -e "s|BRANCH = .*$|BRANCH = ${BRANCH}|" \
        -e "s|SRCREV = .*$|SRCREV = ${COMMIT}|" \
        -e "s|LINUX_VERSION ?= .*$|LINUX_VERSION ?= \"${KERNEL_VERSION}\"|"
    git commit -s recipes-kernel/linux/kernel_6.12.inc -m "linux-renesas: Update to ${KERNEL_VERSION}"
else
    echo "There is no update"
fi

