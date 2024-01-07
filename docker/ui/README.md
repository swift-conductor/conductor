# Docker

## Conductor UI

This Dockerfile creates the swift-conductor:ui image

## Building the image

Run the following commands from the project root.

```sh
docker build --file Dockerfile --tag swift-conductor:ui .
```

## Running the conductor server

With localhost conductor server: 

```sh
docker run -p 5000:5000 -d -t swift-conductor:ui
```

With external conductor server: 

```sh
docker run -p 5000:5000 -d -t -e "WF_SERVER=http://conductor-server:8080" swift-conductor:ui
```
