#!/usr/bin/env bash

source ~/.sdkman/bin/sdkman-init.sh

# Java
sdk install java 17.0.5-tem

# Gradle
sdk install groovy 4.0.6
sdk install gradle 7.5.1

sdk env

# Download packages
gradle wrapper
