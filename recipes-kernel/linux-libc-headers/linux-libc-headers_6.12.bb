require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

COMPATIBLE_MACHINE = "(rcar-gen3|rcar-gen4)"

LINUX_VERSION ?= "6.14-rc7"
REPO = "git://github.com/morimoto/linux.git"
BRANCH = "sound-msiof-2025-03-26-2"
SRC_URI = "${REPO};branch=${BRANCH};protocol=https"
SRCREV = "2518ccf64c65482b415e5a831057998bfe2bda1d"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

S = "${WORKDIR}/git"
