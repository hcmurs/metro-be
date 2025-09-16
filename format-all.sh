#!/bin/bash

set -e

# Colors
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

print_status() {
  echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
  echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
  echo -e "${RED}[ERROR]${NC} $1"
}

COMMAND=${1:-check}
SERVICES_DIR="services"

if [ ! -d "$SERVICES_DIR" ]; then
  print_error "Services directory not found!"
  exit 1
fi

pids=()
for service in "$SERVICES_DIR"/*; do
  if [ -d "$service" ]; then
    if [ -x "$service/format.sh" ]; then
      # ensure mvnw is executable
      if [ -f "$service/mvnw" ] && [ ! -x "$service/mvnw" ]; then
        chmod +x "$service/mvnw"
        print_warning "Fixed permissions for mvnw in $(basename "$service")"
      fi

      print_status "Starting format.sh ($COMMAND) in $(basename "$service")..."
      (
        cd "$service" && ./format.sh "$COMMAND"
        print_status "✔ Done: $(basename "$service")"
      ) &
      pids+=($!)
    else
      print_warning "No format.sh found in $(basename "$service"), skipping..."
    fi
  fi
done

# Wait for all parallel jobs
for pid in "${pids[@]}"; do
  wait $pid || {
    print_error "❌ One of the services failed formatting."
    exit 1
  }
done

print_status "✅ Formatting finished for all services!"
