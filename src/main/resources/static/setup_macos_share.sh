#!/bin/bash

# Usage: ./setup_timemachine_share.sh <username> <password>

# Validate args
if [ "$#" -ne 2 ]; then
  echo "Usage: $0 <username> <password>"
  exit 1
fi

# Setup Vars
USERNAME="$1"
PASSWORD="$2"
BASE_PATH="/srv/phylactery-store"
USER_DIR="$BASE_PATH/$USERNAME"
TM_DIR="$USER_DIR/timemachine"

# create timemachine folder
sudo mkdir -p "$TM_DIR"

# Add samba user
(echo "$PASSWORD"; echo "$PASSWORD") | sudo smbpasswd -s -a "$USERNAME"
echo "Add samba-user $USERNAME success!"
