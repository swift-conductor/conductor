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
package com.swiftconductor.conductor.core.execution.tasks

import javax.validation.ConstraintViolation
import javax.validation.Validator

import com.swiftconductor.conductor.common.config.ObjectMapperProvider
import com.swiftconductor.conductor.core.exception.NotFoundException
import com.swiftconductor.conductor.core.exception.TransientException
import com.swiftconductor.conductor.core.execution.WorkflowExecutor
import com.swiftconductor.conductor.core.operation.StartWorkflowOperation
import com.swiftconductor.conductor.model.TaskModel
import com.swiftconductor.conductor.model.WorkflowModel

import spock.lang.Specification
import spock.lang.Subject

import static com.swiftconductor.conductor.core.execution.tasks.StartWorkflow.START_WORKFLOW_PARAMETER
import static com.swiftconductor.conductor.model.TaskModel.Status.FAILED
import static com.swiftconductor.conductor.model.TaskModel.Status.SCHEDULED

/**
 * Unit test for StartWorkflow. Success and Javax validation cases are covered by the StartWorkflowSpec in test-harness module.
 */
class StartWorkflowSpec extends Specification {

    @Subject
    StartWorkflow startWorkflow

    WorkflowExecutor workflowExecutor
    Validator validator
    WorkflowModel workflowModel
    TaskModel taskModel
    StartWorkflowOperation startWorkflowOperation

    def setup() {
        workflowExecutor = Mock(WorkflowExecutor.class)
        validator = Mock(Validator.class) {
            validate(_) >> new HashSet<ConstraintViolation<Object>>()
        }
        startWorkflowOperation = Mock(StartWorkflowOperation.class)

        def inputData = [:]
        inputData[START_WORKFLOW_PARAMETER] = ['name': 'some_workflow']
        taskModel = new TaskModel(status: SCHEDULED, inputData: inputData)
        workflowModel = new WorkflowModel()

        startWorkflow = new StartWorkflow(new ObjectMapperProvider().getObjectMapper(), validator, startWorkflowOperation)
    }

    def "StartWorkflow task is asynchronous"() {
        expect:
        startWorkflow.isAsync()
    }

    def "startWorkflow parameter is missing"() {
        given: "a task with no start_workflow in input"
        taskModel.inputData = [:]

        when:
        startWorkflow.start(workflowModel, taskModel, workflowExecutor)

        then:
        taskModel.status == FAILED
        taskModel.reasonForIncompletion != null
    }

    def "ObjectMapper throws an IllegalArgumentException"() {
        given: "a task with no start_workflow in input"
        taskModel.inputData[START_WORKFLOW_PARAMETER] = "I can't be converted to StartWorkflowRequest"

        when:
        startWorkflow.start(workflowModel, taskModel, workflowExecutor)

        then:
        taskModel.status == FAILED
        taskModel.reasonForIncompletion != null
    }

    def "WorkflowExecutor throws a retryable exception"() {
        when:
        startWorkflow.start(workflowModel, taskModel, workflowExecutor)

        then:
        taskModel.status == SCHEDULED
        1 * startWorkflowOperation.execute(*_) >> { throw new TransientException("") }
    }

    def "WorkflowExecutor throws a NotFoundException"() {
        when:
        startWorkflow.start(workflowModel, taskModel, workflowExecutor)

        then:
        taskModel.status == FAILED
        taskModel.reasonForIncompletion != null
        1 * startWorkflowOperation.execute(*_) >> { throw new NotFoundException("") }
    }

    def "WorkflowExecutor throws a RuntimeException"() {
        when:
        startWorkflow.start(workflowModel, taskModel, workflowExecutor)

        then:
        taskModel.status == FAILED
        taskModel.reasonForIncompletion != null
        1 * startWorkflowOperation.execute(*_) >> { throw new RuntimeException("I am an unexpected exception") }
    }
}
