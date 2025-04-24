SOC_FAMILY =. "rcar-gen4:"

require conf/machine/include/rcar_common.inc

WKS_FILE ?= "rcar-singlepart-noloader.wks"

IMAGE_FSTYPES += " ext4 wic.gz"
