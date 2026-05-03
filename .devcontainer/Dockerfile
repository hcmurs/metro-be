FROM mcr.microsoft.com/devcontainers/java:1-21-bookworm

# Remove stale Yarn apt sources that can break apt update during feature installation.
RUN set -eux; \
    find /etc/apt/sources.list.d -maxdepth 1 -type f -name '*yarn*' -delete

# install node and npm for Spotless
RUN apt-get update && apt-get install -y nodejs npm && rm -rf /var/lib/apt/lists/*
