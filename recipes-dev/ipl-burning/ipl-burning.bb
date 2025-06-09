DESCRIPTION = "IPL burning tool"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

COMPATIBLE_MACHINE = "sparrow-hawk"

ALLOW_EMPTY:${PN} = "1"
ALLOW_EMPTY:${PN}-dev = "1"
ALLOW_EMPTY:${PN}-staticdev = "1"

SRC_URI:append = " \
    file://burn.py \
    file://ipl_burning.json \
    file://ipl_burning.py \
"

# do_configure() nothing
do_configure[noexec] = "1"
# do_compile() nothing
do_compile[noexec] = "1"
# do_install() nothing
do_install[noexec] = "1"

# Wait for U-Boot
do_deploy[depends] += "u-boot:do_deploy"

do_deploy() {
    # Create deploy folder
    install -d ${DEPLOYDIR}/${PN}

    # Copy to deploy folder
    install -m 0644 ${WORKDIR}/burn.py ${DEPLOYDIR}/${PN}
    install -m 0644 ${WORKDIR}/ipl_burning.py ${DEPLOYDIR}/${PN}
    install -m 0644 ${WORKDIR}/ipl_burning.json ${DEPLOYDIR}/${PN}
    install -m 0644 ${DEPLOY_DIR}/images/${MACHINE}/flash.bin ${DEPLOYDIR}/${PN}
}

addtask deploy before do_build after do_compile

