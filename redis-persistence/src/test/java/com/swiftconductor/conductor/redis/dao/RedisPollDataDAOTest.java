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
package com.swiftconductor.conductor.redis.dao;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiftconductor.conductor.common.config.TestObjectMapperConfiguration;
import com.swiftconductor.conductor.core.config.ConductorProperties;
import com.swiftconductor.conductor.dao.PollDataDAO;
import com.swiftconductor.conductor.dao.PollDataDAOTest;
import com.swiftconductor.conductor.redis.config.RedisProperties;
import com.swiftconductor.conductor.redis.jedis.JedisMock;
import com.swiftconductor.conductor.redis.jedis.JedisProxy;
import redis.clients.jedis.commands.JedisCommands;

import static org.mockito.Mockito.mock;

@ContextConfiguration(classes = {TestObjectMapperConfiguration.class})
@RunWith(SpringRunner.class)
public class RedisPollDataDAOTest extends PollDataDAOTest {

    private PollDataDAO redisPollDataDAO;

    @Autowired private ObjectMapper objectMapper;

    @Before
    public void init() {
        ConductorProperties conductorProperties = mock(ConductorProperties.class);
        RedisProperties properties = mock(RedisProperties.class);
        JedisCommands jedisMock = new JedisMock();
        JedisProxy jedisProxy = new JedisProxy(jedisMock);

        redisPollDataDAO =
                new RedisPollDataDAO(jedisProxy, objectMapper, conductorProperties, properties);
    }

    @Override
    protected PollDataDAO getPollDataDAO() {
        return redisPollDataDAO;
    }
}
