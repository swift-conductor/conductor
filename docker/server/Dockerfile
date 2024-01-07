#
# conductor:server - Swift Conductor Core Server
#
# ===========================================================================================================
# 0. Builder stage
# ===========================================================================================================
FROM eclipse-temurin:17-jammy AS builder

LABEL maintainer="Swift Software Group <hello@swiftsoftwaregroup.com>"

# ===========================================================================================================
# 0. Build Conductor Server
# ===========================================================================================================
RUN apt update && apt -y install git

# Copy the project directly onto the image
WORKDIR /
COPY . /server-build/conductor

# Build the server on run
WORKDIR /server-build/conductor
RUN ./gradlew clean build -x test --stacktrace

# ===========================================================================================================
# 1. Bin stage
# ===========================================================================================================
FROM alpine:3.19

LABEL maintainer="Swift Software Group <hello@swiftsoftwaregroup.com>"

RUN apk add openjdk17

# Make app folders
RUN mkdir -p /app/config /app/logs /app/libs

# Copy the compiled output to new image
COPY docker/server/bin /app
COPY docker/server/config /app/config
COPY --from=builder /server-build/conductor/server/build/libs/*boot*.jar /app/libs/conductor-server.jar

# Copy the files for the server into the app folders
RUN chmod +x /app/startup.sh

HEALTHCHECK --interval=60s --timeout=30s --retries=10 CMD curl -I -XGET http://localhost:8080/health || exit 1

CMD [ "/app/startup.sh" ]
ENTRYPOINT [ "/bin/sh"]
