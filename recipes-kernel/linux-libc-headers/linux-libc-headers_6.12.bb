require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

COMPATIBLE_MACHINE = "(rcar-gen3|rcar-gen4)"

LINUX_VERSION ?= "6.12.22"
REPO = "git://github.com/morimoto/linux.git"
BRANCH = "renesas-lts/v6.12.22-2025-04-09-sparrow-hawk-test"
SRC_URI = "${REPO};branch=${BRANCH};protocol=https"
SRCREV = "bc748d485f47071226e137b68ac586098b30c990"
KERNEL_DEFCONFIG = "renesas_defconfig"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

S = "${WORKDIR}/git"
