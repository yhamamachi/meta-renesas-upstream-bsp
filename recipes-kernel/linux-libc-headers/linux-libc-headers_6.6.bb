require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

COMPATIBLE_MACHINE = "(rcar-gen3|rcar-gen4)"

SRC_URI = "git://github.com/renesas-rcar/upstream_bsp.git;branch=renesas-lts/v6.6-dev;protocol=https"
SRCREV = "c3ec98e4f85feebe53a989a088dd574c2ca5d9e7"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

S = "${WORKDIR}/git"
