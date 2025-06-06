DESCRIPTION = "Custom FIT image with BL31, DTB, and Kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

inherit deploy

DEPENDS += "u-boot-mkimage-native dtc-native"
DEPENDS += " \
    linux-renesas \
    arm-trusted-firmware \
"

SRC_URI = " \
    file://fit-image.its \
    file://fit-image-pwm.its \
    file://fit-image-rpi-display-2.its \
    file://fit-image-rpi-display-2-and-DP.its \
"

FILES:${PN} += " \
    /boot/fitImage \
    /boot/fitImage-pwm \
    /boot/fitImage-rpi-display-2 \
    /boot/fitImage-rpi-display-2-and-DP \
"

do_configure[noexec] = "1"
do_compile[depends] += "linux-renesas:do_deploy"
do_compile[depends] += "arm-trusted-firmware:do_deploy"

do_compile() {
    cd ${DEPLOY_DIR}/images/${MACHINE}
    install -m 644 ${WORKDIR}/fit-image.its ./
    sed -i "s/bl31.bin/bl31-${MACHINE}.bin/" ./fit-image.its
    mkimage -f ./fit-image.its ./fitImage

    cd ${DEPLOY_DIR}/images/${MACHINE}
    install -m 644 ${WORKDIR}/fit-image-pwm.its ./
    sed -i "s/bl31.bin/bl31-${MACHINE}.bin/" ./fit-image-pwm.its
    mkimage -f ./fit-image-pwm.its ./fitImage-pwm

    cd ${DEPLOY_DIR}/images/${MACHINE}
    install -m 644 ${WORKDIR}/fit-image-rpi-display-2.its ./
    sed -i "s/bl31.bin/bl31-${MACHINE}.bin/" ./fit-image-rpi-display-2.its
    mkimage -f ./fit-image-rpi-display-2.its ./fitImage-rpi-display-2

    cd ${DEPLOY_DIR}/images/${MACHINE}
    install -m 644 ${WORKDIR}/fit-image-rpi-display-2-and-DP.its ./
    sed -i "s/bl31.bin/bl31-${MACHINE}.bin/" ./fit-image-rpi-display-2-and-DP.its
    mkimage -f ./fit-image-rpi-display-2-and-DP.its ./fitImage-rpi-display-2-and-DP
}
do_install() {
    cd ${DEPLOY_DIR}/images/${MACHINE}
    install -d ${D}/boot
    install -m 644 ./fitImage ${D}/boot
    install -m 644 ./fitImage-pwm ${D}/boot
    install -m 644 ./fitImage-rpi-display-2 ${D}/boot
    install -m 644 ./fitImage-rpi-display-2-and-DP ${D}/boot
}

