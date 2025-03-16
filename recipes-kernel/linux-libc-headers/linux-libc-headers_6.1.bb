require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

COMPATIBLE_MACHINE = "(rcar-gen3|rcar-gen4)"

SRC_URI = "git://github.com/renesas-rcar/upstream_bsp.git;branch=renesas-lts/v6.1-dev;protocol=https"
SRCREV = "66e89b79c53d5af4ea53675a51b287512209e49b"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

S = "${WORKDIR}/git"
