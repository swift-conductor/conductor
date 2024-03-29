/*
 * Copyright 2023 Swift Software Group, Inc.
 * (Code and content before December 13, 2023, Copyright Netflix, Inc.)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.swiftconductor.conductor.redis.dynoqueue;

import java.util.List;

import com.swiftconductor.conductor.redis.config.RedisProperties;

import com.google.common.collect.Lists;
import com.netflix.dyno.connectionpool.Host;
import com.netflix.dyno.connectionpool.HostBuilder;
import com.netflix.dyno.connectionpool.HostSupplier;

public class LocalhostHostSupplier implements HostSupplier {

    private final RedisProperties properties;

    public LocalhostHostSupplier(RedisProperties properties) {
        this.properties = properties;
    }

    @Override
    public List<Host> getHosts() {
        Host dynoHost =
                new HostBuilder()
                        .setHostname("localhost")
                        .setIpAddress("0")
                        .setRack(properties.getAvailabilityZone())
                        .setStatus(Host.Status.Up)
                        .createHost();
        return Lists.newArrayList(dynoHost);
    }
}
