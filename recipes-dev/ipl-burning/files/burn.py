#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# import section
import os
import sys
import shutil
import subprocess
import serial.tools.list_ports # pyserial
import colorama

# Global variables
WORK_DIR = os.path.dirname(os.path.abspath(__file__))
IPL_PATH=f"{WORK_DIR}"
MOT_PATH=f"{WORK_DIR}"

# Functions

def print_err(str):
    colorama.init()
    print(f'{colorama.Fore.RED}{str}{colorama.Style.RESET_ALL}')

def Usage():
    print(f"Usage:")
    print(f"    {sys.argv[0]} board <comport or serial_device>")
    print(f"comport or serial_device:")
    for comport in serial.tools.list_ports.comports():
        print(f"    {comport}")


def main():
    BOARD = "sparrow-hawk"
    COM_PORT = "/dev/ttyUSBXX or COMXX"
    BURN_MODE = "all"
    DRY_RUN = False # if True, test only this file(contains file copy process)

    args = sys.argv
    if "-h" in args:
        Usage(); quit()

    if len(args) <= 1:
        print_err(f"ERROR: Please input a comport")
        Usage(); quit()
    elif args[1] not in [comport.device for comport in serial.tools.list_ports.comports()]:
        print_err(f"ERROR: Please \"input\" correct comport:")
        Usage(); quit()
    COM_PORT=args[1]

    cmd = ["python3", "ipl_burning.py" ,BOARD, COM_PORT, MOT_PATH, IPL_PATH, BURN_MODE]
    if DRY_RUN is False:
        subprocess.run(cmd)

if __name__ == "__main__":
    main()

