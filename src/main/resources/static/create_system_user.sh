#!/bin/bash

# Usage: ./create_system_user.sh <username>

# Validate args
if [ "$#" -ne 1 ]; then
  echo "Usage: $0 <username>"
  exit 1
fi

# Vars
USERNAME="$1"
BASE_PATH="/srv/phylactery-store"
USER_DIR="$BASE_PATH/$USERNAME"

# Add system user
if ! id "$USERNAME" &>/dev/null; then
  sudo adduser --disabled-password --no-create-home --gecos "" "$USERNAME"
  echo "User $USERNAME created!"
else
  echo "User $USERNAME already exists!"
fi

# Create user working directory
sudo mkdir -p "$USER_DIR"
sudo chown "$USERNAME:$USERNAME" "$USER_DIR"
sudo chmod 700 "$USER_DIR"
echo "Dir $USER_DIR created!"
