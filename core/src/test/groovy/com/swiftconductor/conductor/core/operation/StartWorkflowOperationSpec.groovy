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
package com.swiftconductor.core.operation

import org.springframework.context.ApplicationEventPublisher

import com.swiftconductor.common.metadata.workflow.WorkflowDef
import com.swiftconductor.core.dal.ExecutionDAOFacade
import com.swiftconductor.core.execution.StartWorkflowInput
import com.swiftconductor.core.metadata.MetadataMapperService
import com.swiftconductor.core.utils.IDGenerator
import com.swiftconductor.core.utils.ParametersUtils
import com.swiftconductor.service.ExecutionLockService
import spock.lang.Specification
import spock.lang.Subject

class StartWorkflowOperationSpec extends Specification {

    @Subject
    StartWorkflowOperation startWorkflowOperation

    MetadataMapperService metadataMapperService
    IDGenerator idGenerator
    ParametersUtils parametersUtils
    ExecutionDAOFacade executionDAOFacade
    ExecutionLockService executionLockService
    ApplicationEventPublisher eventPublisher

    def setup() {
        metadataMapperService = Mock(MetadataMapperService.class)
        idGenerator = Mock(IDGenerator.class)
        parametersUtils = Mock(ParametersUtils.class)
        executionDAOFacade = Mock(ExecutionDAOFacade.class)
        executionLockService = Mock(ExecutionLockService.class)
        eventPublisher = Mock(ApplicationEventPublisher.class)

        startWorkflowOperation = new StartWorkflowOperation(metadataMapperService, idGenerator, parametersUtils, executionDAOFacade, executionLockService, eventPublisher)
    }

    def "simple start workflow"() {
        given:
        def workflowDef = new WorkflowDef(name: 'test')
        def generatedWorkflowId = UUID.randomUUID().toString()

        StartWorkflowInput startWorkflowInput = new StartWorkflowInput(workflowDefinition: workflowDef, workflowInput: [:])

        when:
        def workflowId = startWorkflowOperation.execute(startWorkflowInput)

        then:
        workflowId == generatedWorkflowId
        1 * idGenerator.generate() >> generatedWorkflowId
        1 * metadataMapperService.populateTaskDefinitions(workflowDef) >> workflowDef
        1 * executionLockService.acquireLock(generatedWorkflowId) >> true
        1 * executionDAOFacade.createWorkflow(_)
        1 * eventPublisher.publishEvent(_)
    }
}
