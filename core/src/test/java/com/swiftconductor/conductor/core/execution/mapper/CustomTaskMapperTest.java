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
package com.swiftconductor.conductor.core.execution.mapper;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.swiftconductor.conductor.common.metadata.tasks.TaskDef;
import com.swiftconductor.conductor.common.metadata.workflow.WorkflowDef;
import com.swiftconductor.conductor.common.metadata.workflow.WorkflowTask;
import com.swiftconductor.conductor.core.exception.TerminateWorkflowException;
import com.swiftconductor.conductor.core.utils.IDGenerator;
import com.swiftconductor.conductor.core.utils.ParametersUtils;
import com.swiftconductor.conductor.model.TaskModel;
import com.swiftconductor.conductor.model.WorkflowModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class CustomTaskMapperTest {

    private CustomTaskMapper customTaskMapper;

    private IDGenerator idGenerator = new IDGenerator();

    @Rule public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        ParametersUtils parametersUtils = mock(ParametersUtils.class);
        customTaskMapper = new CustomTaskMapper(parametersUtils);
    }

    @Test
    public void getMappedTasks() {

        WorkflowTask workflowTask = new WorkflowTask();
        workflowTask.setName("custom_task");
        workflowTask.setTaskDefinition(new TaskDef("custom_task"));

        String taskId = idGenerator.generate();
        String retriedTaskId = idGenerator.generate();

        WorkflowDef workflowDef = new WorkflowDef();
        WorkflowModel workflow = new WorkflowModel();
        workflow.setWorkflowDefinition(workflowDef);

        TaskMapperContext taskMapperContext =
                TaskMapperContext.newBuilder()
                        .withWorkflowModel(workflow)
                        .withTaskDefinition(new TaskDef())
                        .withWorkflowTask(workflowTask)
                        .withTaskInput(new HashMap<>())
                        .withRetryCount(0)
                        .withRetryTaskId(retriedTaskId)
                        .withTaskId(taskId)
                        .build();

        List<TaskModel> mappedTasks = customTaskMapper.getMappedTasks(taskMapperContext);
        assertNotNull(mappedTasks);
        assertEquals(1, mappedTasks.size());
    }

    @Test
    public void getMappedTasksException() {

        // Given
        WorkflowTask workflowTask = new WorkflowTask();
        workflowTask.setName("custom_task");
        String taskId = idGenerator.generate();
        String retriedTaskId = idGenerator.generate();

        WorkflowDef workflowDef = new WorkflowDef();
        WorkflowModel workflow = new WorkflowModel();
        workflow.setWorkflowDefinition(workflowDef);

        TaskMapperContext taskMapperContext =
                TaskMapperContext.newBuilder()
                        .withWorkflowModel(workflow)
                        .withTaskDefinition(new TaskDef())
                        .withWorkflowTask(workflowTask)
                        .withTaskInput(new HashMap<>())
                        .withRetryCount(0)
                        .withRetryTaskId(retriedTaskId)
                        .withTaskId(taskId)
                        .build();

        // then
        expectedException.expect(TerminateWorkflowException.class);
        expectedException.expectMessage(
                String.format(
                        "Invalid task. Task %s does not have a definition",
                        workflowTask.getName()));

        // when
        customTaskMapper.getMappedTasks(taskMapperContext);
    }
}
