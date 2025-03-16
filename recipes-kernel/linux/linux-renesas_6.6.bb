DESCRIPTION = "Linux kernel for the R-Car board"

require recipes-kernel/linux/linux-yocto.inc
require include/cas-control.inc

COMPATIBLE_MACHINE = "(rcar-gen3|rcar-gen4)"

LINUX_VERSION ?= "6.6.52"
PV = "${LINUX_VERSION}+git${SRCPV}"

SRC_URI = "git://github.com/renesas-rcar/upstream_bsp.git;branch=renesas-lts/v6.6-dev;protocol=https"
SRCREV = "c3ec98e4f85feebe53a989a088dd574c2ca5d9e7"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

S = "${WORKDIR}/git"

# For generating defconfig
KCONFIG_MODE = "--alldefconfig"
KBUILD_DEFCONFIG = "defconfig"

do_src_package_preprocess () {
    # Trim build paths from comments in generated sources to ensure reproducibility
    sed -i -e "s,${S}/,,g" \
            -e "s,${B}/,,g" \
        ${B}/drivers/video/logo/logo_linux_clut224.c \
        ${B}/drivers/tty/vt/consolemap_deftbl.c \
        ${B}/lib/oid_registry_data.c
}
addtask do_src_package_preprocess after do_compile before do_install

do_compile_kernelmodules:append () {
    if (grep -q -i -e '^CONFIG_MODULES=y$' ${B}/.config); then
        # 5.10+ kernels have module.lds that we need to copy for external module builds
        if [ -e "${B}/scripts/module.lds" ]; then
            install -Dm 0644 ${B}/scripts/module.lds ${STAGING_KERNEL_BUILDDIR}/scripts/module.lds
        fi
    fi
}

do_deploy:append() {
    # Remove the redundant device tree file (<device_tree>-<MACHINE>.dtb) that was created in the deploy directory
    for dtbf in ${KERNEL_DEVICETREE}; do
        dtb=`normalize_dtb "$dtbf"`
        dtb_ext=${dtb##*.}
        dtb_base_name=`basename $dtb .$dtb_ext`
        rm -f $deployDir/$dtb_base_name-${KERNEL_DTB_LINK_NAME}.$dtb_ext
    done
}
