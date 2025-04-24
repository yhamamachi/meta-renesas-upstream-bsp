#!/bin/bash

MACHINE=s4sk
MACHINE=sparrow-hawk
KERNEL_VERSION=6.12

SCRIPT_DIR=$(cd `dirname $0` && pwd)

mkdir -p ${SCRIPT_DIR}/build
cd ${SCRIPT_DIR}/build
export WORK=`pwd`

cd $WORK
git clone git://git.yoctoproject.org/poky
git clone git://git.openembedded.org/meta-openembedded
#git clone https://github.com/renesas-rcar/meta-renesas-upstream-bsp.git

git -C poky checkout -b scarthgap origin/scarthgap
git -C meta-openembedded checkout -b scarthgap origin/scarthgap
#git -C meta-renesas-upstream-bsp checkout -b scarthgap origin/scarthgap

cd $WORK
TEMPLATECONF=${SCRIPT_DIR}/conf/templates/$MACHINE  . poky/oe-init-build-env build-$MACHINE
sed -i conf/local.conf -e 's/"package_rpm"/"package_deb"/'

if [ "$KERNEL_VERSION" == "6.1" ]; then
    sed -i 's|= "6.6%"|= "6.1%"|g' conf/local.conf
fi
if [ "$KERNEL_VERSION" == "6.12" ]; then
    sed -i 's|= "6.6%"|= "6.12%"|g' conf/local.conf
fi

if [[ "${MACHINE}" == "sparrow-hawk" ]]; then
    FIRMWARE_LIST=("rcar_gen4_pcie.bin" "renesas_usb_fw.mem")
    for item in ${FIRMWARE_LIST[@]}; do
        if [[ ! -e ${SCRIPT_DIR}/firmware/${item} ]]; then
            echo "${SCRIPT_DIR}/firmware/${item} is not found !!"
            exit -1
        fi
    done
fi

#bitbake linux-renesas
bitbake core-image-minimal
# bitbake core-image-minimal -c populate_sdk

