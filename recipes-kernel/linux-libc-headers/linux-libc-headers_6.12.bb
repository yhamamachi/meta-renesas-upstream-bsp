require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

COMPATIBLE_MACHINE = "(rcar-gen3|rcar-gen4)"

LINUX_VERSION ?= "6.12.22"
REPO = "git://github.com/morimoto/linux.git"
BRANCH = "renesas-lts/v6.12-dev"
SRC_URI = "${REPO};branch=${BRANCH};protocol=https"
SRCREV = "d40c02f47a379bf3537c019e7ad3f9fc1d47824c"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

S = "${WORKDIR}/git"
