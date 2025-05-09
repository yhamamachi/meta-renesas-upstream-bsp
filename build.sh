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
rm -rf build-$MACHINE/conf
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
            echo "Dummy file is created: ${SCRIPT_DIR}/firmware/${item}"
            touch ${SCRIPT_DIR}/firmware/${item}
        fi
    done
fi

cat << EOS >> conf/local.conf
IMAGE_INSTALL:remove = " cpufreq-initscripts"
EOS

cat << EOS >> conf/local.conf
BB_HASHSERVE_UPSTREAM = "wss://hashserv.yoctoproject.org/ws"
SSTATE_MIRRORS ?= "file://.* http://cdn.jsdelivr.net/yocto/sstate/all/PATH;downloadfilename=PATH"
BB_HASHSERVE = "auto"
BB_SIGNATURE_HANDLER = "OEEquivHash"
EOS

sed -i 's/INHERIT:remove = "create-spdx"//' conf/local.conf
cat << EOS >> conf/local.conf
# added for SBOM
# required. enable to generate spdx files.
INHERIT += "create-spdx"

# optional. if "1", output spdx files will be formatted.
SPDX_PRETTY = "1"

# optional. if "1", output spdx files includes [file-information section](https://spdx.github.io/spdx-spec/v2.3/file-information/).
SPDX_INCLUDE_SOURCES = "1"

# optional. if "1", bitbake will create source files archive for each package.
SPDX_ARCHIVE_SOURCES = "1"

# optional. if "1", bitbake will create output binary archive for each package.
SPDX_ARCHIVE_PACKAGED = "1"
EOS

#bitbake linux-renesas
bitbake core-image-minimal
# bitbake core-image-minimal -c populate_sdk

