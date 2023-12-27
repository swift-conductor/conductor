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
package com.swiftconductor.core.execution.mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.swiftconductor.common.metadata.tasks.TaskDef;
import com.swiftconductor.common.metadata.tasks.TaskType;
import com.swiftconductor.common.metadata.workflow.WorkflowTask;
import com.swiftconductor.core.utils.ParametersUtils;
import com.swiftconductor.dao.MetadataDAO;
import com.swiftconductor.model.TaskModel;
import com.swiftconductor.model.WorkflowModel;

/**
 * @author x-ultra
 * @deprecated {@link com.swiftconductor.core.execution.tasks.Lambda} is also deprecated. Use
 *     {@link com.swiftconductor.core.execution.tasks.Inline} and so ${@link InlineTaskMapper}
 *     will be used as a result.
 */
@Deprecated
@Component
public class LambdaTaskMapper implements TaskMapper {

    public static final Logger LOGGER = LoggerFactory.getLogger(LambdaTaskMapper.class);
    private final ParametersUtils parametersUtils;
    private final MetadataDAO metadataDAO;

    public LambdaTaskMapper(ParametersUtils parametersUtils, MetadataDAO metadataDAO) {
        this.parametersUtils = parametersUtils;
        this.metadataDAO = metadataDAO;
    }

    @Override
    public String getTaskType() {
        return TaskType.LAMBDA.name();
    }

    @Override
    public List<TaskModel> getMappedTasks(TaskMapperContext taskMapperContext) {

        LOGGER.debug("TaskMapperContext {} in LambdaTaskMapper", taskMapperContext);

        WorkflowTask workflowTask = taskMapperContext.getWorkflowTask();
        WorkflowModel workflowModel = taskMapperContext.getWorkflowModel();
        String taskId = taskMapperContext.getTaskId();

        TaskDef taskDefinition =
                Optional.ofNullable(taskMapperContext.getTaskDefinition())
                        .orElseGet(() -> metadataDAO.getTaskDef(workflowTask.getName()));

        Map<String, Object> taskInput =
                parametersUtils.getTaskInputV2(
                        taskMapperContext.getWorkflowTask().getInputParameters(),
                        workflowModel,
                        taskId,
                        taskDefinition);

        TaskModel lambdaTask = taskMapperContext.createTaskModel();
        lambdaTask.setTaskType(TaskType.TASK_TYPE_LAMBDA);
        lambdaTask.setStartTime(System.currentTimeMillis());
        lambdaTask.setInputData(taskInput);
        lambdaTask.setStatus(TaskModel.Status.IN_PROGRESS);

        return List.of(lambdaTask);
    }
}
