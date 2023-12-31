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
package com.swiftconductor.conductor.cassandra.dao

import java.time.Duration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.CassandraContainer
import org.testcontainers.containers.wait.CassandraQueryWaitStrategy
import org.testcontainers.spock.Testcontainers

import com.swiftconductor.conductor.cassandra.config.CassandraProperties
import com.swiftconductor.conductor.cassandra.util.Statements
import com.swiftconductor.conductor.common.config.TestObjectMapperConfiguration

import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.Session
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.PackageScope
import spock.lang.Shared
import spock.lang.Specification

@ContextConfiguration(classes = [TestObjectMapperConfiguration.class])
@Testcontainers
@PackageScope
abstract class CassandraSpec extends Specification {

    @Shared
    CassandraContainer cassandra = new CassandraContainer()
                                        .waitingFor(new CassandraQueryWaitStrategy());
    @Shared
    Session session

    @Autowired
    ObjectMapper objectMapper

    CassandraProperties cassandraProperties
    Statements statements

    def setupSpec() {
        session = cassandra.cluster.newSession()
    }

    def setup() {
        String keyspaceName = "junit"
        cassandraProperties = Mock(CassandraProperties.class) {
            getKeyspace() >> keyspaceName
            getReplicationStrategy() >> "SimpleStrategy"
            getReplicationFactorKey() >> "replication_factor"
            getReplicationFactorValue() >> 1
            getReadConsistencyLevel() >> ConsistencyLevel.LOCAL_ONE
            getWriteConsistencyLevel() >> ConsistencyLevel.LOCAL_ONE
            getTaskDefCacheRefreshInterval() >> Duration.ofSeconds(60)
            getEventHandlerCacheRefreshInterval() >> Duration.ofSeconds(60)
            getEventExecutionPersistenceTtl() >> Duration.ofSeconds(5)
        }

        statements = new Statements(keyspaceName)
    }
}
