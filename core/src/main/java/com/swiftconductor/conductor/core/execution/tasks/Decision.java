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
package com.swiftconductor.conductor.core.execution.tasks;

import org.springframework.stereotype.Component;

import com.swiftconductor.conductor.core.execution.WorkflowExecutor;
import com.swiftconductor.conductor.model.TaskModel;
import com.swiftconductor.conductor.model.WorkflowModel;

import static com.swiftconductor.conductor.common.metadata.tasks.TaskType.TASK_TYPE_DECISION;

/**
 * @deprecated {@link Decision} is deprecated. Use {@link Switch} task for condition evaluation
 *     using the extensible evaluation framework. Also see ${@link
 *     com.swiftconductor.conductor.common.metadata.workflow.WorkflowTask}).
 */
@Deprecated
@Component(TASK_TYPE_DECISION)
public class Decision extends WorkflowSystemTask {

    public Decision() {
        super(TASK_TYPE_DECISION);
    }

    @Override
    public boolean execute(
            WorkflowModel workflow, TaskModel task, WorkflowExecutor workflowExecutor) {
        task.setStatus(TaskModel.Status.COMPLETED);
        return true;
    }
}
