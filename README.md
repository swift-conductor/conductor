![Conductor](docs/logo.svg)

## Notice

> As of **December 13, 2023**, Netflix has discontinued maintenance of Netflix Conductor OSS on GitHub. This is a fork of the [original](https://github.com/Netflix/conductor) project maintained by [Swift Software Group](https://www.swiftsoftwaregroup.com).

> We (Swift Software Group) are working on getting everything up and running again. Things may be broken here and there while we are in the middle of this transition.

# Conductor

[![OSS Lifecycle](https://img.shields.io/osslifecycle/swift-conductor/conductor.svg)]()
[![Github release](https://img.shields.io/github/v/release/swift-conductor/conductor.svg)](https://github.com/swift-conductor/conductor/releases)
[![License](https://img.shields.io/github/license/swift-conductor/conductor.svg)](http://www.apache.org/licenses/LICENSE-2.0)

[![GitHub stars](https://img.shields.io/github/stars/swift-conductor/conductor.svg?style=social&label=Star&maxAge=2592000)](https://github.com/swift-conductor/conductor/stargazers/)
[![GitHub forks](https://img.shields.io/github/forks/swift-conductor/conductor.svg?style=social&label=Fork&maxAge=2592000)](https://github.com/swift-conductor/conductor/network/)

Conductor is a platform for orchestrating workflows that span across microservices.

## Releases

The latest release is [![Github release](https://img.shields.io/github/v/release/swift-conductor/conductor.svg)](https://github.com/swift-conductor/conductor/releases)

The last Netflix release was [![Github release](https://img.shields.io/github/v/release/Netflix/conductor.svg)](https://github.com/Netflix/conductor/releases)

## Workflow Creation in Code

Conductor supports creating workflows using JSON and Code. SDK support for creating workflows using code is available in multiple languages and can be found at:

- [Swift Conductor Client SDK for Python](https://pypi.org/project/swift-conductor-client/)
- [Swift Conductor Client SDK for .NET](https://www.nuget.org/packages/swift-conductor-client)
- [Swift Conductor Client SDK for Java](https://github.com/swift-conductor/conductor/tree/main/java-sdk)
- [Swift Conductor Client SDK for Go](https://github.com/swift-conductor/conductor-client-golang)
- [Swift Conductor Client SDK for TypeScript / JavaScript](https://github.com/swift-conductor/conductor-client-typescript)

## Community Contributions

The modules contributed by the community are housed at [conductor-community](https://github.com/swift-conductor/conductor-community). Compatible versions of the community modules are released simultaneously with releases of the main modules.

[Discussion Forum](https://github.com/swift-conductor/conductor/discussions): Please use the forum for questions and discussing ideas and join the community.

[List of Conductor community projects](https://github.com/swift-conductor/conductor-docs/blob/main/docs/resources/related.md): Backup tool, Cron like workflow starter, Docker containers and more.

## Getting Started - Building & Running Conductor

### Using Docker

The easiest way to get started is with Docker containers. Please follow the instructions [here](https://swiftconductor.com/getting-started/running/docker.html).

### From Source

Conductor Server is a [Spring Boot](https://spring.io/projects/spring-boot) project and follows all applicable conventions. See instructions [here](https://swiftconductor.com/getting-started/running/source.html).

## Published Artifacts

Latest release binaries are available from the [Maven Central Repository: com.swiftconductor.conductor](https://search.maven.org/search?q=g:com.swiftconductor.conductor).

Latest snapshot binaries are available from the [Maven Snapshot Repository](https://s01.oss.sonatype.org/content/repositories/snapshots/com/swiftconductor/conductor/).

See [OSSRH Usage Notes](https://central.sonatype.org/publish/publish-guide/#accessing-repositories) for details.

### Server

| Artifact                        | Description                                                                                     |
| ------------------------------- | ----------------------------------------------------------------------------------------------- |
| conductor-common                | Common models used by various conductor modules                                                 |
| conductor-core                  | Core Conductor module                                                                           |
| conductor-rest                  | Spring MVC Rest API                                                                             |
| conductor-server                | Spring Boot Web Application                                                                     |
| conductor-grpc                  | Protobuf models used by the server and client                                                   |
| conductor-grpc-server           | gRPC server Application                                                                         |
| conductor-http-task             | Workflow system task implementation to send make requests                                       |
| conductor-json-jq-task          | Workflow system task implementation to evaluate JSON using [jq](https://stedolan.github.io/jq/) |
| conductor-redis-lock            | Workflow execution lock implementation using Redis                                              |
| conductor-redis-persistence     | Persistence and queue using Redis / Dynomite                                                    |
| conductor-cassandra-persistence | Persistence using Cassandra                                                                     |
| conductor-es6-persistence       | Indexing using Elasticsearch 6.X                                                                |
| conductor-awss3-storage         | External payload storage implementation using AWS S3                                            |
| conductor-awssqs-event-queue    | Event queue implementation using AWS SQS                                                        |

### Client

| Artifact                | Description                                                                                                       |
| ----------------------- | ----------------------------------------------------------------------------------------------------------------- |
| conductor-ui            | [Web UI / React App](https://github.com/swift-conductor/conductor-ui)                                             |
| conductor-client        | [Java Client](https://github.com/swift-conductor/conductor-client-java) that includes helpers for running workers |
| conductor-client-spring | [Java Client Starter Kit for Spring](https://github.com/swift-conductor/conductor-client-java)                    |
| conductor-grpc-client   | [Java gRPC client](https://github.com/swift-conductor/conductor-client-java) to interact with the gRPC server     |
| conductor-java-sdk      | [Java SDK](https://github.com/swift-conductor/conductor-client-java) for writing workflows in code                |
| conductor-test-e2e      | [Integration and regression tests](https://github.com/swift-conductor/conductor-client-java)                      |

## Database Requirements

- The default persistence used is Redis
- The indexing backend is [Elasticsearch](https://www.elastic.co/) (6.x)

## Other Requirements

- JDK 17+
- UI requires Node 14 to build. Earlier Node versions may work but is untested.

## Get Support

Get in touch with us:

- [GitHub Discussion Forum](https://github.com/swift-conductor/conductor/discussions)

## Contributions

Whether it is a small documentation correction, bug fix or a new feature, contributions are highly appreciated. We just ask you to follow standard OSS guidelines. The [Discussion Forum](https://github.com/swift-conductor/conductor/discussions) is a good place to ask questions, discuss new features and explore ideas. Please check with us before spending too much time, only to find out later that someone else is already working on a similar feature.

`main` branch is the current working branch. Please send your PR's to `main` branch, making sure that it builds on your local system successfully. Also, please make sure all the conflicts are resolved.

## License

Copyright 2023 Swift Software Group, Inc.  
(Code and content before December 13, 2023, Copyright Netflix, Inc.)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
