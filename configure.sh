#!/usr/bin/env bash

# Java

source ~/.sdkman/bin/sdkman-init.sh
sdk install java 17.0.5-tem
sdk install groovy 4.0.6
sdk install gradle 7.5.1
sdk env

# Gradle
gradle wrapper